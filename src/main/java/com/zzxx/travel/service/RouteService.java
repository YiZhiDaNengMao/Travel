package com.zzxx.travel.service;

import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.Route;

public interface RouteService {
    PageBean<Route> findByPage(int cid, String currentPage, String rows, String rname);

    Route findByRid(int rid);

    boolean isFavorite(int rid, int uid);

    void favorite(int rid, int uid);
}
