import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.util.JedisUtil;
import cn.itcast.travel.util.MailUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class test {
    @Test
    public void test1() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection);
    }

    @Test
    public void test2() {
        String content = "<a href='http://localhost:80/travel/ActivationServlet?code="+"8c68c8ececef438ea8282902602ab67d"+"'>点击该处激活你的用户</a>";
        MailUtils.sendMail("2569677439@qq.com",content,"激活邮件");
    }

    @Test
    public void test3() {
        CategoryServiceImpl service = new CategoryServiceImpl();
        List<Category> categories = service.findAll();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String value = mapper.writeValueAsString(categories.toArray());
            System.out.println(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(service.findAll());
    }

    @Test
    public void test4() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.set("momo","alice");
        System.out.println(jedis.get("momo"));
    }

    @Test
    public void test5() {
        CategoryDao dao = new CategoryDaoImpl();
        Jedis jedis = JedisUtil.getJedis();
        // 从redis中查询
        Set<String> category = jedis.zrange("category", 0, -1);
//        System.out.println(category);
        List<Category> categories = null;
        // 判断集合是否为空
        if (category == null || category.size() == 0) {
            // 从数据库返回
            categories = dao.findAll();
            // 将数据存储到redis
            for (Category c : categories) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }
        } else {
            categories = new ArrayList<Category>();
            for (String s : category) {
                Category c = new Category();
                c.setCname(s);
                categories.add(c);

            }
        }
    }

    @Test
    public void test6() {
        String s = null;
        if(s.length() > 0 ){
            System.out.println("momo");
        }
    }

    @Test
    public void test7() {
        RouteImgDao dao = new RouteImgDaoImpl();
        System.out.println(dao.findByRouteImg(1));
    }


    @Test
    public void test8() {
        RouteDao dao = new RouteDaoImpl();
        System.out.println(dao.findByRoute(1));
    }

    @Test
    public void test9() {
        SellerDao sellerDao = new SellerDaoImpl();
        System.out.println(sellerDao.findBySeller(1));
    }
}
