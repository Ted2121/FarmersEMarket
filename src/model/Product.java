package model;

public class Product {
    private int id;
    private String productName;
    private double purchasingPrice;
    private double sellingPrice;
    public enum Unit{L, KG}
    private Unit unit;
    public enum WeightCategory{ONE, FIVE, TEN}
    private int weightCategory;


    public Product(String productName, double purchasingPrice, double sellingPrice, WeightCategory weightCategoryOption, Unit unit) {
        this.productName = productName;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        this.unit = unit;
        switch (weightCategoryOption){
            case ONE -> this.weightCategory = 1;
            case FIVE -> this.weightCategory = 5;
            case TEN -> this.weightCategory = 10;
        }
    }

    public Product(int id, String productName, double purchasingPrice, double sellingPrice, WeightCategory weightCategoryOption, Unit unit) {
        this.id = id;
        this.productName = productName;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        this.unit = unit;
        switch (weightCategoryOption) {
            case ONE -> this.weightCategory = 1;
            case FIVE -> this.weightCategory = 5;
            case TEN -> this.weightCategory = 10;
        }
    }

    public Product(String productName, double purchasingPrice, double sellingPrice, Unit unit) {
        this.productName = productName;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        this.unit = unit;
    }

    public Product(int id, String productName, double purchasingPrice, double sellingPrice, Unit unit) {
        this.id = id;
        this.productName = productName;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        this.unit = unit;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
