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
import model.Person;
import model.Provider;

public class ProviderDaoImplementation implements ProviderDao {
	//TODO this class needs to be checked
	Connection connectionDB = DBConnection.getInstance().getDBCon();

	private Provider buildObject(ResultSet rs) throws SQLException{
		Provider builtObject = new model.Provider(rs.getInt("id"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("city"),
				rs.getString("country"));
		return builtObject;
	}
	
	private ArrayList<Provider> buildObjects(ResultSet rs) throws SQLException{
		ArrayList<Provider> providerList = new ArrayList<>();
		while(rs.next()) {
			providerList.add(buildObject(rs));
		}
		
		return providerList;
	}
	
	@Override
	public model.Provider findPersonById(int personId) throws SQLException {
		String query = "SLEECT * FROM Provider WHERE PK_idProviders = ?";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setLong(1, personId);
		ResultSet rs = preparedSelectStatement.executeQuery();
		model.Provider retrievedPerson = null;
		while(rs.next()) {
			retrievedPerson = buildObject(rs);
		}
		return retrievedPerson;
	}

	@Override
	public List<model.Provider> findAllPersons() throws SQLException {
		String query = "SELECT * FROM Provider";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		ResultSet rs = preparedSelectStatement.executeQuery();
		ArrayList<model.Provider> retrievedPersonList = buildObjects(rs);
		
		return retrievedPersonList;
	}

	@Override
	public int createPerson(Provider objectToInsert) throws SQLException {
		String sqlInsertPersonStatement = "INSERT INTO Provider (FirstName, LastName, Country, City) + VALUES(?,?,?,?)";
		PreparedStatement preparedInsertPersonStatementWithGeneratedKey = connectionDB.prepareStatement(sqlInsertPersonStatement, Statement.RETURN_GENERATED_KEYS);
		preparedInsertPersonStatementWithGeneratedKey.setString(1, objectToInsert.getFirstName());
		preparedInsertPersonStatementWithGeneratedKey.setString(2, objectToInsert.getLastName());
		preparedInsertPersonStatementWithGeneratedKey.setString(3, objectToInsert.getCountry());
		preparedInsertPersonStatementWithGeneratedKey.setString(2, objectToInsert.getCity());
		
		preparedInsertPersonStatementWithGeneratedKey.executeUpdate();
		ResultSet tableContainingGenratedIds = preparedInsertPersonStatementWithGeneratedKey.getGeneratedKeys();
		int generatedId = 0;
		while(tableContainingGenratedIds.next()) {
			generatedId = tableContainingGenratedIds.getInt(1);
		}
		objectToInsert.setId(generatedId);
		
		return generatedId;
	}

	@Override
	public void updatePerson(Provider objectToUpdate) throws SQLException {
		String sqlUpdatePersonStatement = "UPDATE Provider SET FirstName = ?, LastName = ?, Country = ?, City = ?";
		PreparedStatement preparedUpdatePersonStatement = connectionDB.prepareStatement(sqlUpdatePersonStatement);
		preparedUpdatePersonStatement.setString(1, objectToUpdate.getFirstName());
		preparedUpdatePersonStatement.setString(2, objectToUpdate.getLastName());
		preparedUpdatePersonStatement.setString(3, objectToUpdate.getCountry());
		preparedUpdatePersonStatement.setString(4, objectToUpdate.getCity());
		
		preparedUpdatePersonStatement.execute();
	}

	@Override
	public void deletePerson(Provider objectToDelete) throws SQLException {
		String sqlDeletePersonStatement = "DELETE FROM Provider WHERE id = ?";
		PreparedStatement preparedDeletePersonStatement = connectionDB.prepareStatement(sqlDeletePersonStatement);
		preparedDeletePersonStatement.setInt(1, objectToDelete.getId());
		preparedDeletePersonStatement.execute();
		
	}

	@Override
	public Provider findPersonByFullName(String fullName) throws SQLException {
		String sqlFindPersonStatement = "SELECT * FROM Provider WHERE FirstName = ?, LastName = ?";
		PreparedStatement preparedFindPersonStatement = connectionDB.prepareStatement(sqlFindPersonStatement);
		String[] fullNameArray = fullName.split(" ");
		preparedFindPersonStatement.setString(1, fullNameArray[0]);
		preparedFindPersonStatement.setString(2, fullNameArray[1]);
		ResultSet rs = preparedFindPersonStatement.executeQuery();
		Provider retrievedProvider = null;
		while(rs.next()) {
			retrievedProvider = buildObject(rs);
		}
		return retrievedProvider;
	}


}
