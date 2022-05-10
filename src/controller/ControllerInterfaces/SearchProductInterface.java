package controller.ControllerInterfaces;

import java.util.List;

import model.Customer;
import model.Product;

public interface SearchProductInterface {
	List<Product> searchProductUsingThisName(String productName);
}
