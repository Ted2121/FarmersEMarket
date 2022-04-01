package db_access.DaoInterfaces;

import model.Product;
import model.ProductInformation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StorageDao {
    int createStorage(ProductInformation objectToInsert) throws SQLException;
    void updateStorage(ProductInformation objectToUpdate) throws SQLException;
    void deleteStorage(ProductInformation objectToDelete) throws SQLException;
    ArrayList<ProductInformation> findAllTuples() throws SQLException;
    ProductInformation findTupleByProduct(Product product) throws SQLException;
    ProductInformation findTupleByProductId(int id) throws SQLException;
}
