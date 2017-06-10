package gentle;

import com.gentle.annotation.Db;

/**
 * Created by Smith on 2017/6/3.
 */
public class TestEntity {

    @Db(fieldName = "id")
    int id;

    @Db(fieldName = "name")
    private String sName;

    @Db(fieldName = "price")
    private int price;

    @Db(fieldName = "dateline")
    private int dateline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDateline() {
        return dateline;
    }

    public void setDateline(int dateline) {
        this.dateline = dateline;
    }
}
