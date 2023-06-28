package com.example.carvefood;

public class Upload2 {
    String name,type,ingredients,message,extras,contact,item_id;

    public Upload2(){}

    public Upload2(String name, String type, String ingredients, String message, String extras, String contact, String item_id) {
        this.name = name;
        this.type = type;
        this.ingredients = ingredients;
        this.message = message;
        this.extras = extras;
        this.contact = contact;
        this.item_id = item_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
