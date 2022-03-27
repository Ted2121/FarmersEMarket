package model;

public class Product {
    private int id;
    private String name;
    private double purchasingPrice;
    private double sellingPrice;
    public enum WeightCategory{ONE, FIVE, TEN}
    private WeightCategory weightCategoryOption;
    private int weightCategory;


    public Product(String name, double purchasingPrice, double sellingPrice, WeightCategory weightCategoryOption) {
        this.name = name;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        this.weightCategoryOption = weightCategoryOption;
        switch (weightCategoryOption){
            case ONE -> this.weightCategory = 1;
            case FIVE -> this.weightCategory = 5;
            case TEN -> this.weightCategory = 10;
        }


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
}
