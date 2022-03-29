package db_access.DaoInterfaces;

import model.Product;
import model.Storage;
import model.Provider;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StorageDao {
    int createStorage(Storage objectToInsert) throws SQLException;
    boolean updateStorage(Storage objectToUpdate) throws SQLException;
    boolean deleteStorage(Storage objectToDelete) throws SQLException;
    ArrayList<Storage> findAllTuples() throws SQLException;
    Storage findTupleByProduct(Product product) throws SQLException;
    Storage findTupleByProductId(int id) throws SQLException;
}
