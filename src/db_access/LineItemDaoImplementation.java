package db_access;

import db_access.DaoInterfaces.LineItemDao;
import model.LineItem;
import model.Product;
import model.PurchaseOrder;
import model.SaleOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LineItemDaoImplementation implements LineItemDao {
    Connection dbCon = DBConnection.getInstance().getDBcon();

    private ArrayList<LineItem> buildObjects(ResultSet rs) throws SQLException{
        ArrayList<LineItem> LineItemList = new ArrayList<LineItem>();
        while(rs.next()) {
            LineItemList.add(buildObject(rs));
        }

        return LineItemList;
    }

    private LineItem buildObject(ResultSet rs) throws SQLException{
        ProductDao productDao = DaoFactory.getProductDao();
        SaleOrderDao saleOrderDao = DaoFactory.getSaleOrderDao();
        // TODO we should maybe use a factory to create model objects
        Product product = productDao.getProductById(rs.getInt("Id"));

        //SaleOrder saleOrder = saleOrderDao.findSaleOrderById(rs.getInt("PK_FK_SaleOrder"));
        LineItem builtObject= null;

            builtObject = new LineItem(rs.getInt("quantity"), product, saleOrder);

        return builtObject;
    }

    @Override
    public LineItem findLineItemBySaleOrderId(int saleOrderId, int productId) throws SQLException {
        String query = "SELECT * FROM LineItem WHERE PK_FK_SaleOrder = ? AND PK_FK_Product = ?";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        preparedSelectStatement.setLong(1, saleOrderId);
        preparedSelectStatement.setLong(2, productId);
        ResultSet rs = preparedSelectStatement.executeQuery();
        LineItem retrievedLineItem =null;
        while(rs.next()) {
            retrievedLineItem = buildObject(rs);
        }

        return retrievedLineItem;
    }

    @Override
    public LineItem findLineItemByPurchaseOrderId(int purchaseOrderId, int productId) throws SQLException {
        String query = "SELECT * FROM LineItem WHERE PK_FK_PurchaseOrder = ? AND PK_FK_Product = ?";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        preparedSelectStatement.setLong(1, purchaseOrderId);
        preparedSelectStatement.setLong(2, productId);
        ResultSet rs = preparedSelectStatement.executeQuery();
        LineItem retrievedLineItem =null;
        while(rs.next()) {
            retrievedLineItem = buildObject(rs);
        }

        return retrievedLineItem;
    }

    @Override
    public ArrayList<LineItem> findLineItemsBySaleOrder(SaleOrder saleOrder) throws SQLException {
        String query = "SELECT * FROM LineItem WHERE PK_FK_SaleOrder = ?";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        preparedSelectStatement.setLong(1, saleOrder.getId());
        ResultSet rs = preparedSelectStatement.executeQuery();
        ArrayList<LineItem> retrievedLineItem = null;
        retrievedLineItem = buildObjects(rs);

        return retrievedLineItem;
    }

    @Override
    public ArrayList<LineItem> findLineItemsByPurchaseOrder(PurchaseOrder purchaseOrder) throws SQLException {
        String query = "SELECT * FROM LineItem WHERE PK_FK_PurchaseOrder = ?";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        preparedSelectStatement.setLong(1, purchaseOrder.getId());
        ResultSet rs = preparedSelectStatement.executeQuery();
        ArrayList<LineItem> retrievedLineItem = null;
        retrievedLineItem = buildObjects(rs);

        return retrievedLineItem;
    }

    @Override
    public ArrayList<LineItem> findLineItemsByProduct(Product product) throws SQLException {
        String query = "SELECT * FROM LineItem WHERE PK_FK_Product = ?";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        preparedSelectStatement.setLong(1, product.getId());
        ResultSet rs = preparedSelectStatement.executeQuery();
        ArrayList<LineItem> retrievedLineItem = null;
        retrievedLineItem = buildObjects(rs);

        return retrievedLineItem;
    }

    @Override
    public ArrayList<LineItem> findAllLineItems() throws SQLException {
        String query = "SELECT * FROM LineItem";
        PreparedStatement preparedSelectStatement = dbCon.prepareStatement(query);
        ResultSet rs = preparedSelectStatement.executeQuery();
        ArrayList<LineItem> retrievedLineItemList = buildObjects(rs);

        return retrievedLineItemList;
    }

//    @Override
//    public boolean createLineItem(LineItem objectToInsert) throws SQLException {
//        String sqlInsertLineItemStatement = "INSERT INTO LineItem(PK_FK_Product, PK_FK_SaleOrder, quantity) "
//                + "VALUES (?,?,?)";
//        PreparedStatement preparedInsertLineItemStatement = dbCon.prepareStatement(sqlInsertLineItemStatement);
//        preparedInsertLineItemStatement.setInt(1, objectToInsert.getProduct().getId());
//        preparedInsertLineItemStatement.setInt(2, objectToInsert.getOrder().getId());
//        preparedInsertLineItemStatement.setInt(3, objectToInsert.getQuantity());
//        preparedInsertLineItemStatement.executeUpdate();
//        return true;
//    }

    @Override
    public void updateLineItem(LineItem objectToUpdate) throws SQLException {

    }

    @Override
    public void deleteLineItem(LineItem objectToDelete) throws SQLException {

    }
}
