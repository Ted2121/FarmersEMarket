package controller.ControllerImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.FastSearchFactory;
import controller.FastSearchHelperClass;
import controller.HelperClass;
import controller.ControllerInterfaces.CRUDPurchaseOrderController;
import controller.ControllerInterfaces.SearchProductInterface;
import controller.ControllerInterfaces.SearchProviderInterface;
import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.*;
import model.*;

public class CRUDPurchaseOrderControllerImplementation implements CRUDPurchaseOrderController, SearchProductInterface, SearchProviderInterface{
	private LineItemDao lineItemDao;
	private OrderDao orderDao;
	private PurchaseOrderDao purchaseOrderDao;
	private List<PurchaseOrder> purchaseOrdersList;
	private List<LineItem> lineItemToDeleteList;
	private List<Product> productToAdd;
	private HashMap<Integer, PurchaseOrder> idRelatedToPurchaseOrder;
	private HashMap<Product, Integer> productsAlreadyPresent;
	
	private FastSearchHelperClass<Product> productFastSearch;
	private FastSearchHelperClass<Provider> providerFastSearch;

	private Connection connection;
	
	public CRUDPurchaseOrderControllerImplementation() {
		connection = DBConnection.getInstance().getDBCon();
		productFastSearch = FastSearchFactory.getProductFastSearch();
		providerFastSearch = FastSearchFactory.getProviderFastSearch();
		
		purchaseOrdersList = findAllPurchaseOrder();
		
		lineItemToDeleteList = new ArrayList<>();
		idRelatedToPurchaseOrder = new HashMap<>();
		productsAlreadyPresent = new HashMap<>();
		productToAdd = new ArrayList<>();
		for(PurchaseOrder purchaseOrder : purchaseOrdersList) {
			idRelatedToPurchaseOrder.put(purchaseOrder.getId() , purchaseOrder);
		}
		
		lineItemDao= DaoFactory.getLineItemDao();
		orderDao = DaoFactory.getOrderDao();
		purchaseOrderDao = DaoFactory.getPurchaseOrderDao();
		
	}

	@Override
	public void updatePurchaseOrder(PurchaseOrder purchaseOrder) {
		try {
			HelperClass.exchangeProductSubsetWithQuantityHashMapToCompleteProducts(productsAlreadyPresent);
		} catch (SQLException e2) {
			System.out.println("Cannot retrieve the full product object due to database error");
		} catch (Exception e2) {
			System.out.println("Cannot retrieve the full product object due to software");
		}
		purchaseOrder.setOrderPrice(HelperClass.calculateTotalPrice(productsAlreadyPresent));
		//Transaction
		try {
			connection.setAutoCommit(false);
			
			purchaseOrderDao.updatePurchaseOrder(purchaseOrder);
			orderDao.updateOrder(purchaseOrder);
			for(LineItem lineItem : lineItemToDeleteList) {
				lineItem.setOrder(purchaseOrder);
				lineItemDao.deleteLineItem(lineItem);
			}
			
			for(Product product : productToAdd) {
				LineItem lineItem = ModelFactory.getLineItemModel(productsAlreadyPresent.get(product), product, purchaseOrder);
				lineItemDao.createLineItem(lineItem);
			}
			
			connection.commit();
			
		}catch(SQLException e) {
			try {
				connection.rollback();
				System.out.println("Transaction rolled back");
			} catch (SQLException e1) {
				System.out.println("Cannot rollback the transaction");
			}
		}finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Cannot set the autoCommit to true");
			}
		}
	}

	@Override
	public void deletePurchaseOrder(PurchaseOrder purchaseOrder) {
		
		try {
			//Transaction
			connection.setAutoCommit(false);
			
			List<LineItem> lineItemToDelete = lineItemDao.findLineItemsByOrder(purchaseOrder, true);
			for(LineItem lineItem : lineItemToDelete) {
				lineItemDao.deleteLineItem(lineItem);
			}
			orderDao.deleteOrder(purchaseOrder);
			
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("Cannot rollback due to database reason");
			}
		}finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Cannot set the autocommit to true due to database reason");
			}
		}
		
	}

	@Override
	public List<PurchaseOrder> findAllPurchaseOrder() {
		List<PurchaseOrder> purchaseOrderlist = null;
		try {
			purchaseOrderlist = DaoFactory.getPurchaseOrderDao().findAllPurchaseOrders(true, false);
		} catch (SQLException e) {
			System.out.println("Cannot retrieve purchase orders from database");
		} catch (Exception e) {
			System.out.println("Cannot retrieve purchase orders due to software problems");
		}
		
		return purchaseOrderlist;
	}

	@Override
	public String[][] retrieveTableData() {
		List<PurchaseOrder> purchaseOrdersList = findAllPurchaseOrder();
		String[][] purchaseOrdersData = new String[purchaseOrdersList.size()][4];
		int index=0;
		for(PurchaseOrder purchaseOrder : purchaseOrdersList) {
			purchaseOrdersData[index][0] = purchaseOrder.getId()+"";
			purchaseOrdersData[index][1] = purchaseOrder.getProvider().getFullName();
			purchaseOrdersData[index][2] = purchaseOrder.getOrderDateTime();
			purchaseOrdersData[index][3] = purchaseOrder.getOrderPrice()+" DKK";
			index++;
		}
		return purchaseOrdersData;
	}

	@Override
	public HashMap<Integer, PurchaseOrder> retrieveIdRelatedToPurchaseOrderHashMap() {
		return idRelatedToPurchaseOrder;
	}

	@Override
	public List<LineItem> findAllLineItemRelatedToThisPurchaseOrder(PurchaseOrder purchaseOrder) {
		List<LineItem> lineItemList = null;
		try {
			lineItemList = lineItemDao.findLineItemsByOrder(purchaseOrder, true);
		} catch (SQLException e) {
			System.out.println("Cannot retrieve all LineItem and product inforamtion due to Database error");
		} catch (Exception e) {
			System.out.println("Cannot retrieve all LineItem and product inforamtion due to software error");
		}
		
		for(LineItem lineItem : lineItemList) {
			productsAlreadyPresent.put(lineItem.getProduct(), lineItem.getQuantity());
		}
		
		return lineItemList;
	}

	@Override
	public void deleteLineItemFromPurchaseOrder(LineItem lineItem) {
		lineItemToDeleteList.add(lineItem);
		productsAlreadyPresent.remove(lineItem.getProduct());
	}

	@Override
	public void addProductToPurchaseOrder(Product selectedProduct, int quantity) {
		productsAlreadyPresent.put(selectedProduct, quantity);
		productToAdd.add(selectedProduct);
	}

	@Override
	public void deleteProductInProductToAdd(Product product) {
		productToAdd.remove(product);
		productsAlreadyPresent.remove(product);
	}
	
	@Override
	public boolean isProductAlreadyInThePurchaseOrder(Product selectedProduct) {
		for(Product product : productsAlreadyPresent.keySet()) {
			if(product.getId() == selectedProduct.getId()) 
				return true;
		}
		return false;
	}

	@Override
	public List<Provider> searchProviderUsingThisName(String providerName) {
		return providerFastSearch.searchUsingThisName(providerName);
	}

	@Override
	public List<Product> searchProductUsingThisName(String productName) {
		return productFastSearch.searchUsingThisName(productName);
	}

	public List<LineItem> getDeleteListForTestReasonOnly(){
		return lineItemToDeleteList;
	}
	
	public List<Product> getProductToAddListForTestReasonOnly(){
		return productToAdd;
	}
	
	public HashMap<Product, Integer> getProductAlreadyPresentMapForTestReasonOnly(){
		return productsAlreadyPresent;
	}

	@Override
	public void productSearchRefreshData() {
		productFastSearch.refreshData();
	}
	
	@Override
	public void providerSearchRefreshData() {
		providerFastSearch.refreshData();;
	}
	
}
