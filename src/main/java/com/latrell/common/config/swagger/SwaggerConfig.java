package com.latrell.common.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * swagger配置文件
 * http://localhost:8180/swagger-ui.html API访问地址
 *
 * @author liz
 * @date 2022-10-11 11:08:36
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.latrell.test.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo())
                .alternateTypeRules( //自定义规则，如果遇到DeferredResult，则把泛型类转成json
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)));
    }

    public ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("通用工具swagger接口文档")
                .description("通用工具swagger接口文档，详细信息......")
                .version("1.0.0")
                .contact(new Contact("通用工具", "blog.csdn.net", "zhen.li@dbappsecurity.com.cn"))
                .license("The Apache License")
                .licenseUrl("blog.csdn.net")
                .build();
    }

}
