package com.szy.sqlsession;

import com.szy.pojo.DataBaseConfiguration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author Suzy
 * @Date 2020-10-18
 */
public class DefaultSqlSession implements SqlSession {

    private DataBaseConfiguration configuration;

    public DefaultSqlSession(DataBaseConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) {
        SimpleQueryExecutor simpleQueryExecutor = new SimpleQueryExecutor();
        simpleQueryExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
        return null;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) {
        List<Object> result = selectList(statementId, params);
        if (result.size() > 1) {
            throw new RuntimeException("查询结果过多");
        }
        return (T) result.get(0);
    }

    @Override
    public <T> T getInvoke(Class<?> mapperClass) {
        Object o = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return null;
            }
        });


        return null;
    }
}
