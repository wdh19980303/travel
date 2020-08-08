package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    // 返回图片列表集合
    @Override
    public List<RouteImg> findByRouteImg(int rid) {
        String sql = " select * from tab_route_img where rid = ?";
        return template.query(sql,new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}
