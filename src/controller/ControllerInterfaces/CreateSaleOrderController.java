package controller.ControllerInterfaces;

import model.Product;
import model.Customer;
import model.Provider;

public interface CreateSaleOrderController {
    void addProductToSaleOrder(Product product, int quantity);
    void deleteProductFromSaleOrder(Product product);
    boolean isProductAlreadyInTheSaleOrder(Product product);
    void createSaleOrder(Customer customer);
}
