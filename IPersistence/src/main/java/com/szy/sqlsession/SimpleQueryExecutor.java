package com.szy.sqlsession;

import com.szy.config.BoundSql;
import com.szy.pojo.DataBaseConfiguration;
import com.szy.pojo.MappedStatement;
import com.szy.utils.GenericTokenParser;
import com.szy.utils.ParameterMapping;
import com.szy.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.List;

/**
 * @Author Suzy
 * @Date 2020-10-19
 */
public class SimpleQueryExecutor implements QueryExecutor {

    public <E> List<E> query(DataBaseConfiguration configuration, MappedStatement statement, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException {
        // 1.建立连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2.获取sql
        String sql = statement.getSql();

        // 3.解析/转换 sql， sql中占位符替换操作
        BoundSql boundSql = getBoundSql(sql);

        // 4.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 5.设置参数
        String paramterType = statement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(0);
            String content = parameterMapping.getContent();

            // 反射获取属性
            Field declaredField = paramterTypeClass.getDeclaredField(content);
            // 设置暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }

        // 6.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> classType = getClassType(statement.getResultType());
        Object o = classType.newInstance();

        // 7.遍历封装结果集
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段值
                Object value = resultSet.getObject(columnName);

                // 使用反射或内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
        }


        return null;
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        Class<?> aClass = null;
        if (paramterType != null) {
            aClass = Class.forName(paramterType);
        }
        return aClass;
    }


    // TODO 待分析
    private BoundSql getBoundSql(String sql) {
        // 标记处理器
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // 解析出的sql
        String parse = genericTokenParser.parse(sql);

        // #{}解析出的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parse, parameterMappings);
        return boundSql;
    }
}
