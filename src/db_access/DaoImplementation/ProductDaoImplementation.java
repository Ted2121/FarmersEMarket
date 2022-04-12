package db_access.DaoImplementation;

import java.sql.SQLException;
import java.util.ArrayList;

import db_access.DaoInterfaces.ProductDao;
import model.Product;

public class ProductDaoImplementation implements ProductDao {

	@Override
	public int createProduct(Product objectToInsert) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateProduct(Product objectToUpdate) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(Product objectToDelete) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Product> findAllProducts() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductByProductName(String productName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductById(int productId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
