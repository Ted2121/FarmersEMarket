package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db_access.DBConnection;
import db_access.DaoInterfaces.SaleOrderDao;
import model.ModelFactory;
import model.PurchaseOrder;
import model.SaleOrder;

public class SaleOrderDaoImplementation implements SaleOrderDao {

	Connection con = DBConnection.getInstance().getDBCon();

	private List<SaleOrder> buildObjects(ResultSet rs) throws SQLException{
		List<SaleOrder> SaleOrderList = new ArrayList<>();
		while(rs.next()) {
			boolean wasProviderLinked = false;
			SaleOrderList.add(buildObject(rs));
		}

		return SaleOrderList;
	}

	private SaleOrder buildObject(ResultSet rs) throws SQLException{
		SaleOrder builtObject = ModelFactory.getSaleOrderModel(rs.getInt("PK_idSaleOrder"));

		return builtObject;
	}

	@Override
	public SaleOrder findSaleOrderById(int saleOrderId) throws SQLException {
		String query = "SELECT * FROM PurchaseOrder WHERE PK_idSaleOrder = ?";
		PreparedStatement preparedSelectStatement = con.prepareStatement(query);
		preparedSelectStatement.setLong(1, saleOrderId);
		ResultSet rs = preparedSelectStatement.executeQuery();
		SaleOrder retrievedSaleOrder = null;
		while(rs.next()) {
			retrievedSaleOrder = buildObject(rs);
		}

		return retrievedSaleOrder;
	}

	@Override
	public List<SaleOrder> findAllSaleOrders() throws SQLException {
		String query = "SELECT * FROM SaleOrder";
		PreparedStatement preparedSelectStatement = con.prepareStatement(query);

		ResultSet rs = preparedSelectStatement.executeQuery();
		List<SaleOrder> retrievedSaleOrderList = buildObjects(rs);

		return retrievedSaleOrderList;
	}

	@Override
	public void createSaleOrder(SaleOrder objectToInsert) throws SQLException {
		String sqlInsertOrderStatement = "INSERT INTO SaleOrder(PK_idSaleOrder, FK_Provider) VALUES (? , ?)";
		PreparedStatement preparedSqlInsertOrderStatement = con.prepareStatement(sqlInsertOrderStatement) ;
		preparedSqlInsertOrderStatement.setInt(1, objectToInsert.getId());
		preparedSqlInsertOrderStatement.setInt(2, objectToInsert.getCustomer().getId());

		preparedSqlInsertOrderStatement.execute();
	}

	@Override
	public void updateSaleOrder(SaleOrder objectToUpdate) throws SQLException {
		String sqlUpdateSaleOrderStatement = "UPDATE SaleOrder SET FK_Provider = ? WHERE PK_idSaleOrder = ?";
		PreparedStatement preparedUpdateProductStatement = con.prepareStatement(sqlUpdateSaleOrderStatement);
		preparedUpdateProductStatement.setInt(1, objectToUpdate.getCustomer().getId());
		preparedUpdateProductStatement.setInt(2, objectToUpdate.getId());
		preparedUpdateProductStatement.execute();

	}

	@Override
	public void deleteSaleOrder(SaleOrder objectToDelete) throws SQLException {
		String sqlDeleteSaleOrderStatement = "DELETE FROM SaleOrder WHERE PK_idSaleOrder = ?";
		PreparedStatement preparedDeleteOrderStatement = con.prepareStatement(sqlDeleteSaleOrderStatement);
		preparedDeleteOrderStatement.setInt(1, objectToDelete.getId());
		preparedDeleteOrderStatement.execute();
	}
}
