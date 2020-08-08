package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    private Jedis jedis = JedisUtil.getJedis();


    /**
     * 查询所有分类
     *
     * @return
     */
    @Override
    public List<Category> findAll() {
        // 从redis中查询
//        Set<String> categorySet = jedis.zrange("category", 0, -1);
        // id和value
        Set<Tuple> categorySet = jedis.zrangeWithScores("category", 0, -1);


        List<Category> categoryList = null;
        // 判断集合是否为空
        if (categorySet == null || categorySet.size() == 0) {
            // 从数据库返回
            categoryList = dao.findAll();
            // 将数据存储到redis
            for (Category c : categoryList) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }

        } else {
            categoryList = new ArrayList<Category>();
            for (Tuple s : categorySet) {
                Category c = new Category();
                c.setCname(s.getElement());
                c.setCid((int)s.getScore());
                categoryList.add(c);
            }
        }

        return categoryList;
    }
}
