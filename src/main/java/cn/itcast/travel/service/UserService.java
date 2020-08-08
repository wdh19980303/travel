package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    // 注册用户
    boolean register(User user);

    // 查询用户是否存在
    boolean userIsExist(String username);

    //发消息
    void sendMsg(HttpServletResponse response, boolean flag, String errorMsg, Object data);

    // 激活方法
    boolean active(String code);

    // 查询激活状态
    boolean activeStatus(String username);

    // 用户登录
    boolean login(String username, String password);

    // 返回一个用户进行操作
    User getUser(String username);
}
