package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.FavoriteDao;
import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.dao.RouteImgDao;
import com.zzxx.travel.dao.SellerDao;
import com.zzxx.travel.dao.impl.FavoriteDaoImpl;
import com.zzxx.travel.dao.impl.RouteDaoImpl;
import com.zzxx.travel.dao.impl.RouteImgDaoImpl;
import com.zzxx.travel.dao.impl.SellerDaoImpl;
import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.RouteImg;
import com.zzxx.travel.domain.Seller;
import com.zzxx.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao rdao = new RouteDaoImpl();
    private RouteImgDao ridao = new RouteImgDaoImpl();
    private SellerDao sdao = new SellerDaoImpl();

    private FavoriteDao fdao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> findByPage(int cid, String currentPage, String rows, String rname) {
        int current = Integer.parseInt(currentPage);
        int row = Integer.parseInt(rows);
        if(current <= 0) {
            current = 1;
        }
        int totalCount = rdao.findTotalCountByCid(cid, rname);
        int totalPage = totalCount / row + ((totalCount % row == 0) ? 0 : 1);
        if(current > totalPage){
            current = totalPage;
        }
        int start = (current - 1) * row;
        List<Route> list = rdao.findPageList(cid,start,row,rname);

        return new PageBean<Route>(totalCount,totalPage,list,current,row);
    }

    @Override
    public Route findByRid(int rid) {
        Route route = rdao.findByRid(rid);
        List<RouteImg> list = ridao.findByRid(rid);
        Seller seller = sdao.findBySid(route.getSid());

        route.setRouteImgList(list);
        route.setSeller(seller);
        return route;
    }

    @Override
    public boolean isFavorite(int rid, int uid) {
        int isFavor = fdao.isFavorite(rid,uid);
        return isFavor != 0;
    }

    @Override
    public void favorite(int rid, int uid) {
        fdao.favorite(rid, uid);
    }


}
