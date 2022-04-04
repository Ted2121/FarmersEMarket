package db_access.DaoInterfaces;

import model.Provider;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProviderDao {
    Provider findProviderById(int id) throws SQLException;
    ArrayList<Provider> findAllProviders() throws SQLException;
    int createProvider(Provider objectToInsert) throws SQLException;
    void updateProvider(Provider objectToUpdate) throws SQLException;
    void deleteProvider(Provider objectToDelete) throws SQLException;
    Provider findProviderByFullName(String fullName) throws SQLException;
}