package controller.ControllerInterfaces;

import java.util.List;

import model.Customer;

public interface SearchCustomerInterface {
	List<Customer> searchCustomerUsingThisName(String customerName);
}
