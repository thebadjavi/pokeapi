package com.ounce.javi.cvapi.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class
Pokemon {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public float rating;



    private int number;
    private String name;
    private String url;

    private String type;

    public String getType() {
        return type;
    }

    public   String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
         String[] urlPartes = url.split("/");
         return Integer.parseInt(urlPartes[urlPartes.length - 1]);
        //return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }



    public void setType(String type) {
        this.type = type;
    }
}
