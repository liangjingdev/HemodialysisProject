package com.liangjing.hemodialysisproject.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by liangjing on 2017/8/27.
 * <p>
 * function:IO流工具类
 */

public class IOUtil {

    /**
     * function:关闭流
     *
     * @param io
     * @return
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
