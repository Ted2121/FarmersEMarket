package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.PurchaseOrderDao;
import model.LineItem;
import model.ModelFactory;
import model.Person;
import model.Provider;
import model.PurchaseOrder;

public class PurchaseOrderDaoImplementation implements PurchaseOrderDao {
	Connection connectionDB = DBConnection.getInstance().getDBCon();

	private ArrayList<PurchaseOrder> buildObjects(ResultSet rs, boolean retrieveProvider, boolean retrieveLineItem) throws SQLException{
		ArrayList<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
		while(rs.next()) {
			PurchaseOrder retrievedPurchaseOrder = buildObject(rs, false);
			if(retrieveProvider) {
				Provider retrievedProviderLinkedToThisPurchaseOrder = (Provider) DaoFactory.getProviderDao().findPersonById(rs.getInt("FK_Provider"));
				retrievedPurchaseOrder.setProvider(retrievedProviderLinkedToThisPurchaseOrder);
			}
			
			if(retrieveLineItem) {
				ArrayList<LineItem> lineItemOfTheOrder = retrievedPurchaseOrder.getLineItems();
				for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(retrievedPurchaseOrder)) {
					lineItemOfTheOrder.add(lineItem);
				}
			}
			
			purchaseOrderList.add(retrievedPurchaseOrder);
			
		}
		
		return purchaseOrderList;
	}
	
	private PurchaseOrder buildObject(ResultSet rs, boolean retrieveProviderAssociation) throws SQLException{
		PurchaseOrder builtObject = (PurchaseOrder) ModelFactory.getPurchaseOrderModel(rs.getInt("PK_idPurchaseOrder"));
		return builtObject;
	}

	@Override
	public PurchaseOrder findPurchaseOrderById(int id, boolean retrieveProvider, boolean retrieveLineItem)  throws SQLException{
		String query = "SELECT * FROM PurchaseOrder WHERE PK_idPurchaseOrder = ?";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setInt(1, id);
		ResultSet rs = preparedSelectStatement.executeQuery();
		PurchaseOrder retrievedPurchaseOrder = null;
		while(rs.next()) {
			retrievedPurchaseOrder = buildObject(rs, false);
			
			if(retrieveProvider) {
				Provider retrievedProviderLinkedToThisPurchaseOrder = (Provider) DaoFactory.getProviderDao().findPersonById(rs.getInt("FK_Provider"));
				retrievedPurchaseOrder.setProvider(retrievedProviderLinkedToThisPurchaseOrder);
			}
			
			if(retrieveLineItem) {
				ArrayList<LineItem> lineItemOfTheOrder = retrievedPurchaseOrder.getLineItems();
				for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(retrievedPurchaseOrder)) {
					lineItemOfTheOrder.add(lineItem);
				}
			}
		}
		
		return retrievedPurchaseOrder;
	}
	
	@Override
	public List<PurchaseOrder> findAllPurchaseOrders(boolean retrieveProvider, boolean retrieveLineItem) throws SQLException {
		String query = "SELECT * FROM PurchaseOrder";
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
		System.out.println(">> Order deleted from the Database");
	}


}
