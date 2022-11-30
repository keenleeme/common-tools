package com.latrell.common.config.cache.decorator;

import com.latrell.common.config.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 定时缓存 -- 装饰器模式
 *
 * @author liz
 * @date 2022/10/19-14:47
 */
public class ScheduledCache implements Cache {

    private final Cache delegate;
    protected long clearInterval;
    protected long lastClear;

    public ScheduledCache(Cache delegate) {
        this.delegate = delegate;
        this.clearInterval = 60 * 60 * 1000L; // 1 小时
        this.lastClear = System.currentTimeMillis();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public int getSize() {
        clearWhenStale();
        return delegate.getSize();
    }

    @Override
    public void putObject(Object key, Object value) {
        clearWhenStale();
        delegate.putObject(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return clearWhenStale() ? null : delegate.getObject(key);
    }

    @Override
    public Object removeObject(Object key) {
        clearWhenStale();
        return delegate.removeObject(key);
    }

    @Override
    public void clear() {
        lastClear = System.currentTimeMillis();
        delegate.clear();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    private boolean clearWhenStale() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClear - clearInterval > 0) {
            clear();
            return true;
        }
        return false;
    }

}
