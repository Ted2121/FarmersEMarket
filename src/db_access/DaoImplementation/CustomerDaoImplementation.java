package db_access.DaoImplementation;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.CustomerDao;
import model.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImplementation implements CustomerDao {

    Connection dbCon = DBConnection.getInstance().getDBCon();

    private List<Customer> buildObjects(ResultSet rs, boolean retrieveSaleOrder) throws Exception {

        List<Customer> customerList = new ArrayList<>();
		while(rs.next()) {
        Customer retrievedCustomer = buildObject(rs);
        if(retrieveSaleOrder) {
            ArrayList<SaleOrder> linkedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(rs.getInt("PK_IdCustomer"), false, false);
            retrievedCustomer.setSaleOrders(linkedSaleOrder);
        }

            customerList.add(retrievedCustomer);
    }

		return customerList;
    }

    private Customer buildObject(ResultSet rs) throws SQLException {
        Customer builtCustomer = ModelFactory.getCustomerModel(
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
    public Customer findCustomerById(int customerId, boolean retrieveSaleOrder) throws Exception {
        String query = "SELECT * FROM Customer WHERE PK_idCustomer = ?";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        preparedSelectStatement.setInt(1, customerId);
        ResultSet rs = preparedSelectStatement.executeQuery();
        Customer retrievedCustomer = null;
        while (rs.next()) {
            retrievedCustomer = buildObject(rs);

            if(retrieveSaleOrder) {
                ArrayList<SaleOrder> linkedSaleOrder = DaoFactory.getSaleOrderDao().findSaleOrderByCustomerId(rs.getInt("PK_IdCustomer"), false, false);
                retrievedCustomer.setSaleOrders(linkedSaleOrder);
            }
        }

        return retrievedCustomer;
    }

    @Override
    public List<Customer> findAllCustomers(boolean retrieveSaleOrder) throws Exception {
        String query = "SELECT * FROM Customer";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        ResultSet rs = preparedSelectStatement.executeQuery();
        List<Customer> retrievedCustomerList = buildObjects(rs, retrieveSaleOrder);

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
        ResultSet tableContainingGeneratedIds = preparedInsertCustomerStatementWithGeneratedKey.getGeneratedKeys();
        int generatedId = 0;
        while (tableContainingGeneratedIds.next()) {
            generatedId = tableContainingGeneratedIds.getInt(1);
        }
        objectToInsert.setId(generatedId);

        return generatedId;
    }

    @Override
    public void updateCustomer(Customer objectToUpdate) throws SQLException {
        String sqlUpdateCustomerStatement = "UPDATE Customer SET FirstName= ?, LastName= ?, Address= ?, PostalCode= ?, City= ?, Country= ? WHERE PK_idCustomer = ?";
        PreparedStatement preparedUpdateCustomerStatement = dbCon.prepareStatement(sqlUpdateCustomerStatement);
        preparedUpdateCustomerStatement.setString(1, objectToUpdate.getFirstName());
        preparedUpdateCustomerStatement.setString(2, objectToUpdate.getLastName());
        preparedUpdateCustomerStatement.setString(3, objectToUpdate.getAddress());
        preparedUpdateCustomerStatement.setInt(4, objectToUpdate.getPostalCode());
        preparedUpdateCustomerStatement.setString(5, objectToUpdate.getCity());
        preparedUpdateCustomerStatement.setString(6, objectToUpdate.getCountry());
        preparedUpdateCustomerStatement.setInt(7, objectToUpdate.getId());

        preparedUpdateCustomerStatement.execute();

    }

    @Override
    public void deleteCustomer(Customer objectToDelete) throws SQLException {
        String sqlDeleteProductStatement = "DELETE FROM Customer WHERE PK_idCustomer = ?";
        PreparedStatement preparedDeleteCustomerStatement = dbCon.prepareStatement(sqlDeleteProductStatement);
        preparedDeleteCustomerStatement.setInt(1, objectToDelete.getId());
        preparedDeleteCustomerStatement.execute();
    }


    @Override
    public Customer findCustomerByFullName(String fullName) throws SQLException {
        String query = "SELECT * FROM Customer WHERE FirstName = ? AND LastName = ?";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        String[] fullNameAsArray = fullName.split(" ");
        String firstName = fullNameAsArray[0];
        String lastName = fullNameAsArray[1];
        preparedSelectStatement.setString(1, firstName);
        preparedSelectStatement.setString(2, lastName);
        ResultSet rs = preparedSelectStatement.executeQuery();
        Customer retrievedCustomer = null;
        while (rs.next()) {
            retrievedCustomer = buildObject(rs);
        }

        return retrievedCustomer;
    }

}
