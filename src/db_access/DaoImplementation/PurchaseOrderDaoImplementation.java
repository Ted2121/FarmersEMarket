package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db_access.*;
import db_access.DaoInterfaces.PurchaseOrderDao;
import model.*;

public class PurchaseOrderDaoImplementation implements PurchaseOrderDao {
	private Connection connectionDB = DBConnection.getInstance().getDBCon();

	private ArrayList<PurchaseOrder> buildObjects(ResultSet rs, boolean retrieveProvider, boolean retrieveLineItem) throws SQLException, Exception{
		ArrayList<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
		while(rs.next()) {
			PurchaseOrder retrievedPurchaseOrder = buildObject(rs);
			if(retrieveProvider) {
				Provider retrievedProviderLinkedToThisPurchaseOrder = DaoFactory.getProviderDao().findProviderById(rs.getInt("FK_Provider"), false);
				retrievedPurchaseOrder.setProvider(retrievedProviderLinkedToThisPurchaseOrder);
			}
			
			if(retrieveLineItem) {
				retrievedPurchaseOrder.setLineItems(new ArrayList<LineItem>());
				for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(retrievedPurchaseOrder, true)) {
					retrievedPurchaseOrder.getLineItems().add(lineItem);
				}
			}
			
			purchaseOrderList.add(retrievedPurchaseOrder);
			
		}
		
		return purchaseOrderList;
	}
	
	private PurchaseOrder buildObject(ResultSet rs) throws SQLException{
		PurchaseOrder builtObject = ModelFactory.getPurchaseOrderModel(rs.getInt("PK_idPurchaseOrder"));
		builtObject.setOrderPrice(rs.getInt("Price"));
		builtObject.setOrderDateTime(rs.getString("DateTime"));
		return builtObject;
	}

	@Override
	public PurchaseOrder findPurchaseOrderById(int id, boolean retrieveProvider, boolean retrieveLineItem)  throws Exception{
		//Retrieving the PurchaseOrder from the database
		String query = "SELECT * FROM PurchaseOrder INNER JOIN [Order] ON PurchaseOrder.PK_idPurchaseOrder = [Order].PK_idOrder WHERE PK_idPurchaseOrder = ? ";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setInt(1, id);
		ResultSet rs = preparedSelectStatement.executeQuery();
		PurchaseOrder retrievedPurchaseOrder = null;
		while(rs.next()) {
			//Building the PurchaseOrder object
			List<PurchaseOrder> retrievedList = buildObjects(rs, retrieveProvider, retrieveLineItem);
			retrievedPurchaseOrder = retrievedList.get(0);
			if(retrievedList.size()>1) {
				throw new Exception("More than 1 item in the retrieved list of purchaseOrder");
			}
		}
		
		return retrievedPurchaseOrder;
	}
	
	@Override
	public ArrayList<PurchaseOrder> findPurchaseOrderByProviderId(int idProvider, boolean retrieveProvider, boolean retrieveLineItem)  throws SQLException, Exception{
		//Retrieving the PurchaseOrder from the database
		String query = "SELECT * FROM PurchaseOrder INNER JOIN [Order] ON PurchaseOrder.PK_idPurchaseOrder = [Order].PK_idOrder WHERE FK_Provider = ? ";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setInt(1, idProvider);
		ResultSet rs = preparedSelectStatement.executeQuery();
		ArrayList<PurchaseOrder> retrievedPurchaseOrders = buildObjects(rs, retrieveProvider, retrieveLineItem);
		
		return retrievedPurchaseOrders;
	}
	
	
	@Override
	public List<PurchaseOrder> findAllPurchaseOrders(boolean retrieveProvider, boolean retrieveLineItem) throws Exception {
		String query = "SELECT * FROM PurchaseOrder INNER JOIN [Order] ON PurchaseOrder.PK_idPurchaseOrder = [Order].PK_idOrder";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		
		ResultSet rs = preparedSelectStatement.executeQuery();
		ArrayList<PurchaseOrder> retrievedPurchaseOrderList = buildObjects(rs, retrieveProvider, retrieveLineItem);

		return retrievedPurchaseOrderList;
	}

	@Override
	public void createPurchaseOrder(PurchaseOrder objectToInsert) throws SQLException {
		String sqlInsertOrderStatement = "INSERT INTO PurchaseOrder(PK_idPurchaseOrder, FK_Provider) VALUES (? , ?)";
		PreparedStatement preparedSqlInsertOrderStatement = connectionDB.prepareStatement(sqlInsertOrderStatement) ;
		preparedSqlInsertOrderStatement.setInt(1, objectToInsert.getId());
		preparedSqlInsertOrderStatement.setInt(2, objectToInsert.getProvider().getId());
		
		preparedSqlInsertOrderStatement.execute();
		System.out.println(">> Purchase order added to the Database");
	}

	@Override
	public void updatePurchaseOrder(PurchaseOrder objectToUpdate) throws SQLException {
		String sqlUpdateOrderStatement = "UPDATE PurchaseOrder SET FK_Provider = ? WHERE PK_idPurchaseOrder = ?";
		PreparedStatement preparedUpdateProductStatement = connectionDB.prepareStatement(sqlUpdateOrderStatement);
		preparedUpdateProductStatement.setInt(1, objectToUpdate.getProvider().getId());
		preparedUpdateProductStatement.setInt(2, objectToUpdate.getId());
		
		preparedUpdateProductStatement.execute();		
		System.out.println(">> Purchase order updated in the Database");
	}

	@Override
	public void deletePurchaseOrder(PurchaseOrder objectToDelete) throws SQLException {
		String sqlDeleteOrderStatement = "DELETE FROM PurchaseOrder WHERE PK_idPurchaseOrder = ?";
		PreparedStatement preparedDeleteOrderStatement = connectionDB.prepareStatement(sqlDeleteOrderStatement);
		preparedDeleteOrderStatement.setInt(1, objectToDelete.getId());
		preparedDeleteOrderStatement.execute();
		System.out.println(">> Purchase order deleted from the Database");
	}


}
