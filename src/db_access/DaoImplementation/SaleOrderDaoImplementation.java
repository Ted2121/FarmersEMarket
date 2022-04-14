package db_access.DaoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db_access.DBConnection;
import db_access.DaoFactory;
import db_access.DaoInterfaces.SaleOrderDao;
import model.*;

public class SaleOrderDaoImplementation implements SaleOrderDao {

	Connection con = DBConnection.getInstance().getDBCon();

	private List<SaleOrder> buildObjects(ResultSet rs, boolean retrieveCustomer, boolean retrieveLineItem) throws Exception{
		List<SaleOrder> saleOrderList = new ArrayList<>();
		while(rs.next()) {
			SaleOrder retrievedSaleOrder = buildObject(rs);
			if(retrieveCustomer) {
				Customer retrievedCustomerLinkedToThisSaleOrder = DaoFactory.getCustomerDao().findCustomerById(rs.getInt("FK_Customer"));
				retrievedSaleOrder.setCustomer(retrievedCustomerLinkedToThisSaleOrder);
			}

			if(retrieveLineItem) {
				List<LineItem> lineItemsOfTheOrder = retrievedSaleOrder.getLineItems();
				lineItemsOfTheOrder.addAll(DaoFactory.getLineItemDao().findLineItemsByOrder(retrievedSaleOrder, true));
			}

			saleOrderList.add(retrievedSaleOrder);

		}

		return saleOrderList;
	}

	private SaleOrder buildObject(ResultSet rs) throws SQLException{
		SaleOrder builtObject = ModelFactory.getSaleOrderModel(rs.getInt("PK_idSaleOrder"));

		return builtObject;
	}

	@Override
	public SaleOrder findSaleOrderById(int saleOrderId, boolean retrieveCustomer, boolean retrieveLineItem) throws SQLException {
		//Retrieving the SaleOrder from the database
		String query = "SELECT * FROM SaleOrder WHERE PK_idSaleOrder = ?";
		PreparedStatement preparedSelectStatement = con.prepareStatement(query);
		preparedSelectStatement.setInt(1, saleOrderId);
		ResultSet rs = preparedSelectStatement.executeQuery();
		SaleOrder retrievedSaleOrder = null;
		while(rs.next()) {
			//Building the SaleOrder object
			retrievedSaleOrder = buildObject(rs);

			//If we want to set the Customer, we just specify we want to retrieve the Customer as a parameter of this method
			if(retrieveCustomer) {
				//If we want to retrieve the Customer, we get it from the CustomerDao
				Customer retrievedCustomerLinkedToThisSaleOrder = DaoFactory.getCustomerDao().findCustomerById(rs.getInt("FK_Customer"));
				//And we set it as the customer of its object
				retrievedSaleOrder.setCustomer(retrievedCustomerLinkedToThisSaleOrder);
			}

			//If we want to set the LineItems, we just specify we want to retrieve the LineItem as a parameter of this method
			if(retrieveLineItem) {
				//If we want to retrieve the LineItems, we get the ArrayList of the retrieved PurchaseOrder
				List<LineItem> lineItemOfTheOrder = retrievedSaleOrder.getLineItems();

				//We get all the LineItems from the LineItemDao and add each of them to the ArrayList we retrieve earlier
				try {
					lineItemOfTheOrder.addAll(DaoFactory.getLineItemDao().findLineItemsByOrder(retrievedSaleOrder, true));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return retrievedSaleOrder;
	}

	@Override
	public List<SaleOrder> findAllSaleOrders(boolean retrieveCustomer, boolean retrieveLineItem) throws Exception {
		String query = "SELECT * FROM SaleOrder";
		PreparedStatement preparedSelectStatement = con.prepareStatement(query);

		ResultSet rs = preparedSelectStatement.executeQuery();
		List<SaleOrder> retrievedSaleOrderList = buildObjects(rs, retrieveCustomer, retrieveLineItem);

		for(SaleOrder saleOrder : retrievedSaleOrderList) {
			if(retrieveCustomer) {
				//If we want to retrieve the customer, we get it from the CustomerDao
				Customer retrievedCustomerLinkedToThisSaleOrder = DaoFactory.getCustomerDao().findCustomerById(rs.getInt("FK_Customer"));
				//And we set it as the customer of its object
				saleOrder.setCustomer(retrievedCustomerLinkedToThisSaleOrder);
			}

			//If we want to set the LineItems, we just specify we want to retrieve the LineItem as a parameter of this method
			if(retrieveLineItem) {
				//If we want to retrieve the LineItems, we get the ArrayList of the retrieved SaleOrder
				List<LineItem> lineItemOfTheOrder = saleOrder.getLineItems();

				//We get all the LineItems from the LineItemDao and add each of them to the ArrayList we retrieve earlier
				
					lineItemOfTheOrder.addAll(DaoFactory.getLineItemDao().findLineItemsByOrder(saleOrder, true));
			
			}
		}

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
