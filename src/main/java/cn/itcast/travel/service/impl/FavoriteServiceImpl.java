package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 判断是否收藏
     *
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(int rid, int uid) {

        return favoriteDao.findByRidAndUid(rid, uid) != null;

    }

    /**
     * 用户收藏路线
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
        favoriteDao.insertFavorite(rid, uid);
    }
}
