package com.zzxx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.TravelService;
import com.zzxx.travel.service.impl.TravelServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private TravelService service = new TravelServiceImpl();

    public void register(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf8");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            mapper.writeValue(response.getWriter(),info);
            return;
        }

        //1.接收参数封装到user
        User user = new User();
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        //2.调用service方法进行用户注册验证
        TravelService service = new TravelServiceImpl();
        boolean isRegister = service.isRegister(user);
        info.setFlag(isRegister);
        if(isRegister == false){
            info.setErrorMsg("该用户已注册！");
        }
        String json = mapper.writeValueAsString(info);
        response.getWriter().write(json);
    }

    public void active(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取激活码code
        String code = request.getParameter("code");
        System.out.println(code);
        //激活用户
        TravelService service = new TravelServiceImpl();
        boolean isActive = service.active(code);
        //user不为null就激活
        if(isActive){
            //激活成功
            response.sendRedirect("/travel/active_ok.html");
        } else {
            //用户未注册，激活失败
            response.sendRedirect("/travel/active_ok.html");
        }
    }

    public void login(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        //将登录成功/失败信息封装到 info 对象
        ResultInfo info = new ResultInfo();
        //把info对象转换成json传给前端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf8");

        //检验验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            mapper.writeValue(response.getOutputStream(),info);
            return;
        }
        //获取用户输入的账号密码，封装到user对象
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        //调用service方法进行登录
        TravelService service = new TravelServiceImpl();
        User u = service.login(user);

        if(u == null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误，请重试！");
        } else {
            if(u.getStatus().equals("Y")){
                request.getSession().setAttribute("user",u);
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg("账户尚未激活！");
            }
        }

        String json = mapper.writeValueAsString(info);
        response.getWriter().write(json);
    }

    public void findUser(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf8");
        mapper.writeValue(response.getOutputStream(),user);
    }

    public void exit(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.删除user域中的数据
        request.getSession().removeAttribute("user");
        //2.跳转到login.html
        response.sendRedirect
                (request.getContextPath() + "/login.html");
    }
}
