package com.example.mymeal.Bean;

public class Cart
{
    int id;
    String foodItemId;
    String foodName,imageUrl;
    String foodPrice;
    int qunatity;

    public Cart(int id, String foodItemId, String foodName, String foodPrice, int qunatity,String imageUrl) {
        this.id = id;
        this.foodItemId = foodItemId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.qunatity = qunatity;
        this.imageUrl= imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(String foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getQunatity() {
        return qunatity;
    }

    public void setQunatity(int qunatity) {
        this.qunatity = qunatity;
    }
}
