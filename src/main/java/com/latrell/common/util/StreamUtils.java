package com.latrell.common.util;

import java.io.Closeable;
import java.io.OutputStream;

/**
 * 流相关处理工具类， 可以在不抛任何异常的情况下写入和读取流
 *
 * @author liz
 * @date 2022/10/12-17:47
 */
public class StreamUtils {

    public StreamUtils() {
    }

    public static void closeQuietly(Closeable stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        } catch (Exception ignore) {
            // no op
        }
    }

    public static void write(OutputStream outputStream, int data) {
        if (outputStream == null) {
            return;
        }
        try {
            outputStream.write(data);
        } catch (Exception ignore) {
            // no op
        }
    }

    public static void write(OutputStream outputStream, byte[] data) {
        if (outputStream == null) {
            return;
        }
        try {
            outputStream.write(data);
        } catch (Exception ignore) {
            // no op
        }
    }

    public static void write(OutputStream outputStream, byte[] b, int off, int len) {
        if (outputStream == null) {
            return;
        }
        try {
            outputStream.write(b, off, len);
        } catch (Exception ignore) {
            // no op
        }
    }


}
