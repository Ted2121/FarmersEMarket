package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.ProductDao;
import model.LineItem;
import model.ModelFactory;
import model.Product;
import model.ProductInformation;
import model.Provider;
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
	
	public List<Product> buildObjects(ResultSet rs, boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception {
		List<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			Product product = buildObject(rs);
			if(retrieveLineItems) {
				List<LineItem> retrievedLineItemsLinkedToThisProduct = DaoFactory.getLineItemDao().findLineItemsByProduct(product, false);
				product.setRelatedLineItems(retrievedLineItemsLinkedToThisProduct);
			}
			
			if(retrieveProductInformation) {
				ProductInformation retrievedProductInforamtionLinkedToThisProduct = DaoFactory.getProductInformationDao().findProductInformationByProduct(product, false);
				product.setRelatedProductInformation(retrievedProductInforamtionLinkedToThisProduct);
			}
			list.add(product);
			
		}
		return list;
	}
	
	@Override
	public List<Product> findAllProducts(boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception {
		String query = "SELECT * FROM Product";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		return buildObjects(rs, retrieveLineItems, retrieveProductInformation);
	}

	@Override
	public List<Product> findProductByProductName(String productName, boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception {
		//TODO changing the methods to retrieve more than 1 results.
		//If you retrieve potatoes, you can retrive 3 products, potatoes of 1kg, 5kg and 10kg
		String query = "SELECT * FROM Product WHERE [Name]=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, productName);
		ResultSet rs = statement.executeQuery();
		return buildObjects(rs, retrieveLineItems, retrieveProductInformation);
	}

	@Override
	public Product findProductById(int productId, boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception {
		String query = "SELECT * FROM Product WHERE Pk_idProduct=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, productId);
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			Product product = buildObject(rs);
			if(retrieveLineItems) {
				List<LineItem> retrievedLineItemsLinkedToThisProduct = DaoFactory.getLineItemDao().findLineItemsByProduct(product, false);
				product.setRelatedLineItems(retrievedLineItemsLinkedToThisProduct);
			}
			
			if(retrieveProductInformation) {
				ProductInformation retrievedProductInforamtionLinkedToThisProduct = DaoFactory.getProductInformationDao().findProductInformationByProduct(product, false);
				product.setRelatedProductInformation(retrievedProductInforamtionLinkedToThisProduct);
			}
			return product;
		}
		return null;
	}

	@Override
	public List<Product> findProductsByPartialName(String partialName, boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception {
		String sqlFindProviderStatement = "SELECT * FROM Product WHERE [Name] LIKE ?";
		PreparedStatement preparedFindProviderStatement = connection.prepareStatement(sqlFindProviderStatement);
		preparedFindProviderStatement.setString(1, "%" + partialName + "%");
		ResultSet rs = preparedFindProviderStatement.executeQuery();
		List<Product> retrievedProviders = buildObjects(rs, retrieveLineItems, retrieveProductInformation);
		return retrievedProviders;
	}
	
	@Override
	public void createProduct(Product objectToInsert) throws SQLException {
		String query = "INSERT INTO Product VALUES(?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, objectToInsert.getProductName());
		statement.setDouble(2, objectToInsert.getPurchasingPrice());
		statement.setDouble(3, objectToInsert.getSellingPrice());
		statement.setString(4, objectToInsert.getUnit());
		statement.setInt(5, objectToInsert.getWeightCategory());
		statement.executeUpdate();
		
		ResultSet tableContainingGeneratedIds = statement.getGeneratedKeys();
		int generatedId = 0;
		while(tableContainingGeneratedIds.next()) {
			generatedId = tableContainingGeneratedIds.getInt(1);
		}
		
		objectToInsert.setId(generatedId);
	}
	
	@Override
	public void updateProduct(Product objectToUpdate) throws SQLException {
		String query = "UPDATE Product SET [Name]=?, PurchasingPrice=?, SellingPrice=?, Unit=?, WeightCategory=? WHERE PK_idProduct=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, objectToUpdate.getProductName());
		statement.setDouble(2, objectToUpdate.getPurchasingPrice());
		statement.setDouble(3, objectToUpdate.getSellingPrice());
		statement.setString(4, objectToUpdate.getUnit());
		statement.setInt(5, objectToUpdate.getWeightCategory());
		statement.setInt(6, objectToUpdate.getId());
		statement.executeUpdate();
	}

	@Override
	public void deleteProduct(Product objectToDelete) throws SQLException {
		String query = "DELETE FROM Product WHERE PK_idProduct=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, objectToDelete.getId());
		statement.executeUpdate();
	}

	
}
