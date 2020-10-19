package com.szy.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Suzy
 * @Date 2020-10-18
 * @description 2.创建对象接收存储配置
 */
@Data
public class DataBaseConfiguration {

    /**
     * 数据库配置信息
     */
    private DataSource dataSource;

    /**
     * sql配置信息
     * key: statmentId (namespace Id)
     * value: mappedStatment对象
     */
    Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
}
