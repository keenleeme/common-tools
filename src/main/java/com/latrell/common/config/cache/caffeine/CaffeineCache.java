package com.latrell.common.config.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine 缓存
 *
 * @author liz
 * @date 2022/12/2-13:49
 */
public class CaffeineCache {

    private final Cache<String, Object> cache;

    public CaffeineCache() {
        this.cache = this.buildCache();
    }

    private Cache<String, Object> buildCache() {
        return Caffeine.newBuilder().expireAfterAccess(1L, TimeUnit.MINUTES).maximumSize(10_000L).build();
    }

    public void collectData() {
        cache.get("id_name", k -> {
           return new Object();
        });
    }

}
