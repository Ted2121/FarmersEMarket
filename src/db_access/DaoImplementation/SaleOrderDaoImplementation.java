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
import model.SaleOrder;

public class SaleOrderDaoImplementation implements SaleOrderDao {

	Connection con = DBConnection.getInstance().getDBCon();
	//ProductDaoImplementation productDao = DaoFactory.getProductDao();

	private List<SaleOrder> buildObjects(ResultSet rs) throws SQLException{
		List<SaleOrder> SaleOrderList = new ArrayList<>();
		while(rs.next()) {
			SaleOrderList.add(buildObject(rs));
		}

		return SaleOrderList;
	}

	private SaleOrder buildObject(ResultSet rs) throws SQLException{
		SaleOrder builtObject = ModelFactory.getSaleOrderModel(rs.getInt("Id"));

		return builtObject;
	}

	@Override
	public SaleOrder findSaleOrderByCustomerId(int customerId) throws SQLException {
		String query = "SELECT SaleOrder INNER JOIN Customer ON SaleOrder.FK_Customer = Customer_PK_idCustomer WHERE id = ?";
		PreparedStatement preparedSelectStatement = con.prepareStatement(query);
		preparedSelectStatement.setLong(1, customerId);
		ResultSet rs = preparedSelectStatement.executeQuery();
		SaleOrder retrievedSaleOrder = null;
		while(rs.next()) {
			retrievedSaleOrder = buildObject(rs);
		}

		return retrievedSaleOrder;
	}

	@Override
	public ArrayList<SaleOrder> findAllSaleOrders() throws SQLException {
		return null;
	}

	@Override
	public int createSaleOrder(SaleOrder objectToInsert) throws SQLException {
		return 0;
	}

	@Override
	public void updateSaleOrder(SaleOrder objectToUpdate) throws SQLException {

	}

	@Override
	public void deleteSaleOrder(SaleOrder objectToDelete) throws SQLException {

	}
}
