package DataAccessLaye;

import bussinessLayer.DTOPackage.CartDTO;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;
import bussinessLayer.DTOPackage.OrderDTO;
import DataAccessLaye.Interfaces.*;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    private Connection conn;

    public OrderDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public OrderDTO find(int orderId) throws Exception {
        String sql = "SELECT * "
                + "FROM Orders WHERE orderId = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, orderId);
      /*  // set the value
        pstmt.set(1, catalogItemId,contractId);*/
        //
        ResultSet rs = pstmt.executeQuery();
        if(!rs.next()) throw new SQLException("Not Found!");
        int orderIds = rs.getInt("orderId");
        int branchId = rs.getInt("branchId");
        try {
        }catch (Exception e){}
        String status = rs.getString("status");
        int supplierId = rs.getInt("supplierId");
        Timestamp creationDate = rs.getTimestamp("creationTime");
        Timestamp deliveryDate=null;
        try{
            deliveryDate = rs.getTimestamp("deliveryDate");
        }catch (Exception e){
        }
        List<LineCatalogItemDTO> lineCatalogItemDTOS = Repo.getInstance().getAllCatalogItemByOrder(orderId);
        int totalAmount = 0;
        double totalPrice = 0;
        int totalAmountRecieved = 0;
        for (LineCatalogItemDTO lineCatalogItemDTO : lineCatalogItemDTOS) {
            totalAmount = totalAmount + lineCatalogItemDTO.getAmount();
            totalPrice = totalPrice + lineCatalogItemDTO.getPriceAfterDiscount();
            totalAmountRecieved += lineCatalogItemDTO.getAmountRecieved();
        }
        CartDTO cartDTO = new CartDTO(lineCatalogItemDTOS, totalAmount, totalPrice, totalAmountRecieved);
        LocalDateTime deliveryLDate = null;
        try{deliveryLDate = deliveryDate.toLocalDateTime();}catch (Exception e) {}
        LocalDateTime creation = creationDate.toLocalDateTime();
		OrderDTO orderDTO = new OrderDTO(orderIds, supplierId, status,creation  , deliveryLDate , cartDTO, branchId); // TO DO : CREAT CART
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAll() throws Exception {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM Orders ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        //
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int orderIds = rs.getInt("orderId");
            int branchId = rs.getInt("branchId");
            String status = rs.getString("status");
            int supplierId = rs.getInt("supplierId");
            Timestamp creationDate = rs.getTimestamp("creationTime");
            Timestamp deliveryDate = rs.getTimestamp("deliveryDate");
            List<LineCatalogItemDTO> lineCatalogItemDTOS = Repo.getInstance().getAllCatalogItemByOrder(orderIds);
            int totalAmount = 0;
            double totalPrice = 0;
            int totalAmountRecieved = 0;
            for (LineCatalogItemDTO lineCatalogItemDTO : lineCatalogItemDTOS) {
                totalAmount = totalAmount + lineCatalogItemDTO.getAmount();
                totalPrice = totalPrice + lineCatalogItemDTO.getPriceAfterDiscount();
                totalAmountRecieved += lineCatalogItemDTO.getAmountRecieved();
            }
            CartDTO cartDTO = new CartDTO(lineCatalogItemDTOS, totalAmount, totalPrice, totalAmountRecieved);
            LocalDateTime estimate; try{estimate = deliveryDate.toLocalDateTime();;}catch(Exception e) {estimate = null;}
            LocalDateTime creation; try{creation = creationDate.toLocalDateTime();;}catch(Exception e) {creation = null;}
            OrderDTO orderDTO = new OrderDTO(orderIds, supplierId, status, creation, estimate, cartDTO, branchId); // TO DO : CREAT CART
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;


    }

    public int getOrdersCounter() throws Exception {
        return findAll().size();
    }

    @Override
    public synchronized int insert(OrderDTO orderDTO) throws Exception {
        String sql = "INSERT INTO Orders(branchId,status,supplierId,creationTime,deliveryDate,orderId) VALUES(?,?,?,?,?,?)";
        
        int orderId = this.findAll().size()+1;

        PreparedStatement pstmt = conn.prepareStatement(sql);
        //pstmt.setInt(1, orderDTO.getOrderId());
        pstmt.setInt(1, orderDTO.getBranchId());
        //pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(orderDTO.getActualDate())); // TODO : chack how actual day delivery initiate???
        //pstmt.setTimestamp(2, null);
        pstmt.setString(2, orderDTO.getOrderStatus());
        pstmt.setInt(3, orderDTO.getSupplierId());
        pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(orderDTO.getCreationDate()));
        Timestamp t;
        try {
            t = java.sql.Timestamp.valueOf(orderDTO.getDeliveryDate());
        }catch (Exception e){
            t=null;
        }
        pstmt.setTimestamp(5, t);
        pstmt.setInt(6, orderId);
        
        pstmt.executeUpdate();
        
        if (orderDTO.getCart().getLineItems().size() > 0) {
            for (LineCatalogItemDTO lineCatalogItemDTO : orderDTO.getCart().getLineItems()) {
                Repo.getInstance().insertLineCatalogItem(lineCatalogItemDTO, orderId);
            }
        }
        return orderId;
    }
    
}
