package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.RouteService;
import com.zzxx.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService rs = new RouteServiceImpl();

    public void findPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //新增 获取路线名称 - rname
        String rname = request.getParameter("rname");
        System.out.println(rname);

        String _cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");

        if (_cid == null || _cid.equals("")) {
            _cid = "5";
        }
        if (currentPage == null || currentPage.equals("")) {
            currentPage = "1";
        }
        if (rows == null || rows.equals("")) {
            rows = "8";
        }
        int cid = Integer.parseInt(_cid);
        PageBean<Route> pb = rs.findByPage(cid, currentPage, rows, rname);
        writeValue(response, pb);
    }

    public void findDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String _rid = request.getParameter("rid");
        int rid = Integer.parseInt(_rid);
        Route route = rs.findByRid(rid);
        writeValue(response,route);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String _rid = request.getParameter("rid");
        int rid = Integer.parseInt(_rid);

        User user = (User)request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();

        //用户未登录，无法收藏
        if(user == null) {
            info.setFlag(false);
        } else {
            //用户已登录，返回收藏信息
            boolean isFavor = rs.isFavorite(rid,user.getUid());
            info.setFlag(isFavor);
        }
        writeValue(response,info);
    }

    public void favorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String _rid = request.getParameter("rid");
        int rid = Integer.parseInt(_rid);

        User user = (User)request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();

        //用户未登录，无法收藏
        if(user == null) {
            info.setFlag(false);
        } else {
            //用户已登录，返回收藏信息
            rs.favorite(rid, user.getUid());
            info.setFlag(true);
        }
        writeValue(response,info);
    }

}
