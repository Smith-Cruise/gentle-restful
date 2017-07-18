package com.gentle.util.sql;

import com.gentle.annotation.Db;

/**
 * Created by Smith on 2017/7/17.
 */
public class TestEntity implements SqlDataInterface {
    @Db(fieldName = "id")
    private Integer id;

    @Db(fieldName = "dateline")
    private Long dateline;

    @Db(fieldName = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
