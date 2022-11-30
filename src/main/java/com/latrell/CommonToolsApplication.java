package com.latrell;

import com.latrell.common.config.banner.VersionBanner;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 *
 * @author liz
 * @date 2022/10/10-20:27
 */
@EnableSwagger2
@MapperScan(basePackages = "com.latrell.test.mapper")
@SpringBootApplication
public class CommonToolsApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommonToolsApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplicationBuilder(CommonToolsApplication.class)
                .banner(new VersionBanner())
                .build();
        springApplication.run(args);
        logger.info("application started!");
    }

}
