package db_access.DaoInterfaces;

import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends ContainSubsetDao<Product> {
	Product findProductById(int productId, boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception;
    List<Product> findAllProducts(boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception;
    List<Product> findProductByProductName(String productName, boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception;
    List<Product> findProductsByPartialName(String partialName, boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception;
    void createProduct(Product objectToInsert) throws SQLException;
    void updateProduct(Product objectToUpdate) throws SQLException;
    void deleteProduct(Product objectToDelete) throws SQLException;
}
