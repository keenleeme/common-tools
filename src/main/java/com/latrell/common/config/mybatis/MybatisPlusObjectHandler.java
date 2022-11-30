package com.latrell.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * 自定义字段属性自动填充逻辑
 *
 * @author liz
 * @date 2022/10/12-10:53
 */
public class MybatisPlusObjectHandler implements MetaObjectHandler {

    private static final Logger logger = LoggerFactory.getLogger(MybatisPlusObjectHandler.class);

    /**
     * 通用字段：创建时间
     */
    private static final String CREATED_TIME = "createdTime";
    /**
     * 通用字段：更新时间
     */
    private static final String UPDATED_TIME = "updatedTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            if (this.getFieldValByName(CREATED_TIME, metaObject) == null) {
                metaObject.setValue(CREATED_TIME, currentTime);
            }
        } catch (Exception e) {
            logger.warn("mybatis set created_time error. " + e);
        }

        try {
            if (this.getFieldValByName(UPDATED_TIME, metaObject) == null) {
                metaObject.setValue(UPDATED_TIME, currentTime);
            }
        } catch (Exception e) {
            logger.warn("mybatis set updated_time error. " + e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            metaObject.setValue(UPDATED_TIME, LocalDateTime.now());
        } catch (Exception e) {
            logger.warn("mybatis set updated_time error. " + e);
        }
    }

    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        // 覆盖默认的填充策略，即使对象有值，依然填充，解决updated_time有值时无法自动更新的问题
        try {
            Object obj = fieldVal.get();
            if (obj != null) {
                metaObject.setValue(fieldName, fieldVal);
            }
        } catch (Exception e) {
            logger.warn("mybatis 更新自动填充字段异常 " + e);
        }
        return this;
    }
}
