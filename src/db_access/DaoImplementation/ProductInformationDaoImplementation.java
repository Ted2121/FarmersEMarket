package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db_access.*;
import db_access.DaoInterfaces.ProductInformationDao;
import model.*;

public class ProductInformationDaoImplementation implements ProductInformationDao {
	
	Connection connection = DBConnection.getInstance().getDBCon();
	
	public ProductInformation buildObject(ResultSet rs) throws SQLException {
		ProductInformation builtObject = ModelFactory.getProductInformationModel(rs.getInt("LocationCode"), rs.getInt("Quantity"), rs.getInt("PK_FK_Product"));
		return builtObject;
	}
	
	public List<ProductInformation> buildObjects(ResultSet rs, boolean retrieveProduct) throws SQLException, Exception {
		List<ProductInformation> list = new ArrayList<ProductInformation>();
		while(rs.next()) {
			ProductInformation productInformation = buildObject(rs);
			if(retrieveProduct) {
				Product retrievedProductLinkedToThisProductInformation = DaoFactory.getProductDao().findProductById(productInformation.getId(), false, false);
				productInformation.setRelatedProduct(retrievedProductLinkedToThisProductInformation);
			}
			
			list.add(productInformation);
		}
		return list;
	}
	
	@Override
	public List<ProductInformation> findAllProductInformation(boolean retrieveProduct) throws SQLException, Exception {
		String query = "SELECT * FROM ProductInformation";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		return buildObjects(rs, retrieveProduct);
	}

	@Override
	public ProductInformation findProductInformationByProduct(Product product, boolean retrieveProduct) throws Exception {
		String query = "SELECT * FROM ProductInformation WHERE PK_FK_Product=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, product.getId());
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			ProductInformation productInformation = buildObjects(rs, retrieveProduct).get(0);
			return productInformation;
		}
		return null;
	}

	@Override
	public List<ProductInformation> findProductInformationByProductName(String productName, boolean retrieveProduct) throws SQLException, Exception {
		String query = "SELECT * FROM Product INNER JOIN ProductInformation ON PK_idProduct = ProductInformation.PK_FK_Product WHERE Product.[Name] = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, productName);
		ResultSet rs = statement.executeQuery();
		return buildObjects(rs, retrieveProduct);
	}

	@Override
	public ProductInformation findProductInformationByProductId(int productId, boolean retrieveProduct) throws SQLException, Exception {
		String query = "SELECT * FROM ProductInformation WHERE PK_FK_Product = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, productId);
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			ProductInformation productInformation = buildObjects(rs, retrieveProduct).get(0);
			return productInformation;
		}
		return null;
	}
	
	@Override
	public void createProductInformation(ProductInformation objectToInsert) throws SQLException {
		String query = "INSERT INTO ProductInformation VALUES(?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, objectToInsert.getId());
		statement.setInt(2, objectToInsert.getQuantity());
		statement.setInt(3, objectToInsert.getLocationCode());
		statement.executeUpdate();
	}

	@Override
	public void updateProductInformation(ProductInformation objectToUpdate) throws SQLException {
		String query = "UPDATE ProductInformation SET Quantity=?, LocationCode=? WHERE PK_FK_Product=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, objectToUpdate.getQuantity());
		statement.setInt(2, objectToUpdate.getLocationCode());
		statement.setInt(3, objectToUpdate.getId());
		statement.executeUpdate();
	}

	@Override
	public void deleteProductInformation(ProductInformation objectToDelete) throws SQLException {
		String query = "DELETE FROM ProductInformation WHERE PK_FK_Product=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, objectToDelete.getId());
		statement.executeUpdate();
	}

	@Override
	public void addQuantityToProduct(Product product, int quantity) throws SQLException {
		String addQuantity = "UPDATE ProductInformation SET quantity += ? WHERE PK_FK_Product = ?";
		PreparedStatement statement = connection.prepareStatement(addQuantity);
		statement.setInt(1, quantity);
		statement.setInt(2, product.getId());
		statement.executeUpdate();
		
	}

	@Override
	public void removeQuantityToProduct (Product product, int quantity) throws SQLException {
			String addQuantity = "UPDATE ProductInformation SET quantity -= ? WHERE PK_FK_Product = ?";
			PreparedStatement statement = connection.prepareStatement(addQuantity);
			statement.setInt(1, quantity);
			statement.setInt(2, product.getId());
			statement.executeUpdate();
		}

	}

