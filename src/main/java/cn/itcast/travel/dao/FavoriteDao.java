package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {

    // 查询一个Favorite
    public Favorite findByRidAndUid(int rid, int uid);

    // 查询路线订阅数量
    public int findCountByRid(int rid);

    // 添加一个收藏路线
    public void insertFavorite(int rid, int uid);

}
