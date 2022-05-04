package db_access.DaoInterfaces;

import model.Person;
import model.Provider;


import model.Provider;

import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {

	Provider findProviderById(int providerId, boolean retrievePurchaseOrder) throws SQLException, Exception;
	List<Provider> findAllProviders(boolean retrievePurchaseOrder) throws SQLException, Exception;
    void createProvider(Provider objectToInsert) throws SQLException;
    void updateProvider(Provider objectToUpdate) throws SQLException;
    void deleteProvider(Provider objectToDelete) throws SQLException;
    Provider findProviderByFullName(String fullName, boolean retrievePurchaseOrder) throws SQLException, Exception;
	List<Provider> findProvidersByName(String name, boolean retrievePurchaseOrder) throws SQLException, Exception;
	List<Provider> findAllProviderSubset() throws SQLException, Exception;
	
	
	

}
