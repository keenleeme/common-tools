package com.latrell.common.config.spring.aspect.log;

import com.latrell.common.util.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 请求体记录包装类
 *
 * @author liz
 * @date 2022/10/12-16:53
 */
public class RequestLoggingWrapper extends HttpServletRequestWrapper {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingWrapper.class);

    private TeeServletInputStream teeServletInputStream;

    private final HttpLogStream copyStream;

    /**
     * 包装起来的reader，用于处理getReader的情况
     */
    private BufferedReader reader;

    public RequestLoggingWrapper(HttpServletRequest request, int logThreshold) {
        super(request);
        this.copyStream = new HttpLogStream(logThreshold);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.teeServletInputStream == null) {
            ServletInputStream servletInputStream = super.getInputStream();
            this.teeServletInputStream = new TeeServletInputStream(servletInputStream, copyStream);
        }
        return this.teeServletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (this.reader == null) {
            this.reader = new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
        }
        return this.reader;
    }

    /**
     * 强制刷新请求流的记录信息，输出到磁盘上
     */
    void forceFlush() {
        StreamUtils.closeQuietly(this.copyStream);
    }

}
