package com.zzxx.travel.dao;

public interface FavoriteDao {
    int isFavorite(int rid, int uid);

    void favorite(int rid, int uid);
}
