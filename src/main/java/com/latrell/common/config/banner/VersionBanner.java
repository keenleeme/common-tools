package com.latrell.common.config.banner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ResourceBanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * banner 图打印配置
 *
 * @author liz
 * @date 2022/10/20-14:36
 */
public class VersionBanner extends ResourceBanner {

    private static final Logger logger = LoggerFactory.getLogger(VersionBanner.class);

    /**
     * Banner 文件名
     */
    private static final String BANNER_FILE_NAME = "common-banner.txt";

    public VersionBanner() {
        super(new DefaultResourceLoader(null).getResource(BANNER_FILE_NAME));
    }

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        try {
            String banner = readClassPathBanner(BANNER_FILE_NAME);
            if (banner == null) {
                return;
            }
            out.println(banner);
        } catch (Exception e) {
            logger.warn("Banner not printable", e);
        }
    }

    private String readClassPathBanner(String fileName) {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        // 文件内容
        String fileContent;
        // 获取classPath下的文件输入流
        try (InputStream inputStream = classPathResource.getInputStream()) {
            // 把文件输入流的内容读入到 fileContent
            fileContent = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.warn("读取banner文件异常：{}", e);
            fileContent = null;
        }
        return fileContent;
    }

}
