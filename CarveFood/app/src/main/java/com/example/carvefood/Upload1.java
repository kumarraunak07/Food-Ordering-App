package com.example.carvefood;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Upload1 {
    String name,desc,ing,cost,extraing,extramsg,item_id;

    public Upload1(){}

    public Upload1(String name, String desc, String ing, String cost, String extraing, String extramsg,String item_id) {
        this.name = name;
        this.desc = desc;
        this.ing = ing;
        this.cost = cost;
        this.extraing = extraing;
        this.extramsg = extramsg;
        this.item_id = item_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getExtraing() {
        return extraing;
    }

    public void setExtraing(String extraing) {
        this.extraing = extraing;
    }

    public String getExtramsg() {
        return extramsg;
    }

    public void setExtramsg(String extramsg) {
        this.extramsg = extramsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }


}