package db_access.DaoInterfaces;

import model.Product;
import model.ProductInformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductInformationDao {
    void createProductInformation(ProductInformation objectToInsert) throws SQLException;
    void updateProductInformation(ProductInformation objectToUpdate) throws SQLException;
    void deleteProductInformation(ProductInformation objectToDelete) throws SQLException;
    List<ProductInformation> findAllProductInformation(boolean retrieveProduct) throws SQLException, Exception;
    ProductInformation findProductInformationByProduct(Product product, boolean retrieveProduct) throws SQLException, Exception;
    List<ProductInformation> findProductInformationByProductName(String productName, boolean retrieveProduct) throws SQLException, Exception;
    ProductInformation findProductInformationByProductId(int productId, boolean retrieveProduct) throws SQLException, Exception;
    void addQuantityToProduct(Product product, int quantity) throws SQLException;
    void removeQuantityToProduct(Product product, int quantity) throws SQLException;
}
