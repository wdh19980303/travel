package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 返回总记录数
    @Override
    public int totalCount(int cid, String rname) {
//        String sql = "select count(*) from tab_route where cid = ?";
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder builder = new StringBuilder(sql);
        List params = new ArrayList();

        if (cid != 0) {
            builder.append(" and cid = ?");
            params.add(cid);
        }

        if (rname != null && rname.length() > 0) {
            builder.append(" and rname like ?");
            params.add("%" + rname + "%");
        }


        int total = template.queryForObject(builder.toString(), Integer.class, params.toArray());
        return total;
    }

    //返回数据集合
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//        String sql = "select * from tab_route where cid = ? limit ? , ?";
        String sql = "select  * from tab_route where 1 = 1";
        StringBuilder builder = new StringBuilder(sql);
        List params = new ArrayList();

        if (cid != 0) {
            builder.append(" and cid = ?");
            params.add(cid);
        }

        if (rname != null && rname.length() > 0) {
            builder.append(" and rname like ?");
            params.add("%" + rname + "%");
        }

        builder.append(" limit ? , ?");
        params.add(start);
        params.add(pageSize);

        return template.query(builder.toString(), new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }


    // 返回一个Route对象
    @Override
    public Route findByRoute(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
