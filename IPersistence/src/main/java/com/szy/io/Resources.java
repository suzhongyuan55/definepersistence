package com.szy.io;

import java.io.InputStream;

/**
 * @Author Suzy
 * @Date 2020-10-18
 * @description 1.加载配置信息
 */
public class Resources {

    /**
     * 根据配置文件加载成字节输入流，存储在内存中
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path) {
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}
