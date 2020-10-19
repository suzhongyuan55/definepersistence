package com.szy.sqlsession;

/**
 * @Author Suzy
 * @Date 2020-10-18
 * @description 4.创建sqlSessionFactory对象
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
