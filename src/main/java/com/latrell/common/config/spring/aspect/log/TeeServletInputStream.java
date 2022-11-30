package com.latrell.common.config.spring.aspect.log;

import com.latrell.common.util.StreamUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 仿照Common-io中的TeeInputStream，跟随正常的InputStream，在读取时把流内容复制一份（类似流量镜像）
 *
 * @author liz
 * @date 2022/10/12-17:39
 */
public class TeeServletInputStream extends ServletInputStream {

    public static final int EOF = -1;
    private final ServletInputStream rawStream;
    private final OutputStream copyStream;

    public TeeServletInputStream(ServletInputStream rawStream, OutputStream copyStream) {
        this.rawStream = rawStream;
        this.copyStream = copyStream;
    }

    @Override
    public boolean isFinished() {
        return this.rawStream.isFinished();
    }

    @Override
    public boolean isReady() {
        return this.rawStream.isReady();
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        this.rawStream.setReadListener(readListener);
    }

    @Override
    public int read() throws IOException {
        final int ch = this.rawStream.read();
        if (ch != EOF) {
            StreamUtils.write(this.copyStream, ch);
        } else {
            StreamUtils.closeQuietly(this.copyStream);
        }
        return ch;
    }

    @Override
    public int read(@NotNull byte[] b) throws IOException {
        final int n = this.rawStream.read(b);
        if (n != EOF) {
            StreamUtils.write(this.copyStream, b, 0, n);
        }
        checkAllRead();
        return n;
    }

    @Override
    public int read(@NotNull byte[] b, int off, int len) throws IOException {
        final int n = this.rawStream.read(b, off, len);
        if (n != EOF) {
            StreamUtils.write(this.copyStream, b, off, n);
        }
        checkAllRead();
        return n;
    }

    private void checkAllRead() {
        try {
            if (this.rawStream.available() <= 0) {
                StreamUtils.closeQuietly(this.copyStream);
            }
        } catch (Exception ignore) {
        }
    }

}
