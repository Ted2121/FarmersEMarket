package db_access.DaoImplementation;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.LineItemDao;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoImplementation implements LineItemDao {
	private Connection connection = DBConnection.getInstance().getDBCon();
	
	private List<LineItem> buildObjects(ResultSet rs, boolean retrieveProducts, boolean retrieveOrder) throws Exception{
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
		PreparedStatement preparedSelectStatement = connection.prepareStatement(query);
		preparedSelectStatement.setInt(1, orderId);
		preparedSelectStatement.setInt(2, productId);
		ResultSet rs = preparedSelectStatement.executeQuery();
		LineItem retrievedLineItem = null;
		//Building the PurchaseOrder object
		List<LineItem> retrievedList = buildObjects(rs, retrieveProduct, retrieveOrder);
		if(retrievedList.size()>0)
			retrievedLineItem = retrievedList.get(0);
		if(retrievedList.size()>1) {
			throw new Exception("More than 1 item in the retrieved list of LineItem");
		}
		
		return retrievedLineItem;
	}

	@Override
	public List<LineItem> findLineItemsByOrder(Order order, boolean retrieveProduct) throws SQLException, Exception {
		String query = "SELECT * FROM LineItem WHERE PK_FK_OrderId = ?";
		PreparedStatement preparedSelectStatement = connection.prepareStatement(query);
		preparedSelectStatement.setInt(1, order.getId());
		ResultSet rs = preparedSelectStatement.executeQuery();
		List<LineItem> retrievedLineItem = null;
		retrievedLineItem = buildObjects(rs, retrieveProduct, false);
		
		for(LineItem lineItem : retrievedLineItem) {
			lineItem.setOrder(order);
		}
		
		return retrievedLineItem;
	}

	@Override
	public List<LineItem> findLineItemsByProduct(Product product, boolean retrieveOrder) throws SQLException, Exception {
		String query = "SELECT * FROM LineItem WHERE PK_FK_ProductId = ?";
		PreparedStatement preparedSelectStatement = connection.prepareStatement(query);
		preparedSelectStatement.setInt(1, product.getId());
		ResultSet rs = preparedSelectStatement.executeQuery();
		List<LineItem> retrievedLineItem = null;
		retrievedLineItem = buildObjects(rs, false, retrieveOrder);
		
		for(LineItem lineItem : retrievedLineItem) {
			lineItem.setProduct(product);
		}
		
		return retrievedLineItem;
	}

	@Override
	public List<LineItem> findAllLineItems(boolean retrieveOrder, boolean retrieveProduct) throws SQLException, Exception {
		String query = "SELECT * FROM LineItem";
		PreparedStatement preparedSelectStatement = connection.prepareStatement(query);
		ResultSet rs = preparedSelectStatement.executeQuery();
		List<LineItem> retrievedLineItem = null;
		retrievedLineItem = buildObjects(rs, retrieveProduct, retrieveOrder);
		
		return retrievedLineItem;
	}

	@Override
	public void createLineItem(LineItem objectToInsert) throws SQLException {
		String sqlInsertLineItemStatement = "INSERT INTO LineItem(PK_FK_ProductId, PK_FK_OrderId, quantity) "
				+ "VALUES (?,?,?)";
		PreparedStatement preparedInsertLineItemStatement = connection.prepareStatement(sqlInsertLineItemStatement);
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
		PreparedStatement preparedUpdateLineItemStatement = connection.prepareStatement(sqlUpdateLineItemStatement);
		preparedUpdateLineItemStatement.setInt(1, objectToUpdate.getQuantity());
		preparedUpdateLineItemStatement.setInt(2, objectToUpdate.getProduct().getId());
		preparedUpdateLineItemStatement.setInt(3, objectToUpdate.getOrder().getId());

		preparedUpdateLineItemStatement.execute();
		System.out.println(">> LineItem updated to the database");
	}

	@Override
	public void deleteLineItem(LineItem objectToDelete) throws SQLException {
		String sqlDeleteOrderStatement = "DELETE FROM LineItem WHERE PK_FK_OrderId = ? AND PK_FK_ProductId = ?";
		PreparedStatement preparedDeleteOrderStatement = connection.prepareStatement(sqlDeleteOrderStatement);
		preparedDeleteOrderStatement.setInt(1, objectToDelete.getOrder().getId());
		preparedDeleteOrderStatement.setInt(2, objectToDelete.getProduct().getId());
		preparedDeleteOrderStatement.execute();
		System.out.println(">> LineItem deleted from the Database");
	}


}
