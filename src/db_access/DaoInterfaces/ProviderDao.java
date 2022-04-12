package db_access.DaoInterfaces;

import model.Person;

import java.security.Provider;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProviderDao {
    Person findPersonById(int PersonId) throws SQLException;
    ArrayList<Provider> findAllPersons() throws SQLException;
    int createPerson(Provider objectToInsert) throws SQLException;
    void updatePerson(Provider objectToUpdate) throws SQLException;
    void deletePerson(Provider objectToDelete) throws SQLException;
    Person findPersonByFullName(String fullName) throws SQLException;
}
