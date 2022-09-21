package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    List<Route> findPageList(int cid, int start, int row, String rname);

    int findTotalCountByCid(int cid, String rname);

    Route findByRid(int rid);
}
