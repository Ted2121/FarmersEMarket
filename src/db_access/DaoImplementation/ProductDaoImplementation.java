package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_access.*;
import db_access.DaoInterfaces.ProductDao;
import model.*;
import model.Product.*;

public class ProductDaoImplementation implements ProductDao {

	Connection connection = DBConnection.getInstance().getDBCon();
	
	private Product buildObject(ResultSet rs) throws SQLException {
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
	
	private Product buildObjectSubset(ResultSet rs) throws SQLException {
		int result = rs.getInt("WeightCategory");
		WeightCategory weightCategory = null;
		switch(result) {
			case 1 -> weightCategory = WeightCategory.ONE;
			case 5 -> weightCategory = WeightCategory.FIVE;
			case 10 -> weightCategory = WeightCategory.TEN;
		}
		Product builtObjectSubset = ModelFactory.getProductSubsetModel(rs.getInt("PK_idProduct"), rs.getString("Name"),weightCategory, Unit.valueOf(rs.getString("Unit")));
		return builtObjectSubset;
	}
	
	private List<Product> buildObjects(ResultSet rs, boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception {
		List<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			Product product = buildObject(rs);
			if(retrieveLineItems) {
				List<LineItem> retrievedLineItemsLinkedToThisProduct = new ArrayList<>();
				retrievedLineItemsLinkedToThisProduct.addAll(DaoFactory.getLineItemDao().findLineItemsByProduct(product, false));
				product.setRelatedLineItems(retrievedLineItemsLinkedToThisProduct);
			}
			
			if(retrieveProductInformation) {
				ProductInformation retrievedProductInforamtionLinkedToThisProduct = DaoFactory.getProductInformationDao().findProductInformationByProduct(product, false);
				if(retrievedProductInforamtionLinkedToThisProduct == null) {
					retrievedProductInforamtionLinkedToThisProduct = new ProductInformation(0, 0, 0);
				}
				
				product.setRelatedProductInformation(retrievedProductInforamtionLinkedToThisProduct);
			}
			list.add(product);
			
		}
		return list;
	}
	
	private List<Product> buildObjectsSubset(ResultSet rs) throws SQLException, Exception {
		List<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			Product productSubset = buildObjectSubset(rs);
			list.add(productSubset);
		}
		return list;
	}
	
	@Override
	public List<Product> findAllProductSubset() throws SQLException, Exception{
		String query = "SELECT PK_idProduct, [Name], Unit, WeightCategory FROM Product";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		return buildObjectsSubset(rs);
	}
	
	@Override
	public List<Product> findAllProducts(boolean retrieveLineItems, boolean retrieveProductInformation) throws SQLException, Exception {
		String query = "SELECT * FROM Product";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		return buildObjects(rs, retrieveLineItems, retrieveProductInformation);
	}

	@Override
	public List<Product> findProductByProductName(String productName, boolean retrieveLineItems, boolean retrieveProductInformation) throws Exception {
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
		Product retrievedProduct = null;
		List<Product> retrievedList = buildObjects(rs, retrieveLineItems, retrieveProductInformation);
		if(retrievedList.size()>0)
			retrievedProduct = retrievedList.get(0);
		if(retrievedList.size()>1) {
			throw new Exception("More than 1 item in the retrieved list of Product");
		}
		return retrievedProduct;
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
