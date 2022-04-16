package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.OrderDao;
import model.Order;
import model.PurchaseOrder;
import model.SaleOrder;

public class OrderDaoImplementation implements OrderDao{
	Connection connectionDB = DBConnection.getInstance().getDBCon();

	@Override
	public int createEmptyOrder() throws SQLException {
		String sqlInsertOrderStatement = "INSERT INTO [Order](Price, DateTime) VALUES (null, null)";
		PreparedStatement preparedSqlInsertOrderStatementWithGeneratedKey = connectionDB.prepareStatement(sqlInsertOrderStatement, Statement.RETURN_GENERATED_KEYS) ;
		
		preparedSqlInsertOrderStatementWithGeneratedKey.executeUpdate();
		ResultSet tableContainingGeneratedIds = preparedSqlInsertOrderStatementWithGeneratedKey.getGeneratedKeys();
		int generatedId = 0;
		while(tableContainingGeneratedIds.next()) {
			generatedId = tableContainingGeneratedIds.getInt(1);
		}
		System.out.println(">> Order added to the Database");
		return generatedId;
	}
	
	@Override
	public int createOrder(Order order) throws SQLException {
		String sqlInsertOrderStatement = "INSERT INTO [Order](Price, DateTime) VALUES (? , ?)";
		PreparedStatement preparedSqlInsertOrderStatementWithGeneratedKey = connectionDB.prepareStatement(sqlInsertOrderStatement, Statement.RETURN_GENERATED_KEYS) ;
		preparedSqlInsertOrderStatementWithGeneratedKey.setDouble(1, order.getOrderPrice());
		preparedSqlInsertOrderStatementWithGeneratedKey.setString(2, order.getOrderDateTime());
		
		preparedSqlInsertOrderStatementWithGeneratedKey.executeUpdate();
		ResultSet tableContainingGeneratedIds = preparedSqlInsertOrderStatementWithGeneratedKey.getGeneratedKeys();
		int generatedId = 0;
		while(tableContainingGeneratedIds.next()) {
			generatedId = tableContainingGeneratedIds.getInt(1);
		}
		order.setId(generatedId);
		
		System.out.println(">> Order added to the Database");
		return generatedId;
	}

	@Override
	public void updateOrder(Order objectToUpdate) throws SQLException {
		String sqlUpdateOrderStatement = "UPDATE [Order] SET [DateTime] = ?, Price = ? WHERE PK_idOrder = ?";
		PreparedStatement preparedUpdateProductStatement = connectionDB.prepareStatement(sqlUpdateOrderStatement);
		preparedUpdateProductStatement.setString(1, objectToUpdate.getOrderDateTime());
		preparedUpdateProductStatement.setDouble(2, objectToUpdate.getOrderPrice());
		preparedUpdateProductStatement.setInt(3, objectToUpdate.getId());
		
		preparedUpdateProductStatement.execute();
		System.out.println(">> Order updated in the Database");
	}

	@Override
	public void deleteOrder(Order objectToDelete) throws Exception {
		if(objectToDelete.getClass().equals(PurchaseOrder.class)) {
			DaoFactory.getPurchaseOrderDao().deletePurchaseOrder((PurchaseOrder) objectToDelete);
		}else if(objectToDelete.getClass().equals(SaleOrder.class)) {
			DaoFactory.getSaleOrderDao().deleteSaleOrder((SaleOrder) objectToDelete);
		}
		String sqlDeleteOrderStatement = "DELETE FROM [Order] WHERE PK_idOrder = ?";
		PreparedStatement preparedDeleteOrderStatement = connectionDB.prepareStatement(sqlDeleteOrderStatement);
		preparedDeleteOrderStatement.setInt(1, objectToDelete.getId());
		preparedDeleteOrderStatement.execute();
		System.out.println(">> Order deleted from the Database");
	}

}
