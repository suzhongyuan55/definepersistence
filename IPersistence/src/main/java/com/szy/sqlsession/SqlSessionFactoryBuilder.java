package com.szy.sqlsession;

import com.szy.config.XMLConfigBuilder;
import com.szy.pojo.DataBaseConfiguration;
import java.io.InputStream;

/**
 * @Author Suzy
 * @Date 2020-10-18
 * @description 解析配置文件信息
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws Exception {
        // 1.使用dom4j解析配置并封装
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        DataBaseConfiguration configuration = xmlConfigBuilder.parseConfig(inputStream);

        // 2.创建sqlSessionFactory对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
