package model;


import java.util.List;

public class Product {
    private int id;
    private String productName;
    private double purchasingPrice;
    private double sellingPrice;
    public enum Unit{L, KG}
    private Unit unit;
    public enum WeightCategory{ONE, FIVE, TEN}
    private int weightCategory;
    private ProductInformation relatedProductInformation = null;
    private List<LineItem> relatedLineItems = null;

    public Product(int id, String productName, double purchasingPrice, double sellingPrice, WeightCategory weightCategoryOption, Unit unit) {
    	this(productName, purchasingPrice, sellingPrice, weightCategoryOption, unit);
    	this.id = id;
    }
    
    public Product(int id, String productName, WeightCategory weightCategoryOption, Unit unit) {
    	this.id = id;
    	this.productName = productName;
    	setWeightCategory(weightCategoryOption);
    	setUnit(unit);
    }
    
    public Product() {
    }
    
    public Product(String productName, double purchasingPrice, double sellingPrice, WeightCategory weightCategoryOption, Unit unit) {
        this(productName, purchasingPrice, sellingPrice, unit);
        switch (weightCategoryOption){
            case ONE -> this.weightCategory = 1;
            case FIVE -> this.weightCategory = 5;
            case TEN -> this.weightCategory = 10;
        }
    }
    
    public Product(int id, String productName, double purchasingPrice, double sellingPrice, Unit unit) {
    	this(productName, purchasingPrice, sellingPrice, unit);
    	this.id = id;
    }

    public Product(String productName, double purchasingPrice, double sellingPrice, Unit unit) {
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

    public String getUnit() {
        return unit.name();
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

	public ProductInformation getRelatedProductInformation() {
		return relatedProductInformation;
	}

	public void setRelatedProductInformation(ProductInformation relatedProductInformation) {
		this.relatedProductInformation = relatedProductInformation;
	}

	public List<LineItem> getRelatedLineItems() {
		return relatedLineItems;
	}

	public void setRelatedLineItems(List<LineItem> relatedLineItems) {
		this.relatedLineItems = relatedLineItems;
	}

	@Override
	public String toString() {
		return getProductName() + " " + getWeightCategory() + " " + getUnit();
	}
    
    
}
