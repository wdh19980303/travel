package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 提前设置了编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 正式代码

        // 创建服务类
//        UserService userService = new UserServiceImpl();

        // 验证码校验
        String CheckCode = request.getParameter("check");
        if (CheckCode.equalsIgnoreCase((String) request.getSession().getAttribute("CHECKCODE_SERVER"))) {
            // 验证码正确
            request.getSession().removeAttribute("CHECKCODE_SERVER");

        } else {
            // 验证码错误
            request.getSession().removeAttribute("CHECKCODE_SERVER");
            service.sendMsg(response, false, "验证码错误", null);
            return;
        }

        // 校验用户是否已经存在
        if (service.userIsExist(request.getParameter("username"))) {
            service.sendMsg(response, false, "用户存在", null);
            return;
        }


        // 1 获取数据
        Map<String, String[]> map = request.getParameterMap();
        // 2 封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3 调用Service注册
        boolean flag = service.register(user);

        if (flag) {
            // 如果成功
            service.sendMsg(response, flag, "注册成功", null);
        } else {
            // 如果失败
            service.sendMsg(response, flag, "注册失败", null);
        }

    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 提前设置了编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 正式代码

        // 获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //调用Service
//        UserService service = new UserServiceImpl();
        if (!service.userIsExist(username)) {
            service.sendMsg(response, false, "用户名错误", null);
            return;
        }

        if (!service.activeStatus(username)) {
            service.sendMsg(response, false, "用户未激活,请去邮箱进行激活", null);
            return;
        }

        if (!service.login(username, password)) {
            service.sendMsg(response, false, "密码错误", null);
            return;
        }

        request.getSession().setAttribute("loginUser", service.getUser(username));
        service.sendMsg(response, true, "登录成功", null);

    }

    /**
     * 查询姓名
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 提前设置了编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 正式代码
//        System.out.println(request.getSession().getAttribute("loginUser"));
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if(loginUser == null) {
            writeValue(null,response);
            return;
        }
        String json = new ObjectMapper().writeValueAsString(loginUser.getName());

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 退出
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 提前设置了编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 正式代码
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");

    }

    /**
     * 激活用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void activation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 提前设置了编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 正式代码

        // 获取激活码
        String code = request.getParameter("code");

        System.out.println(code);
        if (code != null && !code.equals("")) {
            //调用Service激活
            boolean flag = new UserServiceImpl().active(code);

            if (flag) {
                // 激活成功
                response.sendRedirect(request.getContextPath() + "activationSuccessful.html");
            } else {
                // 激活失败
                response.getWriter().write("<h1>激活失败,请联系管理员</h1>");
            }
        }
    }
}
