package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCountByCid(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1 =1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList<>();
        sb.append("and cid = ? ");
        params.add(cid);
        if(rname != null && rname.length() > 0) {
            sb.append("and rname like ? ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findPageList(int cid, int start, int row, String rname) {
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList<>();
        sb.append("and cid = ? ");
        params.add(cid);
        if (rname != null && rname.length() > 0) {
            sb.append("and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append("limit ?, ?");
        params.add(start);
        params.add(row);
        sql = sb.toString();
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),
                params.toArray());
    }

    @Override
    public Route findByRid(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
