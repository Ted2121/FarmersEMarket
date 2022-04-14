package db_access.DaoInterfaces;

import model.Product;
import model.Provider;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDao {

    void createProduct(Product objectToInsert) throws SQLException;
    void updateProduct(Product objectToUpdate) throws SQLException;
    void deleteProduct(Product objectToDelete) throws SQLException;
    ArrayList<Product> findAllProducts() throws SQLException;
    Product findProductByProductName(String productName) throws SQLException;
    Product findProductById(int productId) throws SQLException;

}
