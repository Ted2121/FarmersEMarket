package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_access.*;
import db_access.DaoInterfaces.ProviderDao;
import model.*;

public class ProviderDaoImplementation implements ProviderDao {

	Connection connectionDB = DBConnection.getInstance().getDBCon();

	private Provider buildObject(ResultSet rs) throws SQLException{
		Provider builtObject = ModelFactory.getProviderModel(rs.getInt("PK_idProvider"),rs.getString("FirstName"),rs.getString("LastName"),rs.getString("City"),
				rs.getString("Country"));
		return builtObject;
	}
	
	private Provider buildObjectSubset(ResultSet rs) throws SQLException {
		
		Provider builtObjectSubset = ModelFactory.getProviderSubsetModel(rs.getInt("PK_idProvider"), rs.getString("FirstName"),rs.getString("LastName"));
		return builtObjectSubset;
	}
	
	private List<Provider> buildObjects(ResultSet rs, boolean retrievePurchaseOrder) throws Exception{
		List<Provider> providerList = new ArrayList<>();
		while(rs.next()) {
			Provider retrievedProvider = buildObject(rs);
			if(retrievePurchaseOrder) {
				ArrayList<PurchaseOrder> linkedPurchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderByProviderId(rs.getInt("PK_IdProvider"), false, false);
				retrievedProvider.setPurchaseOrders(linkedPurchaseOrder);
			}
			
			providerList.add(retrievedProvider);
		}
		
		return providerList;
	}
	
	private List<Provider> buildObjectsSubset(ResultSet rs) throws SQLException, Exception {
		List<Provider> list = new ArrayList<Provider>();
		while(rs.next()) {
			Provider productSubset = buildObjectSubset(rs);
			list.add(productSubset);
		}
		return list;
	}
	
	@Override
	public Provider findProviderById(int providerId, boolean retrievePurchaseOrder) throws SQLException, Exception {
		String query = "SELECT * FROM Provider WHERE PK_idProvider = ?";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setInt(1, providerId);
		ResultSet rs = preparedSelectStatement.executeQuery();
		Provider retrievedProvider = null;
		List<Provider> retrievedList = buildObjects(rs, retrievePurchaseOrder);
		if(retrievedList.size()>0)
			retrievedProvider = retrievedList.get(0);
		if(retrievedList.size()>1) {
			throw new Exception("More than 1 item in the retrieved list of Provider");
		}
		return retrievedProvider;
	}

	@Override
	public Provider findProviderByFullName(String fullName, boolean retrievePurchaseOrder) throws SQLException, Exception {
		String sqlFindProviderStatement = "SELECT * FROM Provider WHERE (FirstName = ? AND LastName = ?) OR (LastName = ? AND FirstName = ?)";
		PreparedStatement preparedFindProviderStatement = connectionDB.prepareStatement(sqlFindProviderStatement);
		String[] fullNameArray = fullName.split(" ");
		preparedFindProviderStatement.setString(1, fullNameArray[0]);
		preparedFindProviderStatement.setString(2, fullNameArray[1]);
		preparedFindProviderStatement.setString(3, fullNameArray[0]);
		preparedFindProviderStatement.setString(4, fullNameArray[1]);
		ResultSet rs = preparedFindProviderStatement.executeQuery();
		Provider retrievedProvider = null;
		List<Provider> retrievedList = buildObjects(rs, retrievePurchaseOrder);
		retrievedProvider = retrievedList.get(0);
		if(retrievedList.size()>1) {
			throw new Exception("More than 1 item in the retrieved list of provider");
		}
		return retrievedProvider;
	}
	
	@Override
	public List<Provider> findProvidersByName(String name, boolean retrievePurchaseOrder) throws SQLException, Exception {
		String sqlFindProviderStatement = "SELECT * FROM Provider WHERE FirstName LIKE ? OR LastName LIKE ? "
				+ "OR CONCAT(FirstName, ' ', LastName) LIKE ? OR CONCAT(LastName, ' ', FirstName) LIKE ?";
		PreparedStatement preparedFindProviderStatement = connectionDB.prepareStatement(sqlFindProviderStatement);
		preparedFindProviderStatement.setString(1, "%" + name + "%");
		preparedFindProviderStatement.setString(2, "%" + name + "%");
		preparedFindProviderStatement.setString(3, "%" + name + "%");
		preparedFindProviderStatement.setString(4, "%" + name + "%");
		ResultSet rs = preparedFindProviderStatement.executeQuery();
		List<Provider> retrievedProviders = buildObjects(rs, retrievePurchaseOrder);
		return retrievedProviders;
	}
	
	@Override
	public List<Provider> findAllProviderSubset() throws SQLException, Exception {
		String query = "SELECT PK_idProvider, FirstName, LastName  FROM Provider";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		ResultSet rs = preparedSelectStatement.executeQuery();
		List<Provider> retrievedProvidersList = buildObjectsSubset(rs);
		
		return retrievedProvidersList;
	}
	
	@Override
	public List<Provider> findAllProviders(boolean retrievePurchaseOrder) throws SQLException, Exception {
		String query = "SELECT * FROM Provider";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		ResultSet rs = preparedSelectStatement.executeQuery();
		List<Provider> retrievedProvidersList = buildObjects(rs, retrievePurchaseOrder);
		
		return retrievedProvidersList;
	}

	@Override
	public void createProvider(Provider objectToInsert) throws SQLException {
		String sqlInsertProviderStatement = "INSERT INTO Provider (FirstName, LastName, Country, City) VALUES(?,?,?,?)";
		PreparedStatement preparedInsertProviderStatementWithGeneratedKey = connectionDB.prepareStatement(sqlInsertProviderStatement, Statement.RETURN_GENERATED_KEYS);
		preparedInsertProviderStatementWithGeneratedKey.setString(1, objectToInsert.getFirstName());
		preparedInsertProviderStatementWithGeneratedKey.setString(2, objectToInsert.getLastName());
		preparedInsertProviderStatementWithGeneratedKey.setString(3, objectToInsert.getCountry());
		preparedInsertProviderStatementWithGeneratedKey.setString(4, objectToInsert.getCity());
		
		preparedInsertProviderStatementWithGeneratedKey.executeUpdate();
		ResultSet tableContainingGeneratedIds = preparedInsertProviderStatementWithGeneratedKey.getGeneratedKeys();
		int generatedId = 0;
		while(tableContainingGeneratedIds.next()) {
			generatedId = tableContainingGeneratedIds.getInt(1);
		}
		objectToInsert.setId(generatedId);
		
		System.out.println(">> Provider added to the Database");
	}

	@Override
	public void updateProvider(Provider objectToUpdate) throws SQLException {
		String sqlUpdatePersonStatement = "UPDATE Provider SET FirstName = ?, LastName = ?, Country = ?, City = ? WHERE PK_idProvider = ?";
		PreparedStatement preparedUpdateProviderStatement = connectionDB.prepareStatement(sqlUpdatePersonStatement);
		preparedUpdateProviderStatement.setString(1, objectToUpdate.getFirstName());
		preparedUpdateProviderStatement.setString(2, objectToUpdate.getLastName());
		preparedUpdateProviderStatement.setString(3, objectToUpdate.getCountry());
		preparedUpdateProviderStatement.setString(4, objectToUpdate.getCity());
		preparedUpdateProviderStatement.setInt(5, objectToUpdate.getId());
		
		preparedUpdateProviderStatement.execute();
		System.out.println(">> Provider updated in the Database");
	}

	@Override
	public void deleteProvider(Provider objectToDelete) throws SQLException {
		String sqlDeleteProviderStatement = "DELETE FROM Provider WHERE PK_idProvider = ?";
		PreparedStatement preparedDeleteProviderStatement = connectionDB.prepareStatement(sqlDeleteProviderStatement);
		preparedDeleteProviderStatement.setInt(1, objectToDelete.getId());
		preparedDeleteProviderStatement.execute();
		System.out.println(">> Provider deleted in the Database");
	}

	


}
