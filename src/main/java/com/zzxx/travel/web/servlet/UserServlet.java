package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    public void register(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(response,info);
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
        UserService service = new UserServiceImpl();
        boolean isRegister = service.isRegister(user);
        info.setFlag(isRegister);
        if(isRegister == false){
            System.out.println("该用户已注册！");
        }
        String json = writeValueAsString(info);
        response.getWriter().write(json);
    }

    public void active(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取激活码code
        String code = request.getParameter("code");
        System.out.println(code);
        //激活用户
        UserService service = new UserServiceImpl();
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

        //检验验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            writeValue(response,info);
            return;
        }
        //获取用户输入的账号密码，封装到user对象
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        //调用service方法进行登录
        UserService service = new UserServiceImpl();
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
        writeValue(response,info);
    }

    public void findUser(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user = request.getSession().getAttribute("user");
        //3.新增状态 - 判断当前用户是否登录状态
        ResultInfo info = new ResultInfo();
        if (user == null){
            //表示没有登录
            info.setFlag(false);
        }else {
            //表示用户已经登录
            info.setFlag(true);
            info.setData(user);
        }
        writeValue(response,info);
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
