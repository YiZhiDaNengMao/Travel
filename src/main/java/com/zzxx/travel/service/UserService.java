package com.zzxx.travel.service;

import com.zzxx.travel.domain.User;

public interface UserService {
    boolean isRegister(User user);

    boolean active(String code);

    User login(User user);
}
