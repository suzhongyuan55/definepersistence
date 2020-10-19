package com.szy.io;

import com.szy.pojo.User;
import com.szy.sqlsession.SqlSession;
import com.szy.sqlsession.SqlSessionFactory;
import com.szy.sqlsession.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @Author Suzy
 * @Date 2020-10-18
 */
public class IPersistenceTest {

    public void test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfigure.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(1).setName("李四");
        User resultUser = sqlSession.selectOne("user.selectOne", user);

    }
}
