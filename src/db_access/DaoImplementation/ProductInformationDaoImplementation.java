package db_access.DaoImplementation;

import java.sql.SQLException;
import java.util.ArrayList;

import db_access.DaoInterfaces.ProductInformationDao;
import model.Product;
import model.ProductInformation;

public class ProductInformationDaoImplementation implements ProductInformationDao {

	@Override
	public void createProductInformation(ProductInformation objectToInsert) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProductInformation(ProductInformation objectToUpdate) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProductInformation(ProductInformation objectToDelete) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ProductInformation> findAllProductInformationEntries() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductInformation findProductInformationByProduct(Product product) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductInformation findProductInformationByProductName(String productName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductInformation findProductInformationByProductId(int productId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
