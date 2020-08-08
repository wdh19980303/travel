package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.service.RouteService;

import java.util.ArrayList;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    private RouteImgDao imgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();

    // 查询分页数据集合
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        // 封装PageBean
        PageBean<Route> route = new PageBean<>();

        int totalCount = dao.totalCount(cid, rname);
        int start = (currentPage - 1) * pageSize;
        int totalPage = 1;
        List<Route> routeList = new ArrayList<>();

        routeList = dao.findByPage(cid, start, pageSize, rname);

        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }


        route.setList(routeList);
        route.setPageSize(pageSize);
        route.setCurrentPage(currentPage);
        route.setTotalCount(totalCount);
        route.setTotalPage(totalPage);


        return route;
    }

    // 查询完整的路线
    @Override
    public Route completeRoute(int rid) {
        // 根rid查询一个基本的route
        Route route = dao.findByRoute(rid);
        // 根据route的id查询图片集合信息
        List<RouteImg> routeImgList = imgDao.findByRouteImg(rid);
        // 设置到里面
        route.setRouteImgList(routeImgList);
        // 根据route的sid查询商家对象
        route.setSeller(sellerDao.findBySeller(route.getSid()));

        return route;
    }
}
