package com.zzxx.travel.dao;

import com.zzxx.travel.domain.User;

public interface UserDao {
    User findByUsername(String username);

    void save(User u);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
