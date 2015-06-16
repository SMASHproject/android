package com.spm.cafe529.Struct;

/**
 * Created by 성호 on 2015-04-10.
 */
public class Menu {
    private String imagepath;
    private String name;
    private int price;

    public Menu(String name, int price, String path) {
        this.imagepath = path;
        this.name = name;
        this.price = price;
    }


    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
