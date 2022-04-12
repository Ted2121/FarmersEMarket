package db_access.DaoInterfaces;

import model.Person;
import model.Provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProviderDao {
    Provider findPersonById(int PersonId) throws SQLException;
    List<Provider> findAllPersons() throws SQLException;
    int createPerson(Provider objectToInsert) throws SQLException;
    void updatePerson(Provider objectToUpdate) throws SQLException;
    void deletePerson(Provider objectToDelete) throws SQLException;
    Provider findPersonByFullName(String fullName) throws SQLException;
}
