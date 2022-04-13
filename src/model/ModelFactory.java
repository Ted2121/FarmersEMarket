package model;


import java.time.format.DateTimeFormatter;

import model.Product.Unit;
import model.Product.WeightCategory;

public class ModelFactory {
    private ModelFactory(){}

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public static DateTimeFormatter getFormat() {
        return FORMAT;
    }

    public static ProductInformation getProductInformationModel(int locationCode, int quantity, Product product) {
    	return new ProductInformation(locationCode, quantity, product);
    }
    
    public static Product getProductModelWithoutId(String name, double purchasingPrice, double sellingPrice, WeightCategory weightCategory, Unit unit) {
    	return new Product(name, purchasingPrice, sellingPrice, weightCategory, unit);
    }
    
    public static Product getProductModel(int id, String name, double purchasingPrice, double sellingPrice, WeightCategory weightCategory, Unit unit) {
    	return new Product(id, name, purchasingPrice, sellingPrice, weightCategory, unit);
    }
    
    public static Product getProductModelWithoutIdWithoutWeightedCategory(String name, double purchasingPrice, double sellingPrice, Unit unit) {
    	return new Product(name, purchasingPrice, sellingPrice, unit);
    }
    
    public static Product getProductModelWithoutWeightedCategory(int id, String name, double purchasingPrice, double sellingPrice, Unit unit) {
    	return new Product(id, name, purchasingPrice, sellingPrice, unit);
    }

    public static Person getProviderModel(int id, String firstName, String lastName, String city, String country){
        return new Provider(id, firstName, lastName, city, country);
    }
    
    public static Person getProviderModel(String firstName, String lastName, String city, String country){
        return new Provider(firstName, lastName, city, country);
    }

    public static Person getCustomerModel(int id, String firstName, String lastName, String city, String country, String address, int postalCode){

         return new Customer(id, firstName, lastName, city, country, address, postalCode);
    }
    
    public static Person getCustomerModel(String firstName, String lastName, String city, String country, String address, int postalCode){

        return new Customer(firstName, lastName, city, country, address, postalCode);
    }
    
    public static Order getPurchaseOrderModel(int id, Provider provider) {
    	return new PurchaseOrder(id, provider);
    }
    
    public static Order getPurchaseOrderModel(int id) {
    	return new PurchaseOrder(id);
    }
}
