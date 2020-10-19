package com.szy.sqlsession;

import com.szy.pojo.DataBaseConfiguration;
import com.szy.pojo.MappedStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author Suzy
 * @Date 2020-10-19
 */
public interface QueryExecutor {

    <E> List<E> query(DataBaseConfiguration configuration, MappedStatement statement, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException;
}
