package com.szy.sqlsession;

import java.util.List;

/**
 * @Author Suzy
 * @Date 2020-10-18
 * 创建SqlSession接口
 */
public interface SqlSession {

    /**
     * 查询所有
     * @param statementId
     * @param params
     * @param <E>
     * @return
     */
    <E>List<E> selectList(String statementId, Object... params);

    /**
     * 查询单个信息
     * @param statementId
     * @param params
     * @param <T>
     */
    <T>T selectOne(String statementId, Object... params);

    /**
     * 获取代理对象
     * @param <T>
     * @return
     */
    <T>T getInvoke(Class<?> mapperClass);
}
