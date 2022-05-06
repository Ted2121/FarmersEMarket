package controller.ControllerInterfaces;

import java.util.List;

import model.Product;
import model.ProductInformation;

public interface CRUDProductInformationController extends RetrievingSubsetController {
	public List<Product> searchAllProductAndProductInformation();
	public void createProductInformationAndProduct(String productName, double purchasingPrice, double sellingPrice, String unit, int weightCategory, int locationCode, int quantity);
	public void updateProductInformationAndProduct(Product product, int locationCode, int quantity);
	public void deleteProductInformationAndProduct(Product product);
}
