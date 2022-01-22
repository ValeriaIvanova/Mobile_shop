package com.example.myshop.Model;

public class Product {

    private String Pname, description, price, image, category, id, date, time;

    public Product(){

    }

    public Product(String Pname, String description, String price, String image, String category, String id, String date, String time) {
        this.Pname = Pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        this.Pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return id;
    }

    public void setPid(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
