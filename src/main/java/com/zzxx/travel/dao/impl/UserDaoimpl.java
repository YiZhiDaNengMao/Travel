package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoimpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String username) {
        String sql = "select * from tab_user where username = ?";
        User user = null;
        try {
            user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    username);
        } catch (DataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public void save(User u) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" +
                "values(?,?,?,?,?,?,?,?,?)";
        template.update(
                sql,u.getUsername(),u.getPassword(),u.getName(),
                u.getBirthday(), u.getSex(),u.getTelephone(),
                u.getEmail(),u.getStatus(),u.getCode());
    }

    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where code = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status='Y' where uid=?";
        template.update(sql, user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                    username, password);
        } catch (DataAccessException e) {
            return null;
        }
        return user;
    }
}
