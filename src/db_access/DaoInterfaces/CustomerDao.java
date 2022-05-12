package db_access.DaoInterfaces;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends ContainSubsetDao<Customer> {
    Customer findCustomerById(int customerId, boolean retrieveSaleOrder) throws Exception;
    Customer findCustomerByFullName(String fullName) throws SQLException;
    List<Customer> findAllCustomersWithThisName(String fullName, boolean retrieveSaleOrders) throws Exception;
    List<Customer> findAllCustomers(boolean retrieveSaleOrder) throws Exception;
    void createCustomer(Customer objectToInsert) throws SQLException;
    void updateCustomer(Customer objectToUpdate) throws SQLException;
    void deleteCustomer(Customer objectToDelete) throws SQLException;
}