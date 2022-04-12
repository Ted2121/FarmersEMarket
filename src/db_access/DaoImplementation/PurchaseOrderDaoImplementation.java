package db_access.DaoImplementation;

import java.sql.SQLException;
import java.util.ArrayList;

import db_access.DaoInterfaces.PurchaseOrderDao;
import model.PurchaseOrder;

public class PurchaseOrderDaoImplementation implements PurchaseOrderDao {

	@Override
	public PurchaseOrder findPurchaseOrderById(int purchaseOrderId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PurchaseOrder> findAllPurchaseOrders() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPurchaseOrder(PurchaseOrder objectToInsert) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updatePurchaseOrder(PurchaseOrder objectToUpdate) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePurchaseOrder(PurchaseOrder objectToDelete) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
