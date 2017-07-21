package app.database;

import org.inlighting.gentle.annotation.Db;
import org.inlighting.gentle.util.sql.SqlDataInterface;

/**
 * Created by Smith on 2017/7/19.
 * 不得使用基本类型
 */
class TestEntity implements SqlDataInterface {
    @Db(fieldName = "id")
    private Integer id;

    @Db(fieldName = "name")
    private String name;

    @Db(fieldName = "dateline")
    private Long dateline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }
}
