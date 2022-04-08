package db_access.DaoInterfaces;

import model.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProviderDao {
    Person findPersonById(int PersonId) throws SQLException;
    ArrayList<Person> findAllPersons() throws SQLException;
    int createPerson(Person objectToInsert) throws SQLException;
    void updatePerson(Person objectToUpdate) throws SQLException;
    void deletePerson(Person objectToDelete) throws SQLException;
    Person findPersonByFullName(String fullName) throws SQLException;
}
