package com.kylelainez.oop_project_v1;

import android.widget.ImageView;

public class Model {
    private String eat_menu_name;
    private String eat_menu_price;

    public String getEat_menu_shop() {
        return eat_menu_shop;
    }

    public void setEat_menu_shop(String eat_menu_shop) {
        this.eat_menu_shop = eat_menu_shop;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    private String eat_menu_shop;
    private ImageView imageView;

    public Model() {

    }

    public Model(String eat_menu_name, String eat_menu_price,String eat_menu_shop) {
        this.eat_menu_name = eat_menu_name;
        this.eat_menu_price = eat_menu_price;
        this.eat_menu_shop = eat_menu_shop;
    }

    public String getEat_menu_name() {
        return eat_menu_name;
    }

    public void setEat_menu_name(String eat_menu_name) {
        this.eat_menu_name = eat_menu_name;
    }

    public String getEat_menu_price() {
        return eat_menu_price;
    }

    public void setEat_menu_price(String eat_menu_price) {
        this.eat_menu_price = eat_menu_price;
    }
}
