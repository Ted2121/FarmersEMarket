package db_access.DaoInterfaces;

import model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDao {

        Customer findCustomerById(int id) throws SQLException;
        ArrayList<Customer> findAllCustomers() throws SQLException;
        int createCustomer(Customer objectToInsert) throws SQLException;
        void updateCustomer(Customer objectToUpdate) throws SQLException;
        void deleteCustomer(Customer objectToDelete) throws SQLException;
        void setSalesOrderRelatedToThisCustomer(Customer customer) throws SQLException;
        Customer findCustomerByFullName(String fullName) throws SQLException;


        //TODO move this to CustomerDaoImplementation



}