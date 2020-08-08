package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    // 查询用户名
    public User findByUsername(String username);

    //  用户保存
    public void save(User user);

    // 通过激活码查询用户
    public User findByCode(String code);

    // 激活用户
    public void updateStatus(String code);

    // 根据用户名查询激活状态
    public String userStatus(String username) ;
}
