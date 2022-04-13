package db_access.DaoInterfaces;

import model.Person;
import model.Provider;


import model.Provider;

import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {

    Provider findProviderById(int ProviderId) throws SQLException;
    List<Provider> findAllProviders() throws SQLException;
    void createProvider(Provider objectToInsert) throws SQLException;
    void updateProvider(Provider objectToUpdate) throws SQLException;
    void deleteProvider(Provider objectToDelete) throws SQLException;
    Provider findProviderByFullName(String fullName) throws SQLException;

}
