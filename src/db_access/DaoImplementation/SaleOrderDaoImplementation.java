package db_access.DaoImplementation;

import java.sql.SQLException;
import java.util.ArrayList;

import db_access.DaoInterfaces.SaleOrderDao;
import model.SaleOrder;

public class SaleOrderDaoImplementation implements SaleOrderDao {

	@Override
	public SaleOrder findSaleOrderById(int saleOrderId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SaleOrder> findAllSaleOrders() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createSaleOrder(SaleOrder objectToInsert) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateSaleOrder(SaleOrder objectToUpdate) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSaleOrder(SaleOrder objectToDelete) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
