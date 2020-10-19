package com.szy.sqlsession;

import com.szy.pojo.DataBaseConfiguration;

/**
 * @Author Suzy
 * @Date 2020-10-18
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private DataBaseConfiguration configuration;

    public DefaultSqlSessionFactory(DataBaseConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {

        return new DefaultSqlSession();
    }
}
