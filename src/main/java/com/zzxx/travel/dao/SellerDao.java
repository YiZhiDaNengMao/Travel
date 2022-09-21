package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Seller;

public interface SellerDao {
    Seller findBySid(int rid);
}
