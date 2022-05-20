package controller.ControllerImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.FastSearchHelperClass;
import controller.ControllerInterfaces.CRUDProductInformationController;
import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.*;
import model.Product.*;
import model.*;

public class CRUDProductInformationControllerImplementation implements CRUDProductInformationController{
	private ProductDao productDao;
	private ProductInformationDao productInformationDao;
	private Connection connection = DBConnection.getInstance().getDBCon();
	
	public CRUDProductInformationControllerImplementation() {
		productDao = DaoFactory.getProductDao();
		productInformationDao = DaoFactory.getProductInformationDao();
	}

	@Override
	public List<Product> searchAllProductAndProductInformation() {
		List<Product> list = new ArrayList<Product>();
		try {
			list = productDao.findAllProducts(false, true);
		} catch (Exception e) {
			System.out.println("Cannot find products or product inforamtions");
		}
		return list;
	}
	
	@Override
	public String[][] retrieveTableData() {
		List<Product> productList = searchAllProductAndProductInformation();
		String[][] productData = new String[productList.size()][8];
		int index=0;
		for(Product product : productList) {
			productData[index][0] = product.getId()+"";
			productData[index][1] = product.getProductName();
			productData[index][2] = Double.toString(product.getPurchasingPrice());
			productData[index][3] = Double.toString(product.getSellingPrice());
			productData[index][4] = product.getUnit();
			productData[index][5] = Integer.toString(product.getWeightCategory());
			productData[index][6] = Integer.toString(product.getRelatedProductInformation().getLocationCode());
			productData[index][7] = Integer.toString(product.getRelatedProductInformation().getQuantity());
			index++;
		}
		return productData;
	}
	
	@Override
	public Product searchAProductById(int id) {
		Product product = null;
		try {
			product = productDao.findProductById(id, false, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public void createProductInformationAndProduct(String productName, double purchasingPrice, double sellingPrice, String unit,
			int weightCategory, int locationCode, int quantity) {
		try {
			connection.setAutoCommit(false);
			Product product;
			ProductInformation productInformation;
			try {
				if(weightCategory == 1) {
					product = ModelFactory.getProductModel(productName, purchasingPrice, sellingPrice, WeightCategory.valueOf("ONE"), Unit.valueOf(unit));
				}
				else if(weightCategory == 5) {
					product = ModelFactory.getProductModel(productName, purchasingPrice, sellingPrice, WeightCategory.valueOf("FIVE"), Unit.valueOf(unit));
				}
				else if(weightCategory == 10) {
					product = ModelFactory.getProductModel(productName, purchasingPrice, sellingPrice, WeightCategory.valueOf("TEN"), Unit.valueOf(unit));
				}
				else {
					Exception weighCategoryException = new Exception();
					throw weighCategoryException;
				}
				
				try {
					productDao.createProduct(product);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				productInformation = ModelFactory.getProductInformationModel(locationCode, quantity, product.getId());
				try {
					productInformationDao.createProductInformation(productInformation);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Weight Category is incorrect");
			}
			connection.commit();
		}
		catch(Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateProductInformationAndProduct(Product product, int locationCode, int quantity) {
		// TODO Auto-generated method stub
		try {
			connection.setAutoCommit(false);
			try {
				productDao.updateProduct(product);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ProductInformation productInformation = ModelFactory.getProductInformationModel(locationCode, quantity, product.getId());
			try {
				productInformationDao.updateProductInformation(productInformation);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.commit();
		}
		catch(Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteProductInformationAndProduct(Product product) {
		// TODO Auto-generated method stub
		try {
			connection.setAutoCommit(false);
			ProductInformation productInformation;
			try {
				productInformation = productInformationDao.findProductInformationByProduct(product, false);
				productInformationDao.deleteProductInformation(productInformation);
				productDao.deleteProduct(product);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.commit();
		}
		catch(Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
