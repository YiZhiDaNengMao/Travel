package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.Category;
import com.zzxx.travel.service.CategoryService;
import com.zzxx.travel.service.impl.CategoryServiceImpl;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    CategoryService cs = new CategoryServiceImpl();
    public void findAll(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List<Category> list = cs.findAll();
        writeValue(response,list);
    }
}
