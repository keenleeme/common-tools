package com.latrell.common.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-plus 配置信息
 *
 * @author liz
 * @date 2022/10/12-10:39
 */
@Configuration
public class MybatisPlusConfiguration {

    public MybatisPlusConfiguration(ObjectMapper objectMapper) {
        JacksonTypeHandler.setObjectMapper(objectMapper);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页时的Count优化，利用JSqlParser来解析并优化Count语句
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 每页极限大小，设置一般最大1000条，但如果要拉取全部则可能传个很大的数字，暂时限制最大10000条，以控制内训占用
        paginationInnerInterceptor.setMaxLimit(10000L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    /**
     * Mybatis-Plus 保存对象时的字段自动填充相关逻辑
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisPlusObjectHandler();
    }

}
