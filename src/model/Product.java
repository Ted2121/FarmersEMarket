package model;

public class Product {
    private int id;
    private String name;
    private double purchasingPrice;
    private double sellingPrice;
    public enum WeightCategory{ONE, FIVE, TEN}
    private int weightCategory;


    public Product(String name, double purchasingPrice, double sellingPrice, WeightCategory weightCategoryOption) {
        this.name = name;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        switch (weightCategoryOption){
            case ONE -> this.weightCategory = 1;
            case FIVE -> this.weightCategory = 5;
            case TEN -> this.weightCategory = 10;
        }
    }

    public Product(int id, String name, double purchasingPrice, double sellingPrice, WeightCategory weightCategoryOption) {
        this.id = id;
        this.name = name;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        switch (weightCategoryOption) {
            case ONE -> this.weightCategory = 1;
            case FIVE -> this.weightCategory = 5;
            case TEN -> this.weightCategory = 10;
        }
    }

    public Product(String name, double purchasingPrice, double sellingPrice) {
        this.name = name;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
    }

    public Product(int id, String name, double purchasingPrice, double sellingPrice) {
        this.id = id;
        this.name = name;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
    }



    public int getWeightCategory() {
        return weightCategory;
    }

    public void setWeightCategory(WeightCategory weightCategoryOption) {

        switch (weightCategoryOption){
            case ONE -> this.weightCategory = 1;
            case FIVE -> this.weightCategory = 5;
            case TEN -> this.weightCategory = 10;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(double purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
