package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db_access.DBConnection;
import db_access.DaoInterfaces.ProductDao;
import model.ModelFactory;
import model.Product;
import model.Product.Unit;
import model.Product.WeightCategory;

public class ProductDaoImplementation implements ProductDao {

	Connection connection = DBConnection.getInstance().getDBCon();
	
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
		String query = "select * from Product where Name=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, productName);
		ResultSet rs = statement.executeQuery();
		rs.next();
		return ModelFactory.getProductModel(rs.getInt("PK_idProduct"), productName, rs.getDouble("PurchasingPrice"), rs.getDouble("SellingPrice"), WeightCategory.valueOf(rs.getString("WeightCategory")), Unit.valueOf(rs.getString("Unit")));
	}

	@Override
	public Product findProductById(int productId) throws SQLException {
		// TODO Auto-generated method stub
		String query = "select * from Product where Pk_idProduct=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, productId);
		ResultSet rs = statement.executeQuery();
		rs.next();
		return ModelFactory.getProductModel(productId, rs.getString("Name"), rs.getDouble("PurchasingPrice"), rs.getDouble("SellingPrice"), WeightCategory.valueOf(rs.getString("WeightCategory")), Unit.valueOf(rs.getString("Unit")));
	}
}
