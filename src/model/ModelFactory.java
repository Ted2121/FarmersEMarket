package model;


import java.time.format.DateTimeFormatter;

public class ModelFactory {
    private ModelFactory(){}

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public static DateTimeFormatter getFormat() {
        return FORMAT;
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
}
