package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.CatetoryDao;
import com.zzxx.travel.dao.impl.CategoryDaoImpl;
import com.zzxx.travel.domain.Category;
import com.zzxx.travel.service.CategoryService;
import com.zzxx.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CatetoryDao cdao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        /*Jedis jedis = JedisUtil.getJedis();
        jedis.auth("jiang");

        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        //如果没有redis缓存，就从数据库获取list 并保存到 redis中
        if (categorys == null || categorys.size() == 0) {
            list = cdao.findAll();
            System.out.println("从mysql中获取数据...");
            for(Category c : list) {
                jedis.zadd("category",c.getCid(),c.getCname());
            }
            //存到redis缓存后更新categorys
            categorys = jedis.zrangeWithScores("category", 0, -1);
        } else {
            System.out.println("从redis中获取数据...");
        }
        // 把redis中的数据转换成list传回前端
        list = new ArrayList<Category>();
        for(Tuple tuple : categorys) {
            Category category = new Category();
            category.setCid((int) tuple.getScore());
            category.setCname(tuple.getElement());
            list.add(category);
        }
        return list;*/
        return cdao.findAll();
    }
}
