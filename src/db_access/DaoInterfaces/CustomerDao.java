package db_access.DaoInterfaces;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {

        Customer findCustomerById(int customerId, boolean retrieveSaleOrder) throws Exception;
        List<Customer> findAllCustomers(boolean b) throws Exception;
        int createCustomer(Customer objectToInsert) throws SQLException;
        void updateCustomer(Customer objectToUpdate) throws SQLException;
        void deleteCustomer(Customer objectToDelete) throws SQLException;
        Customer findCustomerByFullName(String fullName) throws SQLException;


}