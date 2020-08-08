package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    // 查询图片集合
    public List<RouteImg> findByRouteImg(int rid);


}
