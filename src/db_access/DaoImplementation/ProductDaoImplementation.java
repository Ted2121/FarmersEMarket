package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db_access.DBConnection;
import db_access.DaoInterfaces.ProductDao;
import model.ModelFactory;
import model.Product;
import model.ProductInformation;
import model.PurchaseOrder;
import model.Product.Unit;
import model.Product.WeightCategory;

public class ProductDaoImplementation implements ProductDao {

	Connection connection = DBConnection.getInstance().getDBCon();
	
	public Product buildObject(ResultSet rs) throws SQLException {
		int result = rs.getInt("WeightCategory");
		WeightCategory weightCategory = null;
		switch(result) {
			case 1 -> weightCategory = WeightCategory.ONE;
			case 5 -> weightCategory = WeightCategory.FIVE;
			case 10 -> weightCategory = WeightCategory.TEN;
		}
		Product builtObject = ModelFactory.getProductModel(rs.getInt("PK_idProduct"), rs.getString("Name"), 
				rs.getInt("PurchasingPrice"), rs.getInt("SellingPrice"),weightCategory, Unit.valueOf(rs.getString("Unit")));
		return builtObject;
	}
	
	public List<Product> buildObjects(ResultSet rs) throws SQLException {
		List<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			list.add(buildObject(rs));
		}
		return list;
	}
	
	@Override
	public void createProduct(Product objectToInsert) throws SQLException {
		// TODO Auto-generated method stub
		String query = "insert into Product values(?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, objectToInsert.getProductName());
		statement.setDouble(2, objectToInsert.getPurchasingPrice());
		statement.setDouble(3, objectToInsert.getSellingPrice());
		statement.setString(4, objectToInsert.getUnit());
		statement.setInt(5, objectToInsert.getWeightCategory());
		int row = statement.executeUpdate();
	}
	


	@Override
	public void updateProduct(Product objectToUpdate) throws SQLException {
		// TODO Auto-generated method stub
		String query = "update Product set Name=?, PurchasingPrice=?, SellingPrice=?, Unit=?, WeightCategory=? where PK_idProduct=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, objectToUpdate.getProductName());
		statement.setDouble(2, objectToUpdate.getPurchasingPrice());
		statement.setDouble(3, objectToUpdate.getSellingPrice());
		statement.setString(4, objectToUpdate.getUnit());
		statement.setInt(5, objectToUpdate.getWeightCategory());
		statement.setInt(6, objectToUpdate.getId());
		int row = statement.executeUpdate();
	}

	@Override
	public void deleteProduct(Product objectToDelete) throws SQLException {
		// TODO Auto-generated method stub
		String query = "delete from Product where PK_idProduct=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, objectToDelete.getId());
		int row = statement.executeUpdate();
	}

	@Override
	public List<Product> findAllProducts() throws SQLException {
		// TODO Auto-generated method stub
		List<Product> list = new ArrayList<Product>();
		String query = "select * from Product";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		return buildObjects(rs);
	}

	@Override
	public Product findProductByProductName(String productName) throws SQLException {
		// TODO Auto-generated method stub
		String query = "select * from Product where Name=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, productName);
		ResultSet rs = statement.executeQuery();
		Product product = null;
		while(rs.next()) {
			product =  buildObject(rs);
		}
		return product;
	}

	@Override
	public Product findProductById(int productId) throws SQLException {
		// TODO Auto-generated method stub
		String query = "select * from Product where Pk_idProduct=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, productId);
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			return buildObject(rs);
		}
		return null;
	}
}
