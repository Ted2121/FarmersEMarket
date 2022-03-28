package db_access.DaoInterfaces;

import model.Provider;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProviderDao {
    Provider findProviderById(int id) throws SQLException;
    ArrayList<Provider> findAllProviders() throws SQLException;
    int createProvider(Provider objectToInsert) throws SQLException;
    boolean updateProvider(Provider objectToUpdate) throws SQLException;
    boolean deleteProvider(Provider objectToDelete) throws SQLException;
}
