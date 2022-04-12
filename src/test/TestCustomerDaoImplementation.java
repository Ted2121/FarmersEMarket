package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.CustomerDao;
import model.Customer;
import model.ModelFactory;
import model.Person;

public class TestCustomerDaoImplementation {
	Connection dbCon = DBConnection.getInstance().getDBCon();
	static CustomerDao customerDao = DaoFactory.getCustomerDao();
	static int generatedIdCreateTest;
	static Person objectToDelete;
	static Person objectToUpdate;
	
	@BeforeClass
	public static void CreatingTheTupleToDelete () throws SQLException {
		objectToDelete = ModelFactory.getCustomerModel("John", "Doe", "Aalborg", "Denmark", "testaddress", 9000);
		objectToUpdate = ModelFactory.getCustomerModel("John", "Doe", "Aalborg", "Denmark", "testaddress", 9000);
		customerDao.createCustomer((Customer) objectToDelete);
		customerDao.createCustomer((Customer) objectToUpdate);
	}
	
	@Test
	public void TestFindCustomerById() throws SQLException {
		Person result = customerDao.findCustomerById(1);
		assertNotNull("The retrieved object shouldn't be null", result);
	}
	
	@Test
	public void TestFindAllCustomers() throws SQLException {
		List<Person> result = customerDao.findAllCustomers();
		assertFalse("The retrievedArrayList shouldn't be empty", result.isEmpty());
	}
	
	@Test
	public void TestFindCustomerByFullName() throws SQLException {
		assertNotNull("The retrieved object shouldn't be null", customerDao.findCustomerByFullName("John Doe"));
	}
	
	@Test
	public void TestCreateCustomer() throws SQLException {
		Person testCustomer = ModelFactory.getCustomerModel("James", "Bond", "Wattenscheid", "Great Britain", "testaddress", 321554);
		generatedIdCreateTest = customerDao.createCustomer((Customer) testCustomer);
		assertNotNull("The retrieved object shouldn't be null", customerDao.findCustomerById(generatedIdCreateTest));
	}
	
	@Test
	public void TestDeleteCustomer() throws SQLException {
		customerDao.deleteCustomer((Customer) objectToDelete);
		assertNull("Should have deleted the object", customerDao.findCustomerById(objectToDelete.getId()));
	}
	
	@Test
	public void TestUpdateCustomer() throws SQLException {
		objectToUpdate.setFirstName("updatedFirstName");

		customerDao.updateCustomer((Customer) objectToUpdate);
		
		assertEquals("Should display updatedFirstName", "updatedFirstName", customerDao.findCustomerById(objectToUpdate.getId()).getFirstName());
	}
	
	@AfterClass
	public static void CleanUp() throws SQLException {
		Person objectToBeCleanedUp = (Customer) customerDao.findCustomerById(generatedIdCreateTest);
		customerDao.deleteCustomer((Customer) objectToBeCleanedUp);
		customerDao.deleteCustomer((Customer) objectToUpdate);
	}
}
