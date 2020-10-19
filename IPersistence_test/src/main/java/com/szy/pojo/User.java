package com.szy.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author Suzy
 * @Date 2020-10-19
 */
@Data
@Accessors(chain = true)
public class User {

    private Integer id;

    private String name;
}
