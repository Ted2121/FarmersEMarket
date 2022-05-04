package controller.ControllerInterfaces;

import java.util.List;

import model.Product;
import model.ProductInformation;

public interface CRUDProductInformationController {
	public List<ProductInformation> searchAllProductAndProductInformation();
	public List<ProductInformation> searchProductByProductInformation();
	public void createProductInformationAndProduct(String productName, double purchasingPrice, double sellingPrice, String unit, int weightCategory, int locationCode, int quantity);
	public void updateProductInformationAndProduct(Product product, int locationCode, int quantity);
	public void deleteProductInformationAndProduct(Product product);
}
