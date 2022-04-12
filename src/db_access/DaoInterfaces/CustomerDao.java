package db_access.DaoInterfaces;

import model.Customer;
import model.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDao {

        Person findCustomerById(int customerId) throws SQLException;
        ArrayList<Person> findAllCustomers() throws SQLException;
        int createCustomer(Customer objectToInsert) throws SQLException;
        void updateCustomer(Customer objectToUpdate) throws SQLException;
        void deleteCustomer(Customer objectToDelete) throws SQLException;
        // don't think we need this one
//        void setSalesOrderRelatedToThisCustomer(Customer customer) throws SQLException;
        Person findCustomerByFullName(String fullName) throws SQLException;


}