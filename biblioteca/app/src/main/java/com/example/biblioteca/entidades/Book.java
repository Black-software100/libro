package com.example.biblioteca.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Book {

    private  int id;

    private String dato;

    private String name;

    private Integer price;

    private Bitmap img;

    public String getDato() {
        return dato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDato(String dato) {
        this.dato = dato;
        try{
            byte[] byteCode = Base64.decode(dato,Base64.DEFAULT);
            this.img = BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
