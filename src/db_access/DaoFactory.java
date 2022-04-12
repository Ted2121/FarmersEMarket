package db_access;

import db_access.DaoImplementation.*;
import db_access.DaoInterfaces.*;

public class DaoFactory{
	private DaoFactory() {};
	
	public static CustomerDao getCustomerDao() {
		return new CustomerDaoImplementation();
	}
	
	public static LineItemDao getLineItemDao() {
		return new LineItemDaoImplementation();
	}
	
	public static OrderDao getOrderDao() {
		return new OrderDaoImplementation();
	}
	
	public static ProductDao getProductDao() {
		return new ProductDaoImplementation();
	}
	
	public static ProductInformationDao getProductInformationDao() {
		return new ProductInformationDaoImplementation();
	}
	
	public static ProviderDao getProviderDao() {
		return new ProviderDaoImplementation();
	}
	
	public static PurchaseOrderDao getPurchaseOrderDao() {
		return new PurchaseOrderDaoImplementation();
	}
	
	public static SaleOrderDao getSaleOrderDao() {
		return new SaleOrderDaoImplementation();
	}
}
