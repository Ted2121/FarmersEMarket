package db_access.DaoInterfaces;

import java.util.List;


public interface ContainSubsetDao<T> {
	List<T> findAllSubset() throws Exception;
}




