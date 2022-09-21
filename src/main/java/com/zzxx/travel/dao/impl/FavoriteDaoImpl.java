package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.FavoriteDao;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int isFavorite(int rid, int uid) {
        String sql = "select count(*) from tab_favorite where rid = ? and uid = ?";
        return template.queryForObject(sql, Integer.class, rid, uid);

    }

    @Override
    public void favorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }
}
