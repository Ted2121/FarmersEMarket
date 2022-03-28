package db_access.DaoInterfaces;

import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public interface CustomerDao {

        Customer findCustomerById(int id) throws SQLException;
        ArrayList<Customer> findAllCustomers() throws SQLException;
        int createCustomer(Customer objectToInsert) throws SQLException;
        boolean updateCustomer(Customer objectToUpdate) throws SQLException;
        boolean deleteCustomer(Customer objectToDelete) throws SQLException;
        void setSalesOrderRelatedToThisCustomer(Customer customer) throws SQLException;
        Customer findCustomerByFullName(String fullName) throws SQLException;


        //TODO move this to CustomerDaoImplementation
//        @Override
//        public Customer findCustomerByFullName(String fullName) throws SQLException{
//                String query = "SELECT * FROM Customer WHERE [firstName] = ? AND [lastName] = ?";
//                PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
//                String[] fullNameAsArray = fullName.split(" ");
//                String firstName = fullNameAsArray[0];
//                String lastName = fullNameAsArray[1];
//                preparedSelectStatement.setString(1, firstName);
//                preparedSelectStatement.setString(2, lastName);
//                ResultSet rs = preparedSelectStatement.executeQuery();
//                Customer retrievedCustomer = null;
//                while(rs.next()) {
//                        retrievedCustomer = buildObject(rs);
//                }
//
//                return retrievedCustomer;
//        }


}