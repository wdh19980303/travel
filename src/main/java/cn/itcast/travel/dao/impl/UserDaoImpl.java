package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    // 创建对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 通过用户名查询用户
    @Override
    public User findByUsername(String username) {
        //定义用户
        User user = null;
        // 定义sql
        String sql = "select * from tab_user where username = ?";
        // 查询
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {

        }
        return user;
    }

    // 保存用户
    @Override
    public void save(User user) {
        String sql = "insert into tab_user (username, password, name, birthday, sex, telephone, email, status, code)values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int i = template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    //通过激活码查询用户
    @Override
    public User findByCode(String code) {
//        System.out.println("findCode" + code);
        //定义用户
        User user = null;
        // 定义sql
        String sql = "select * from tab_user where code = ?";
        // 查询
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {

        }
        return user;
    }

    // 修改激活状态
    @Override
    public void updateStatus(String code) {
        String sql = "update tab_user set status = 'Y' where code = ? ";
        template.update(sql, code);
    }

    // 用户激活状态
    @Override
    public String userStatus(String username) {
        String sql = "select status from tab_user where username = ?";
        return template.queryForObject(sql, String.class, username);
    }
}
