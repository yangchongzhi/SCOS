package es.source.code.model;

public class Food {
    private String id;
    private String name;
    private double price;
    private int count;
    private String remark;
    private String image;


    public Food(String id, String name, Double price, int count, String remark, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.remark = remark;
        this.image = image;
    }

    public Food(String id, String name, double price, String remark, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.remark = remark;
        this.image = image;
    }

    public Food(String id, String name, Double price, int count, String remark) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.remark = remark;
    }

    public Food(String id, String name, Double price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Food(String id, int count) {
        this.id = id;
        this.count = count;
    }

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Food() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
