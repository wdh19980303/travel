import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import org.junit.Test;

import java.sql.Date;

public class test_01 {

    @Test
    public void test1() {
        java.sql.Date date = new Date(new java.util.Date().getTime());
        System.out.println(date);
    }


    @Test
    public void test2() {
        FavoriteDao favoriteDao = new FavoriteDaoImpl();
        favoriteDao.insertFavorite(8,2);
    }
}
