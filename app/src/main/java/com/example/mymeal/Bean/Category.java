package com.example.mymeal.Bean;

public class Category
{
    String imageUrl;
    String name,id;

    public Category(String imageUrl, String name) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Category(String imageUrl, String name, String id) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
