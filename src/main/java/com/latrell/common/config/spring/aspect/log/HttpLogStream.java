package com.latrell.common.config.spring.aspect.log;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 接口日志记录的输出流，把HTTP请求或响应的流复制一部分到缓存中，并且有大小上限。此流是HTTP日志流，由WEB容器保证线程安全
 *
 * @author liz
 * @date 2022/10/13-9:24
 */
public class HttpLogStream extends OutputStream {

    private ByteArrayOutputStream bufferStream;
    private boolean isClosed = false;
    private int logThreshold;
    private boolean thresholdExceeded = false;

    public HttpLogStream(int threshold) {
        this.logThreshold = threshold;
        this.bufferStream = new ByteArrayOutputStream(threshold);
    }

    @Override
    public void write(final int b) throws IOException {
        if (this.isClosed) {
            return;
        }
        int availableBufferSize = getAvailableBufferSize(1);
        if (availableBufferSize > 0) {
            this.bufferStream.write(b);
        } else {
            this.thresholdExceeded = true;
            this.close();
        }
    }

    @Override
    public void write(@NotNull byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(@NotNull byte[] b, int off, int len) throws IOException {
        if (this.isClosed) {
            return;
        }
        int availableBufferSize = getAvailableBufferSize(1);
        if (availableBufferSize > 0) {
            this.bufferStream.write(b, off, availableBufferSize);
            if (len > availableBufferSize) {
                this.thresholdExceeded = true;
                this.close();
            }
        }
    }

    private int getAvailableBufferSize(int mallocSize) {
        if (this.thresholdExceeded) {
            return 0;
        }
        // 当前缓存流剩余空间
        int availableSize = logThreshold - this.bufferStream.size();
        return Math.min(mallocSize, availableSize);
    }

}
