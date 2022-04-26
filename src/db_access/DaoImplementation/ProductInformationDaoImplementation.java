package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db_access.DBConnection;
import db_access.DaoInterfaces.ProductDao;
import db_access.DaoInterfaces.ProductInformationDao;
import model.ModelFactory;
import model.Product;
import model.ProductInformation;
import model.Product.Unit;
import model.Product.WeightCategory;

public class ProductInformationDaoImplementation implements ProductInformationDao {
	
	Connection connection = DBConnection.getInstance().getDBCon();
	
	public ProductInformation buildObject(ResultSet rs) throws SQLException {
		ProductInformation builtObject = ModelFactory.getProductInformationModel(rs.getInt("LocationCode"), rs.getInt("Quantity"), rs.getInt("PK_FK_Product"));
		return builtObject;
	}
	
	public List<ProductInformation> buildObjects(ResultSet rs) throws SQLException {
		List<ProductInformation> list = new ArrayList<ProductInformation>();
		while(rs.next()) {
			list.add(buildObject(rs));
		}
		return list;
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
	public List<ProductInformation> findAllProductInformationEntries() throws SQLException {
		String query = "SELECT * FROM ProductInformation";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		return buildObjects(rs);
	}

	@Override
	public ProductInformation findProductInformationByProduct(Product product) throws SQLException {
		String query = "SELECT * FROM ProductInformation WHERE PK_FK_Product=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, product.getId());
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			return buildObject(rs);
		}
		return null;
	}

	@Override
	public ProductInformation findProductInformationByProductName(String productName) throws SQLException {
		// TODO Once ProductDao.findProductBuProductName will be fixed, this part need to be fixed as well
		ProductDao dao = new ProductDaoImplementation();
		Product product = dao.findProductByProductName(productName);
		return findProductInformationByProduct(product);
	}

	@Override
	public ProductInformation findProductInformationByProductId(int productId) throws SQLException {
		//If you have the product ID you can go to the findById without using the productDao
		ProductDao dao = new ProductDaoImplementation();
		Product product = dao.findProductById(productId);
		return findProductInformationByProduct(product);
	}

	@Override
	public void addQuantityToProduct(Product product, int quantity) throws SQLException {
		// TODO Better to use a single SQL statement than 2
		ProductInformation productInformation = findProductInformationByProduct(product);
		productInformation.setQuantity(productInformation.getQuantity()+quantity);
		updateProductInformation(productInformation);
	}

	@Override
	public void removeQuantityToProduct(Product product, int quantity) throws SQLException {
		// TODO Better to use a single SQL statement than 2
		ProductInformation productInformation = findProductInformationByProduct(product);
		quantity = productInformation.getQuantity()-quantity;
		productInformation.setQuantity(quantity);
		updateProductInformation(productInformation);
	}
	
	
}
