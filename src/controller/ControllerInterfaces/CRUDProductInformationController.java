package controller.ControllerInterfaces;

import java.util.List;

import controller.FastSearchHelperClass;
import model.Product;

public interface CRUDProductInformationController{
	public List<Product> searchAllProductAndProductInformation();
	public String[][] retrieveTableData();
	public Product searchAProductById(int id);
	public void createProductInformationAndProduct(String productName, double purchasingPrice, double sellingPrice, String unit, int weightCategory, int locationCode, int quantity);
	public void updateProductInformationAndProduct(Product product, int locationCode, int quantity);
	public void deleteProductInformationAndProduct(Product product);
}
