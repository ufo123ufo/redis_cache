package test.com.youguu.cache.pojo;

/**
 * Created by wangdong on 2016/1/12.
 */
public class Product {
    private int id;
    private String name;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private float price;


    public static Product get(){
        Product p = new Product();
        p.setId(1);
        p.setName("你好");
        p.setPrice(1.01F);
        return p;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
