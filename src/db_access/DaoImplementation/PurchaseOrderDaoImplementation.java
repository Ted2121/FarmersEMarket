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
	private Connection connectionDB = DBConnection.getInstance().getDBCon();

	private ArrayList<PurchaseOrder> buildObjects(ResultSet rs, boolean retrieveProvider, boolean retrieveLineItem) throws SQLException, Exception{
		ArrayList<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
		while(rs.next()) {
			PurchaseOrder retrievedPurchaseOrder = buildObject(rs);
			if(retrieveProvider) {
				Provider retrievedProviderLinkedToThisPurchaseOrder = DaoFactory.getProviderDao().findProviderById(rs.getInt("FK_Provider"));
				retrievedPurchaseOrder.setProvider(retrievedProviderLinkedToThisPurchaseOrder);
			}
			
			if(retrieveLineItem) {
				ArrayList<LineItem> lineItemOfTheOrder = retrievedPurchaseOrder.getLineItems();
				for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(retrievedPurchaseOrder, true)) {
					lineItemOfTheOrder.add(lineItem);
				}
			}
			
			purchaseOrderList.add(retrievedPurchaseOrder);
			
		}
		
		return purchaseOrderList;
	}
	
	private PurchaseOrder buildObject(ResultSet rs) throws SQLException{
		PurchaseOrder builtObject = (PurchaseOrder) ModelFactory.getPurchaseOrderModel(rs.getInt("PK_idPurchaseOrder"));
		builtObject.setOrderPrice(rs.getInt("Price"));
		builtObject.setOrderDateTime(rs.getString("DateTime"));
		return builtObject;
	}

	@Override
	public PurchaseOrder findPurchaseOrderById(int id, boolean retrieveProvider, boolean retrieveLineItem)  throws SQLException, Exception{
		//Retrieving the PurchaseOrder from the database
		String query = "SELECT * FROM PurchaseOrder INNER JOIN [Order] ON PurchaseOrder.PK_idPurchaseOrder = [Order].PK_idOrder WHERE PK_idPurchaseOrder = ? ";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setInt(1, id);
		ResultSet rs = preparedSelectStatement.executeQuery();
		PurchaseOrder retrievedPurchaseOrder = null;
		while(rs.next()) {
			//Building the PurchaseOrder object
			retrievedPurchaseOrder = buildObject(rs);
			
			//If we want to set the Provider, we just specify we want to retrieve the Provider as a parameter of this method
			if(retrieveProvider) {
				//If we want to retrieve the provider, we get it from the Provider Dao
				Provider retrievedProviderLinkedToThisPurchaseOrder = DaoFactory.getProviderDao().findProviderById(rs.getInt("FK_Provider"));
				//And we set it as the provider of its object
				retrievedPurchaseOrder.setProvider(retrievedProviderLinkedToThisPurchaseOrder);
			}
			
			//If we want to set the LineItems, we just specify we want to retrieve the LineItem as a parameter of this method
			if(retrieveLineItem) {
				//If we want to retrieve the LineItems, we get the ArrayList of the retrieved PurchaseOrder
				ArrayList<LineItem> lineItemOfTheOrder = retrievedPurchaseOrder.getLineItems();
				
				//We get all the LineItmes from the LineItemDao and add each of them to the ArrayList we retrieve earlier
				for(LineItem lineItem : DaoFactory.getLineItemDao().findLineItemsByOrder(retrievedPurchaseOrder, true)) {
					lineItemOfTheOrder.add(lineItem);
				}
			}
		}
		
		return retrievedPurchaseOrder;
	}
	
	@Override
	public List<PurchaseOrder> findAllPurchaseOrders(boolean retrieveProvider, boolean retrieveLineItem) throws SQLException, Exception {
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
