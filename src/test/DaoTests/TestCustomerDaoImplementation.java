package test.DaoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db_access.DaoFactory;
import db_access.DaoInterfaces.CustomerDao;
import model.Customer;
import model.ModelFactory;


public class TestCustomerDaoImplementation {
	static CustomerDao customerDao = DaoFactory.getCustomerDao();
	static int generatedIdCreateTest;
	static Customer objectToDelete;
	static Customer objectToUpdate;
	static Customer testCustomer;
	
	@BeforeClass
	public static void creatingTheTupleToDelete () throws SQLException {
		objectToDelete = ModelFactory.getCustomerModel("John", "Doe", "Aalborg", "Denmark", "testaddress", 9000);
		objectToUpdate = ModelFactory.getCustomerModel("John", "Doe", "Aalborg", "Denmark", "testaddress", 9000);
		testCustomer = ModelFactory.getCustomerModel("James", "Bond", "Wattenscheid", "Great Britain", "testaddress", 321554);
		customerDao.createCustomer(objectToDelete);
		customerDao.createCustomer(objectToUpdate);
	}
	
	@Test
	public void testFindCustomerById() throws SQLException {
		Customer result = customerDao.findCustomerById(1);
		assertNotNull("The retrieved object shouldn't be null", result);
	}
	
	@Test
	public void testFindAllCustomers() throws SQLException {
		List<Customer> result = customerDao.findAllCustomers(false);
		assertFalse("The retrievedArrayList shouldn't be empty", result.isEmpty());
	}
	
	@Test
	public void testFindCustomerByFullName() throws SQLException {
		assertNotNull("The retrieved object shouldn't be null", customerDao.findCustomerByFullName("John Doe"));
	}
	
	@Test
	public void testCreateCustomer() throws SQLException {

		generatedIdCreateTest = customerDao.createCustomer(testCustomer);
		assertNotNull("The retrieved object shouldn't be null", customerDao.findCustomerById(generatedIdCreateTest));
	}
	
	@Test
	public void testDeleteCustomer() throws SQLException {
		customerDao.deleteCustomer(objectToDelete);
		assertNull("Should have deleted the object", customerDao.findCustomerById(objectToDelete.getId()));
	}
	
	@Test
	public void testUpdateCustomer() throws SQLException {
		objectToUpdate.setFirstName("updatedFirstName");

		customerDao.updateCustomer(objectToUpdate);
		
		assertEquals("Should display updatedFirstName", "updatedFirstName", customerDao.findCustomerById(objectToUpdate.getId()).getFirstName());
	}
	
	@AfterClass
	public static void cleanUp() throws SQLException {
		Customer objectToBeCleanedUp = customerDao.findCustomerById(generatedIdCreateTest);
		customerDao.deleteCustomer(objectToBeCleanedUp);
		customerDao.deleteCustomer(objectToUpdate);
		customerDao.deleteCustomer(testCustomer);
	}
}
