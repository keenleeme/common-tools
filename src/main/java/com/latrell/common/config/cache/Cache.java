package com.latrell.common.config.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 缓存接口
 *
 * @author liz
 * @date 2022/10/19-11:09
 */
public interface Cache {

    String getId();

    int getSize();

    void putObject(Object key, Object value);

    Object getObject(Object key);

    Object removeObject(Object key);

    void clear();

    ReadWriteLock getReadWriteLock();

}
