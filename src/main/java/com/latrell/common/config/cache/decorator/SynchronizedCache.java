package com.latrell.common.config.cache.decorator;

import com.latrell.common.config.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 加了锁的缓存 -- 装饰器模式
 *
 * @author liz
 * @date 2022/10/19-14:45
 */
public class SynchronizedCache implements Cache {

    private final Cache delegate;

    public SynchronizedCache(Cache delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public synchronized int getSize() {
        return delegate.getSize();
    }

    @Override
    public synchronized void putObject(Object key, Object value) {
        delegate.putObject(key, value);
    }

    @Override
    public synchronized Object getObject(Object key) {
        return delegate.getObject(key);
    }

    @Override
    public synchronized Object removeObject(Object key) {
        return delegate.removeObject(key);
    }

    @Override
    public synchronized void clear() {
        delegate.clear();
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
