package db_access.DaoInterfaces;

import model.Product;
import model.ProductInformation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductInformationDao {
    int createProductInformation(ProductInformation objectToInsert) throws SQLException;
    void updateProductInformation(ProductInformation objectToUpdate) throws SQLException;
    void deleteProductInformation(ProductInformation objectToDelete) throws SQLException;
    ArrayList<ProductInformation> findAllProductInformationEntries() throws SQLException;
    ProductInformation findProductInformationByProduct(Product product) throws SQLException;
    ProductInformation findProductInformationByProductName(String productName) throws SQLException;
    ProductInformation findProductInformationByProductId(int productId) throws SQLException;
}