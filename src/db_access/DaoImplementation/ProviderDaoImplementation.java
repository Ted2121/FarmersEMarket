package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_access.DBConnection;
import db_access.DaoInterfaces.ProviderDao;
import model.Provider;

public class ProviderDaoImplementation implements ProviderDao {
	//TODO this class needs to be checked
	Connection connectionDB = DBConnection.getInstance().getDBCon();

	private Provider buildObject(ResultSet rs) throws SQLException{
		Provider builtObject = new model.Provider(rs.getInt("id"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("city"),
				rs.getString("country"));
		return builtObject;
	}
	
	private List<Provider> buildObjects(ResultSet rs) throws SQLException{
		List<Provider> providerList = new ArrayList<>();
		while(rs.next()) {
			providerList.add(buildObject(rs));
		}
		
		return providerList;
	}
	
	@Override
	public Provider findProviderById(int providerId) throws SQLException {
		String query = "SLEECT * FROM Provider WHERE PK_idProviders = ?";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setLong(1, providerId);
		ResultSet rs = preparedSelectStatement.executeQuery();
		Provider retrievedPerson = null;
		while(rs.next()) {
			retrievedPerson = buildObject(rs);
		}
		return retrievedPerson;
	}

	@Override
	public List<Provider> findAllProviders() throws SQLException {
		String query = "SELECT * FROM Provider";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		ResultSet rs = preparedSelectStatement.executeQuery();
		List<Provider> retrievedProvidersList = buildObjects(rs);
		
		return retrievedProvidersList;
	}

	@Override
	public int createProvider(Provider objectToInsert) throws SQLException {
		String sqlInsertProviderStatement = "INSERT INTO Provider (FirstName, LastName, Country, City) + VALUES(?,?,?,?)";
		PreparedStatement preparedInsertProviderStatementWithGeneratedKey = connectionDB.prepareStatement(sqlInsertProviderStatement, Statement.RETURN_GENERATED_KEYS);
		preparedInsertProviderStatementWithGeneratedKey.setString(1, objectToInsert.getFirstName());
		preparedInsertProviderStatementWithGeneratedKey.setString(2, objectToInsert.getLastName());
		preparedInsertProviderStatementWithGeneratedKey.setString(3, objectToInsert.getCountry());
		preparedInsertProviderStatementWithGeneratedKey.setString(2, objectToInsert.getCity());
		
		preparedInsertProviderStatementWithGeneratedKey.executeUpdate();
		ResultSet tableContainingGeneratedIds = preparedInsertProviderStatementWithGeneratedKey.getGeneratedKeys();
		int generatedId = 0;
		while(tableContainingGeneratedIds.next()) {
			generatedId = tableContainingGeneratedIds.getInt(1);
		}
		objectToInsert.setId(generatedId);
		
		return generatedId;
	}

	@Override
	public void updateProvider(Provider objectToUpdate) throws SQLException {
		String sqlUpdatePersonStatement = "UPDATE Provider SET FirstName = ?, LastName = ?, Country = ?, City = ?";
		PreparedStatement preparedUpdateProviderStatement = connectionDB.prepareStatement(sqlUpdatePersonStatement);
		preparedUpdateProviderStatement.setString(1, objectToUpdate.getFirstName());
		preparedUpdateProviderStatement.setString(2, objectToUpdate.getLastName());
		preparedUpdateProviderStatement.setString(3, objectToUpdate.getCountry());
		preparedUpdateProviderStatement.setString(4, objectToUpdate.getCity());
		
		preparedUpdateProviderStatement.execute();
	}

	@Override
	public void deleteProvider(Provider objectToDelete) throws SQLException {
		String sqlDeleteProviderStatement = "DELETE FROM Provider WHERE id = ?";
		PreparedStatement preparedDeleteProviderStatement = connectionDB.prepareStatement(sqlDeleteProviderStatement);
		preparedDeleteProviderStatement.setInt(1, objectToDelete.getId());
		preparedDeleteProviderStatement.execute();
		
	}

	@Override
	public Provider findProviderByFullName(String fullName) throws SQLException {
		String sqlFindProviderStatement = "SELECT * FROM Provider WHERE FirstName = ?, LastName = ?";
		PreparedStatement preparedFindProviderStatement = connectionDB.prepareStatement(sqlFindProviderStatement);
		String[] fullNameArray = fullName.split(" ");
		preparedFindProviderStatement.setString(1, fullNameArray[0]);
		preparedFindProviderStatement.setString(2, fullNameArray[1]);
		ResultSet rs = preparedFindProviderStatement.executeQuery();
		Provider retrievedProvider = null;
		while(rs.next()) {
			retrievedProvider = buildObject(rs);
		}
		return retrievedProvider;
	}


}
