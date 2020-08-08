package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoImpl();

    // 注册用户
    @Override
    public boolean register(User user) {
        // 根据用户名查询信息
        User u = dao.findByUsername(user.getUsername());
        // 判断用户是否存在
        if (u != null) {
            // 用户存在,保存失败
            return false;
        }

        // 用户初始化
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());

        // 保存用户
        dao.save(user);

        //激活邮件内容
        String content = "<a href='http://localhost:80/travel/user/activation?code="+user.getCode()+"'>点击该处激活你的用户</a>";

        //发送激活邮件
        MailUtils.sendMail(user.getEmail(),content,"激活用户");

        return true;
    }

    // 判断用户是否已经存在
    @Override
    public boolean userIsExist(String username) {
        return dao.findByUsername(username) != null;
    }

    // 发送消息的工具
    @Override
    public void sendMsg(HttpServletResponse response, boolean flag, String errorMsg, Object data) {
        ResultInfo info = new ResultInfo();
        info.setErrorMsg(errorMsg);
        info.setFlag(flag);
        info.setData(data);

        //  序列化info
        ObjectMapper mapper = new ObjectMapper();
        String msg = null;
        try {
            msg = mapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //  将json数据写会客户端
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 激活用户
    @Override
    public boolean active(String code) {
        User user = dao.findByCode(code);
//        System.out.println(user);
        if(user != null) {
            dao.updateStatus(code);
            return true;
        }
        return false;
    }

    // 查询激活状态
    @Override
    public boolean activeStatus(String username) {

        return dao.userStatus(username).equals("Y");
    }

    // 登录方法
    @Override
    public boolean login(String username, String password) {
        User user = dao.findByUsername(username);
        if (user.getPassword().equals(password)) {
           return true;
        }
        return false;
    }

    // 返回一个用户操作
    @Override
    public User getUser(String username) {
        return dao.findByUsername(username);
    }
}
