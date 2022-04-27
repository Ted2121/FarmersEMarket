package controller.ControllerInterfaces;

import model.Product;
import model.Customer;

public interface CreateSaleOrderController {
    void addProductToSaleOrder(Product product, int quantity);
    void createSaleOrder(Customer customer);
}
