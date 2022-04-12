package test;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

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
	public void TestCustomerFindById() throws SQLException {
		Person result = customerDao.findCustomerById(1);
		assertNotNull("The retrieved object shouldn't be null", result);
	}
	
	@AfterClass
	public static void CleanUp() throws SQLException {
		Person objectToBeCleanedUp = (Customer) customerDao.findCustomerById(generatedIdCreateTest);
		customerDao.deleteCustomer((Customer) objectToBeCleanedUp);
		customerDao.deleteCustomer((Customer) objectToUpdate);
	}
}
