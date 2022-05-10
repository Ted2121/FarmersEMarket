package db_access.DaoInterfaces;

import java.sql.SQLException;
import java.util.List;

import model.Customer;
import model.Product;

public interface ContainSubsetDao<T> {
	List<T> findAllSubset() throws Exception;
}
