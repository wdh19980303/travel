package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();


    // 分页
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收分页参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        // 接收查询参数
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        if (rname.equals("null")) {
            rname = null;
        }

        /*System.out.println("rname :_" + rname);
        System.out.println("current:_" + currentPageStr);
        System.out.println("cid :_" + cidStr);
        System.out.println("pageSize :_" + pageSizeStr);*/

        // 处理参数
        int cid = 0;
        System.out.println(cidStr);
        if (cidStr != null && cidStr.length() > 0 && !cidStr.equals("null")) {
            cid = Integer.parseInt(cidStr);
        }

        int pageSize = 5;
        if (pageSizeStr != null && pageSizeStr.length() > 0 && !pageSizeStr.equals("null")) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        int currentPage = 1;
        if (currentPageStr != null && currentPageStr.length() > 0 && !currentPageStr.equals("null")) {
            currentPage = Integer.parseInt(currentPageStr);
        }

        // 调用service
        PageBean<Route> route = routeService.pageQuery(cid, currentPage, pageSize, rname);
        writeValue(route, response);

    }

    // 根据rid查询一个旅游线路的详细信息
    public void routeQuery(HttpServletRequest request, HttpServletResponse response) throws Exception, ServletException {
        // 1 接收rid
        int rid = Integer.parseInt(request.getParameter("rid"));
        // 2 调用service查询
        Route route = routeService.completeRoute(rid);

        // 3 转为json对象写回页面
        writeValue(route, response);
    }

    // 判断当前登录用户是否收藏过该路线
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception, ServletException {
        // 获取路线的rid
        String rid = request.getParameter("rid");
        // 获取之前的用户信息
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        int uid;
        if (loginUser == null) {
            // 用户为空
            uid = 0;
        } else {
            uid = loginUser.getUid();
        }

        // 调用service查询

        boolean flag = favoriteService.isFavorite(Integer.parseInt(rid), uid);
        writeValue(flag, response);
    }

    // 添加收藏
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ridStr = request.getParameter("rid");
        int rid;
        if (ridStr != null && ridStr.length() > 0 && !ridStr.equals("null")) {
            rid = Integer.parseInt(ridStr);
        } else {
            return;
        }
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            return;
        }

        favoriteService.addFavorite(rid, loginUser.getUid());

        routeQuery(request,response);


    }


}
