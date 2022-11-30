package com.latrell.common.config.cache;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存key，一般缓存框架的数据结构基本上都是 key -> value 方式存储
 *
 * @author liz
 * @date 2022/10/19-10:26
 */
public class CacheKey implements Cloneable, Serializable {

    private static final long serialVersionUID = 8819516144926580281L;

    private static final int DEFAULT_MULTIPLIER = 37;
    private static final int DEFAULT_HASHCODE = 17;

    private final int multiplier;
    private int hashcode;
    private int checksum;
    private int count;
    private List<Object> updateList;

    public CacheKey() {
        this.multiplier = DEFAULT_MULTIPLIER;
        this.hashcode = DEFAULT_HASHCODE;
        this.count = 0;
        this.updateList = new ArrayList<>();
    }

    public CacheKey(Object[] objects) {
        this();
        updateAll(objects);
    }

    public void update(Object object) {
        if (object != null && object.getClass().isArray()) {
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                Object element = Array.get(object, i);
                doUpdate(element);
            }
        } else {
            doUpdate(object);
        }
    }

    private void doUpdate(Object object) {
        // 计算HASH码
        int baseHashCode = object == null ? 1 : object.hashCode();
        // 参与 key 计算的参数数量
        count++;
        checksum += baseHashCode;
        baseHashCode *= count;
        hashcode = multiplier * hashcode + baseHashCode;

        updateList.add(object);
    }

    private void updateAll(Object[] objects) {
        for (Object object : objects) {
            update(object);
        }
    }

    public int getUpdateCount() {
        return updateList.size();
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CacheKey)) {
            return false;
        }
        final CacheKey cacheKey = (CacheKey) obj;
        if (hashcode != cacheKey.hashcode) {
            return false;
        }
        if (checksum != cacheKey.checksum) {
            return false;
        }
        if (count != cacheKey.count) {
            return false;
        }
        for (int i = 0; i < updateList.size(); i++) {
            Object thisObject = updateList.get(i);
            Object thatObject = cacheKey.updateList.get(i);
            if (thisObject == null) {
                if (thatObject != null) {
                    return false;
                }
            } else {
                if (!thisObject.equals(thatObject)) {
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CacheKey cloneCacheKey = (CacheKey) super.clone();
        cloneCacheKey.updateList = new ArrayList<>(updateList);
        return cloneCacheKey;
    }

    @Override
    public String toString() {
        StringBuilder returnValue = new StringBuilder().append(hashcode).append(':').append(checksum);
        for (Object obj : updateList) {
            returnValue.append(':').append(obj);
        }
        return returnValue.toString();
    }
}
