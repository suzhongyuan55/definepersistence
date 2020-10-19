package com.szy.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.szy.io.Resources;
import com.szy.pojo.DataBaseConfiguration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @Author Suzy
 * @Date 2020-10-18
 */
public class XMLConfigBuilder {

    private DataBaseConfiguration configuration;

    /**
     * 初始化时加载数据库信息
     */
    public XMLConfigBuilder() {
        this.configuration = new DataBaseConfiguration();
    }

    /**
     * 使用dom4j将配置解析封装为Configuration
     *
     * @param inputStream
     * @return
     */
    public DataBaseConfiguration parseConfig(InputStream inputStream) throws Exception {
        Document document = new SAXReader().read(inputStream);
        // 1.数据库配置解析 ：<configuration>
        Element rootElement = document.getRootElement();
        List<Element> dataBaseElement = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        dataBaseElement.forEach(element -> {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        });

        // c3p0连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);

        // 2.mapper文件解析
        List<Element> mapperElement = rootElement.selectNodes("//mapper");
        mapperElement.forEach(element -> {
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        });

        return null;
    }
}
