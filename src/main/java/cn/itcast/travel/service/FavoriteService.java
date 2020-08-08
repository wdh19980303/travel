package cn.itcast.travel.service;

public interface FavoriteService {
    // 用户是否收藏
    public boolean isFavorite(int rid, int uid);

    // 用户收藏路线
    public void addFavorite(int rid, int uid);
}
