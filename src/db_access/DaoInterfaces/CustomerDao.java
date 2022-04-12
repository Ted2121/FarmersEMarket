package db_access.DaoInterfaces;

import model.Customer;
import model.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDao {

        Person findCustomerById(int customerId) throws SQLException;
        List<Person> findAllCustomers() throws SQLException;
        int createCustomer(Customer objectToInsert) throws SQLException;
        void updateCustomer(Customer objectToUpdate) throws SQLException;
        void deleteCustomer(Customer objectToDelete) throws SQLException;
        Person findCustomerByFullName(String fullName) throws SQLException;


}