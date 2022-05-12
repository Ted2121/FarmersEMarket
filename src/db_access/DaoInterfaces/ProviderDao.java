package db_access.DaoInterfaces;

import model.Provider;

import java.sql.SQLException;
import java.util.List;

public interface ProviderDao extends ContainSubsetDao<Provider> {

	Provider findProviderById(int providerId, boolean retrievePurchaseOrder) throws SQLException, Exception;
	Provider findProviderByFullName(String fullName, boolean retrievePurchaseOrder) throws SQLException, Exception;
	List<Provider> findAllProviders(boolean retrievePurchaseOrder) throws SQLException, Exception;
	List<Provider> findProvidersByName(String name, boolean retrievePurchaseOrder) throws SQLException, Exception;
    void createProvider(Provider objectToInsert) throws SQLException;
    void updateProvider(Provider objectToUpdate) throws SQLException;
    void deleteProvider(Provider objectToDelete) throws SQLException;	
}
