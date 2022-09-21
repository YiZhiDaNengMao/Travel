package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.dao.impl.UserDaoimpl;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.util.MailUtils;
import com.zzxx.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao udao = new UserDaoimpl();

    @Override
    public boolean isRegister(User user) {
        User u = udao.findByUsername(user.getUsername());
        if (u != null) {
            return false;
        }
        //注册成功，设置默认激活状态为'N'
        String code = UuidUtil.getUuid();
        System.out.println("生成的code" + code);
        user.setStatus("N");
        user.setCode(code);
        //发送激活邮件
        String text = "<a href='http://localhost/user/active?code=" + code + "'>点击此处激活您的账号</a>";
        boolean isActive = MailUtils.sendMail("jiangxstar@163.com", text, "账户激活");
        //保存注册的用户信息
        udao.save(user);
        return true;
    }

    @Override
    public boolean active(String code) {
        User u = udao.findByCode(code);
        if (u != null) {
            udao.updateStatus(u);
            return true;
        }
        return false;

    }

    @Override
    public User login(User user) {
        User u = udao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        return u;
    }
}
