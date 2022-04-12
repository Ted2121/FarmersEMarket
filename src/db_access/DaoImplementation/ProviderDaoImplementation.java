package db_access.DaoImplementation;

import java.security.Provider;
import java.sql.SQLException;
import java.util.ArrayList;

import db_access.DaoInterfaces.ProviderDao;
import model.Person;

public class ProviderDaoImplementation implements ProviderDao {

	@Override
	public Person findPersonById(int PersonId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Provider> findAllPersons() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPerson(Provider objectToInsert) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updatePerson(Provider objectToUpdate) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePerson(Provider objectToDelete) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Person findPersonByFullName(String fullName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
