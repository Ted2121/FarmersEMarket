package db_access.DaoInterfaces;

import model.Product;
import model.Provider;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDao {
    //TODO auto generated key or not?
    //int createProduct(Product objectToInsert) throws SQLException;
    void updateProduct(Product objectToUpdate) throws SQLException;
    void deleteProduct(Product objectToDelete) throws SQLException;
    ArrayList<Product> findAllProducts() throws SQLException;
    ArrayList<Product> findProductsByProvider(Provider provider) throws SQLException;

}
