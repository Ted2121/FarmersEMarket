package db_access.DaoInterfaces;

import model.LineItem;
import model.Product;
import model.Provider;

import javax.sound.sampled.Line;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDao {
    //TODO auto generated key or not?
    //int createProduct(Product objectToInsert) throws SQLException;
    boolean updateProduct(Product objectToUpdate) throws SQLException;
    boolean deleteProduct(Product objectToDelete) throws SQLException;
    ArrayList<Product> findAllProducts() throws SQLException;
    ArrayList<Product> findProductsByProvider(Provider provider) throws SQLException;

}
