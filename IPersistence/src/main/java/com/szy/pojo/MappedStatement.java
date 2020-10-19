package com.szy.pojo;

import lombok.Data;

/**
 * @Author Suzy
 * @Date 2020-10-18
 */
@Data
public class MappedStatement {

    // id标识
    private String id;

    // 返回值类型
    private String resultType;

    // 参数值类型
    private String paramterType;

    // sql语句
    private String sql;
}
