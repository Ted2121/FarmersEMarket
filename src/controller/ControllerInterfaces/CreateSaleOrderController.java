package controller.ControllerInterfaces;

import model.Product;
import model.Customer;
import model.Provider;

public interface CreateSaleOrderController {
    void addProductToSaleOrder(Product product, int quantity) throws Exception;
    void deleteProductFromSaleOrder(Product product);
    boolean isProductAlreadyInTheSaleOrder(Product product);
    void createSaleOrder(Customer customer);
    boolean checkIfQuantityIsSufficient(Product selectedProduct, int quantity) throws Exception;
}
