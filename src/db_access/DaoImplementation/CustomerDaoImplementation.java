package db_access.DaoImplementation;

import db_access.DBConnection;
import db_access.DaoInterfaces.CustomerDao;
import model.Customer;
import model.ModelFactory;
import model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImplementation implements CustomerDao {

        Connection dbCon = DBConnection.getInstance().getDBCon();
//    ProductDaoImplementation productDao = DaoFactory.getProductDao();

    private List<Person> buildObjects(ResultSet rs) throws SQLException {
        List<Person> customerList = new ArrayList<Person>();
        while (rs.next()) {
            customerList.add(buildObject(rs));
        }
        return customerList;
    }

    private Person buildObject(ResultSet rs) throws SQLException {
        Person builtCustomer = ModelFactory.getCustomerModel(
                rs.getInt("PK_idCustomer"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("City"),
                rs.getString("Country"),
                rs.getString("Address"),
                rs.getInt("PostalCode"));

        return builtCustomer;
    }

    @Override
    public Person findCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM Customer WHERE id = ?";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        preparedSelectStatement.setLong(1, customerId);
        ResultSet rs = preparedSelectStatement.executeQuery();
        Person retrievedCustomer = null;
        while(rs.next()) {
            retrievedCustomer =  buildObject(rs);
        }

        return retrievedCustomer;
    }

    @Override
    public List<Person> findAllCustomers() throws SQLException {
        String query = "SELECT * FROM Customer";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        ResultSet rs = preparedSelectStatement.executeQuery();
        List<Person> retrievedCustomerList = buildObjects(rs);

        return retrievedCustomerList;
    }

    @Override
    public int createCustomer(Customer objectToInsert) throws SQLException {
        String sqlInsertCustomerStatement = "INSERT INTO Customer(FirstName, LastName, Address, PostalCode, City, Country)"
                + "VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedInsertCustomerStatementWithGeneratedKey = dbCon.prepareStatement(sqlInsertCustomerStatement, Statement.RETURN_GENERATED_KEYS);
        preparedInsertCustomerStatementWithGeneratedKey.setString(1, objectToInsert.getFirstName());
        preparedInsertCustomerStatementWithGeneratedKey.setString(2, objectToInsert.getLastName());
        preparedInsertCustomerStatementWithGeneratedKey.setString(3, objectToInsert.getAddress());
        preparedInsertCustomerStatementWithGeneratedKey.setInt(4, objectToInsert.getPostalCode());
        preparedInsertCustomerStatementWithGeneratedKey.setString(5, objectToInsert.getCity());
        preparedInsertCustomerStatementWithGeneratedKey.setString(6, objectToInsert.getCountry());

        preparedInsertCustomerStatementWithGeneratedKey.executeUpdate();
        ResultSet tableContainingGenratedIds = preparedInsertCustomerStatementWithGeneratedKey.getGeneratedKeys();
        int generatedId = 0;
        while(tableContainingGenratedIds.next()) {
            generatedId = tableContainingGenratedIds.getInt(1);
        }
        objectToInsert.setId(generatedId);

        return generatedId;
    }

    @Override
    public void updateCustomer(Customer objectToUpdate) throws SQLException {

    }

    @Override
    public void deleteCustomer(Customer objectToDelete) throws SQLException {

    }


    @Override
    public Person findCustomerByFullName(String fullName) throws SQLException {
        return null;
    }


    //TODO
	

////
//    @Override
//    public Customer findCustomerById(int id) throws SQLException {
////        String query = "SELECT * FROM Customer WHERE Id = ?";
////        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
////        preparedSelectStatement.setLong(1, id);
////        ResultSet rs = preparedSelectStatement.executeQuery();
////        Customer retrievedCustomer = null;
////        while (rs.next()) {
////            retrievedCustomer = buildObject(rs);
////        }
////
////        return retrievedCustomer;
//    	return null;
//    }
////
//    @Override
//    public Customer findCustomerByFullName(String fullName) throws SQLException {
//        String query = "SELECT * FROM Customer WHERE [FirstName] = ? AND [LastName] = ?";
//        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
//        String[] fullNameAsArray = fullName.split(" ");
//        String firstName = fullNameAsArray[0];
//        String lastName = fullNameAsArray[1];
//        preparedSelectStatement.setString(1, firstName);
//        preparedSelectStatement.setString(2, lastName);
//        ResultSet rs = preparedSelectStatement.executeQuery();
//        Customer retrievedCustomer = null;
//        while (rs.next()) {
//            retrievedCustomer = buildObject(rs);
//        }
//
////        return retrievedCustomer;
//    	return null;
//    }
////
//    @Override
//    public ArrayList<Person> findAllCustomers() throws SQLException {
////        String query = "SELECT * FROM Customer";
////        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
////        ResultSet rs = preparedSelectStatement.executeQuery();
////        ArrayList<Customer> retrievedClothingList = buildObjects(rs);
////
////        return retrievedClothingList;
//    	return null;
//    }
////
//    @Override
//    public int createCustomer(Customer objectToInsert) throws SQLException {
    	
//        String sqlInsertCustomerStatement = "INSERT INTO Customer(FirstName, LastName, [Address], PostalCode, City)"
//                + "VALUES(?, ?, ?, ?, ?);";
//        PreparedStatement preparedInsertCustomerStatementWithGeneratedKey = dbCon.prepareStatement(sqlInsertCustomerStatement, Statement.RETURN_GENERATED_KEYS);
//        preparedInsertCustomerStatementWithGeneratedKey.setString(1, objectToInsert.getFirstName());
//        preparedInsertCustomerStatementWithGeneratedKey.setString(2, objectToInsert.getLastName());
//        preparedInsertCustomerStatementWithGeneratedKey.setString(3, objectToInsert.getAddress());
//        preparedInsertCustomerStatementWithGeneratedKey.setInt(4, objectToInsert.getPostalCode());
//        preparedInsertCustomerStatementWithGeneratedKey.setString(5, objectToInsert.getCity());
//
//        preparedInsertCustomerStatementWithGeneratedKey.executeUpdate();
//        ResultSet tableContainingGeneratedIds = preparedInsertCustomerStatementWithGeneratedKey.getGeneratedKeys();
//        int generatedId = 0;
//        while(tableContainingGeneratedIds.next()) {
//            generatedId = tableContainingGeneratedIds.getInt(1);
//        }
////        objectToInsert.setId(generatedId);
////
////        return generatedId;
//    	return 0;
//    }
////
//    @Override
//    public void updateCustomer(Customer objectToUpdate) throws SQLException {
////        String sqlUpdateCustomerStatement = "UPDATE Customer SET [FirstName]= ?, [LastName]= ?, Address= ?, PostalCode= ?, City= ?";
////        PreparedStatement preparedUpdateCustomerStatement = dbCon.prepareStatement(sqlUpdateCustomerStatement);
////        preparedUpdateCustomerStatement.setString(1, objectToUpdate.getFirstName());
////        preparedUpdateCustomerStatement.setString(2, objectToUpdate.getLastName());
////        preparedUpdateCustomerStatement.setString(3, objectToUpdate.getAddress());
////        preparedUpdateCustomerStatement.setInt(4, objectToUpdate.getPostalCode());
////        preparedUpdateCustomerStatement.setString(5, objectToUpdate.getCity());
////
////        preparedUpdateCustomerStatement.execute();
////
//    }
////
//    @Override
//    public void deleteCustomer(Customer objectToDelete) throws SQLException {
////        String sqlDeleteProductStatement = "DELETE FROM Customer WHERE Id = ?";
////        PreparedStatement preparedDeleteProductStatement = dbCon.prepareStatement(sqlDeleteProductStatement);
////        preparedDeleteProductStatement.setInt(1, objectToDelete.getId());
////        preparedDeleteProductStatement.execute();
//    }
////
////    @Override
////    public void setSalesOrderRelatedToThisCustomer(Customer customer) throws SQLException {
////        customer.setSaleOrders(DaoFactory.getSaleOrderDao().findSaleOrdersByCustomer(customer));
////    }

}
