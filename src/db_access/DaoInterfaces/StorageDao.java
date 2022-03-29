package db_access.DaoInterfaces;

import model.Product;
import model.Storage;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StorageDao {
    int createStorage(Storage objectToInsert) throws SQLException;
    void updateStorage(Storage objectToUpdate) throws SQLException;
    void deleteStorage(Storage objectToDelete) throws SQLException;
    ArrayList<Storage> findAllTuples() throws SQLException;
    Storage findTupleByProduct(Product product) throws SQLException;
    Storage findTupleByProductId(int id) throws SQLException;
}
