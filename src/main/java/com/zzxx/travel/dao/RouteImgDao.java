package com.zzxx.travel.dao;

import com.zzxx.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    List<RouteImg> findByRid(int rid);
}
