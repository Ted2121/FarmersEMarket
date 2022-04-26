package db_access.DaoImplementation;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.LineItemDao;
import db_access.DaoInterfaces.ProductDao;
import db_access.DaoInterfaces.PurchaseOrderDao;
import db_access.DaoInterfaces.SaleOrderDao;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LineItemDaoImplementation implements LineItemDao {
	private Connection connectionDB = DBConnection.getInstance().getDBCon();
	
	private ArrayList<LineItem> buildObjects(ResultSet rs, boolean retrieveProducts, boolean retrieveOrder) throws Exception{
		ArrayList<LineItem> lineItemList = new ArrayList<LineItem>();
		while(rs.next()) {
			LineItem retrievedLineItem = buildObject(rs);
			if(retrieveProducts) {
				Product retrievedProductLinkedToThisPurchaseOrder = (Product) DaoFactory.getProductDao().findProductById(rs.getInt("PK_FK_ProductId"), false, false);
				retrievedLineItem.setProduct(retrievedProductLinkedToThisPurchaseOrder);
			}
			
			if(retrieveOrder) {
				Order purchaseOrder = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(rs.getInt("PK_FK_OrderId"), false, false);
				Order saleOrder = DaoFactory.getSaleOrderDao().findSaleOrderById(rs.getInt("PK_FK_OrderId"), false, false);
				if(purchaseOrder  != null) {
					retrievedLineItem.setOrder(purchaseOrder);
				}else if(saleOrder != null) {
					retrievedLineItem.setOrder(saleOrder);
				}else {
					throw new Exception("Cannot find the order");
				}
			}
			
			lineItemList.add(retrievedLineItem);
			
		}
		
		return lineItemList;
	}
	
	private LineItem buildObject(ResultSet rs) throws SQLException{
		LineItem builtObject = ModelFactory.getLineItemModel(rs.getInt("quantity"));
		return builtObject;
	}

	@Override
	public LineItem findLineItemByOrderAndProductId(int orderId, int productId, boolean retrieveOrder, boolean retrieveProduct) throws SQLException, Exception {
		//Retrieving the PurchaseOrder from the database
		String query = "SELECT * FROM LineItem WHERE PK_FK_OrderId = ? AND PK_FK_ProductId = ? ";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setInt(1, orderId);
		preparedSelectStatement.setInt(2, productId);
		ResultSet rs = preparedSelectStatement.executeQuery();
		LineItem retrievedLineItem = null;
		while(rs.next()) {
			//Building the PurchaseOrder object
			retrievedLineItem = buildObject(rs);
			
			//If we want to set the Provider, we just specify we want to retrieve the Provider as a parameter of this method
			if(retrieveOrder) {
				//If we want to retrieve the provider, we get it from the Provider Dao
				Order retrievedPurchaseOrderLinkedToThisLineItem = DaoFactory.getPurchaseOrderDao().findPurchaseOrderById(rs.getInt("PK_FK_OrderId"), false, false);
				Order retrievedSaleOrderLinkedToThisLineItem = DaoFactory.getSaleOrderDao().findSaleOrderById(rs.getInt("PK_FK_OrderId"), false, false);
				
				if(retrievedPurchaseOrderLinkedToThisLineItem  != null) {
					retrievedLineItem.setOrder(retrievedPurchaseOrderLinkedToThisLineItem);
				}else if(retrievedSaleOrderLinkedToThisLineItem != null) {
					retrievedLineItem.setOrder(retrievedSaleOrderLinkedToThisLineItem);
				}else {
					throw new Exception("Cannot find the order");
				}
			}
			
			//If we want to set the LineItems, we just specify we want to retrieve the LineItem as a parameter of this method
			if(retrieveProduct) {
				//If we want to retrieve the LineItems, we get the ArrayList of the retrieved PurchaseOrder
				Product productRelatedToThisLineItem = DaoFactory.getProductDao().findProductById(rs.getInt("PK_FK_ProductId"), false, false);
				retrievedLineItem.setProduct(productRelatedToThisLineItem);
			}
		}
		
		return retrievedLineItem;
	}

	@Override
	public ArrayList<LineItem> findLineItemsByOrder(Order order, boolean retrieveProduct) throws SQLException, Exception {
		String query = "SELECT * FROM LineItem WHERE PK_FK_OrderId = ?";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setInt(1, order.getId());
		ResultSet rs = preparedSelectStatement.executeQuery();
		ArrayList<LineItem> retrievedLineItem = null;
		retrievedLineItem = buildObjects(rs, retrieveProduct, false);
		
		return retrievedLineItem;
	}

	@Override
	public ArrayList<LineItem> findLineItemsByProduct(Product product, boolean retrieveOrder) throws SQLException, Exception {
		String query = "SELECT * FROM LineItem WHERE PK_FK_ProductId = ?";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		preparedSelectStatement.setInt(1, product.getId());
		ResultSet rs = preparedSelectStatement.executeQuery();
		ArrayList<LineItem> retrievedLineItem = null;
		retrievedLineItem = buildObjects(rs, false, retrieveOrder);
		
		return retrievedLineItem;
	}

	@Override
	public ArrayList<LineItem> findAllLineItems(boolean retrieveOrder, boolean retrieveProduct) throws SQLException, Exception {
		String query = "SELECT * FROM LineItem";
		PreparedStatement preparedSelectStatement = connectionDB.prepareStatement(query);
		ResultSet rs = preparedSelectStatement.executeQuery();
		ArrayList<LineItem> retrievedLineItem = null;
		retrievedLineItem = buildObjects(rs, retrieveProduct, retrieveOrder);
		
		return retrievedLineItem;
	}

	@Override
	public void createLineItem(LineItem objectToInsert) throws SQLException {
		String sqlInsertLineItemStatement = "INSERT INTO LineItem(PK_FK_ProductId, PK_FK_OrderId, quantity) "
				+ "VALUES (?,?,?)";
		PreparedStatement preparedInsertLineItemStatement = connectionDB.prepareStatement(sqlInsertLineItemStatement);
		preparedInsertLineItemStatement.setInt(1, objectToInsert.getProduct().getId());
		preparedInsertLineItemStatement.setInt(2, objectToInsert.getOrder().getId());
		preparedInsertLineItemStatement.setInt(3, objectToInsert.getQuantity());
		preparedInsertLineItemStatement.executeUpdate();
		System.out.println(">> LineItem added to the database");
	}

	@Override
	public void updateLineItem(LineItem objectToUpdate) throws SQLException {
		String sqlUpdateLineItemStatement = "UPDATE LineItem SET quantity = ?"
				+ " WHERE PK_FK_ProductId = ? AND PK_FK_OrderId = ?";
		PreparedStatement preparedUpdateLineItemStatement = connectionDB.prepareStatement(sqlUpdateLineItemStatement);
		preparedUpdateLineItemStatement.setInt(1, objectToUpdate.getQuantity());
		preparedUpdateLineItemStatement.setInt(2, objectToUpdate.getProduct().getId());
		preparedUpdateLineItemStatement.setInt(3, objectToUpdate.getOrder().getId());

		preparedUpdateLineItemStatement.execute();
		System.out.println(">> LineItem updated to the database");
	}

	@Override
	public void deleteLineItem(LineItem objectToDelete) throws SQLException {
		String sqlDeleteOrderStatement = "DELETE FROM LineItem WHERE PK_FK_OrderId = ? AND PK_FK_ProductId = ?";
		PreparedStatement preparedDeleteOrderStatement = connectionDB.prepareStatement(sqlDeleteOrderStatement);
		preparedDeleteOrderStatement.setInt(1, objectToDelete.getOrder().getId());
		preparedDeleteOrderStatement.setInt(2, objectToDelete.getProduct().getId());
		preparedDeleteOrderStatement.execute();
		System.out.println(">> LineItem deleted from the Database");
	}


}
