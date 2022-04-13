package db_access.DaoInterfaces;

import model.Person;
import model.Provider;

<<<<<<< Updated upstream
=======
import model.Provider;
>>>>>>> Stashed changes
import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {
<<<<<<< Updated upstream
    Provider findPersonById(int PersonId) throws SQLException;
    List<Provider> findAllPersons() throws SQLException;
    int createPerson(Provider objectToInsert) throws SQLException;
    void updatePerson(Provider objectToUpdate) throws SQLException;
    void deletePerson(Provider objectToDelete) throws SQLException;
    Provider findPersonByFullName(String fullName) throws SQLException;
=======
    Provider findProviderById(int ProviderId) throws SQLException;
    List<Provider> findAllProviders() throws SQLException;
    int createProvider(Provider objectToInsert) throws SQLException;
    void updateProvider(Provider objectToUpdate) throws SQLException;
    void deleteProvider(Provider objectToDelete) throws SQLException;
    Provider findProviderByFullName(String fullName) throws SQLException;
>>>>>>> Stashed changes
}
