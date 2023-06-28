package com.example.carveyourfood;

import android.os.Parcel;
import android.os.Parcelable;

public class Upload implements Parcelable {
    //private String n;
    private String image_u,item_n,item_desc,item_ing,user,item_c,cur_id;

    public Upload(){

    }
    public Upload(String image_uri, String item_name, String item_description, String item_ingredients, String item_cost, String user_id, String current_id){
        /*if(name.trim().equals("")){
            name="No Name";
        }*/
        //n=name;
        image_u=image_uri;
        item_n = item_name;
        item_desc = item_description;
        item_ing = item_ingredients;
        item_c = item_cost;
        user = user_id;
        cur_id = current_id;
    }

    protected Upload(Parcel in) {
        image_u = in.readString();
        item_n = in.readString();
        item_desc = in.readString();
        item_ing = in.readString();
        user = in.readString();
        item_c = in.readString();
        cur_id = in.readString();
    }

    public static final Creator<Upload> CREATOR = new Creator<Upload>() {
        @Override
        public Upload createFromParcel(Parcel in) {
            return new Upload(in);
        }

        @Override
        public Upload[] newArray(int size) {
            return new Upload[size];
        }
    };

    /*public String getName(){
            return n;
        }
        public void setName(String name){
            n = name;
        }*/
    public String getImageUri(){
        return image_u;
    }
    public void setImageUri(String image_uri){
        image_u = image_uri;
    }

    public String getItem_n() {
        return item_n;
    }

    public void setItem_n(String item_n) {
        this.item_n = item_n;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getItem_ing() {
        return item_ing;
    }

    public void setItem_ing(String item_ing) {
        this.item_ing = item_ing;
    }

    public String getItem_c() {
        return item_c;
    }

    public void setItem_c(String item_c) {
        this.item_c = item_c;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCur_id() {
        return cur_id;
    }

    public void setCur_id(String cur_id) {
        this.cur_id = cur_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image_u);
        dest.writeString(item_n);
        dest.writeString(item_desc);
        dest.writeString(item_ing);
        dest.writeString(user);
        dest.writeString(item_c);
        dest.writeString(cur_id);
    }
}
