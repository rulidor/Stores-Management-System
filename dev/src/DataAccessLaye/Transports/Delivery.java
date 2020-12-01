package DataAccessLaye.Transports;

import DataAccessLaye.Repo;
import bussinessLayer.DTOPackage.CartDTO;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;
import bussinessLayer.DTOPackage.OrderDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Delivery {

    public static void insertDelivery(DTO.Delivery d) throws Exception {
        try   {
            String query = "INSERT OR IGNORE INTO Deliveries VALUES (?, ?, ? ,?,?,?,?,?,?,?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setString(1, d.id);
            stmt.setDate(2, (Date) d.deliveryDay);
            stmt.setTime(3, d.leavingTime);
            stmt.setInt(4, d.driverId);
            stmt.setInt(5, d.srcLocation);
            stmt.setInt(6, d.targetLocation);
            stmt.setDouble(7, d.weight);
            stmt.setString(8, d.truckId);
            stmt.setInt(9,d.orderId);
            stmt.setString(10, d.status);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    /*public static void insertOrdersForDeliveries(DTO.OrdersForDelivery o) throws Exception {
        try   {
            String query = "INSERT OR IGNORE INTO OrdersForDelivery VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,o.deliveryId);
            stmt.setInt(2, o.orederId);

            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }*/

    public static void insertItemsForOrders(DTO.ItemsForOrders i) throws Exception {
        try   {
            String query = "INSERT OR IGNORE INTO ItemsForOrder VALUES (?, ?, ?,?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setString(1,i.deliveryId );
            stmt.setInt(2, i.orderId);
            stmt.setInt(3, i.item);
            stmt.setInt(4,i.qunt);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;        }
    }

    public static void insertDeliveryTargetLocation(DTO.DeliverytargetLocation d) throws Exception {
        try   {
            String query = "INSERT OR IGNORE INTO LocationsForDelivery VALUES (?, ?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setString(1,d.deliveryId);
            stmt.setInt(2,d.targetLocation);

            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public static bussinessLayer.Transports.DeliveryPackage.Delivery checkDelivery(String id) throws Exception {
        try   {
            String sql = "SELECT * From Deliveries WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
           /* List<Integer> locations=getTargetLocations(id);
            List<Integer> orders=getOrdersForDelivery(id);*/
           OrderDTO order= findOrder(results.getInt(9));
           HashMap<Integer,Integer> amountItems=getItemsForOrder(id,order.getOrderId());
             return new bussinessLayer.Transports.DeliveryPackage.Delivery(results.getString(1),results.getDate(2),results.getTime(3),results.getInt(4),results.getInt(5),
                     results.getInt(6),results.getDouble(7),results.getString(8),order,results.getString(10),amountItems);

        } catch (Exception e) {
            throw e;
        }
    }

    public static List<Integer> getTargetLocations(String id) throws Exception {
        List<Integer> locations=new ArrayList<>();
        try   {
            String sql = "SELECT * From LocationsForDelivery WHERE DELIVERY_ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                locations.add(results.getInt(2));
            }
        } catch (Exception e) {
            throw e;
        }
        return locations;
    }

    public static List<Integer> getOrdersForDelivery(String id) throws Exception {
        List<Integer> orders=new ArrayList<>();
        try   {
            String sql = "SELECT * From OrdersForDelivery WHERE DELIVERY_ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                orders.add(results.getInt(2));
            }
        } catch (Exception e) {
            throw e;
        }
        return orders;
    }

        public static HashMap<Integer,Integer> getItemsForOrder(String deliveryID,int orderId) throws Exception {
        HashMap<Integer,Integer> ItemsMap=new HashMap<>();
        try   {
            String sql = "SELECT * From ItemsForOrder WHERE DELIVERY_ID=? AND ORDER_ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,deliveryID);
            pst.setInt(2,orderId);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            int  item = results.getInt(3);
            int qun=results.getInt(4);
            ItemsMap.put(item,qun);
            while (results.next()) {
                int  item1 = results.getInt(3);
                int qun1=results.getInt(4);
                ItemsMap.put(item1,qun1);
                }
        } catch (Exception e) {
            throw e;        }
        return ItemsMap;
    }


    public static void deleteDelivery(String id) throws Exception {
        try   {
            /*deleteLocationsForDel(id);
            deleteOrdersForDelivery(id);*/
            String sql1 = "DELETE FROM Deliveries WHERE ID=? ";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

    public static void deleteOrdersForDelivery(String id) throws Exception {
        try   {
            String sql1 = "DELETE FROM OrdersForDelivery WHERE DELIVERY_ID=? ";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

    public static void deleteLocationsForDel(String id) throws Exception {
        try   {
            String sql1 = "DELETE FROM LocationsForDelivery WHERE DELIVERY_ID=? ";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

    public static boolean checkDTforDate(String id,Date date,int driver,String truck) throws Exception {
        try   {
            String sql1 = "SELECT * From Deliveries WHERE ID<>? AND(  (DELIVERY_DATE=? AND DRIVER_ID=?) OR(DELIVERY_DATE=? AND TRUCK_ID=?)) ";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.setDate(2,date);
            pst1.setInt(3,driver);
            pst1.setDate(4,date);
            pst1.setString(5,truck);
            ResultSet results1 = pst1.executeQuery();
            if(results1.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public static void printDeliveries() throws Exception {
        try   {
            String sql = "SELECT * From Deliveries ";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                String  id = results.getString(1);
                Date date = results.getDate(2);
                Time time=results.getTime(3);
                int driver=results.getInt(4);
                int srcLoc=results.getInt(5);
                int targ=results.getInt(6);
                double weight=results.getDouble(7);
                String truck=results.getString(8);
                int orderId=results.getInt(9);
                String status=results.getString(10);

                System.out.println("Delivery "+id+"\ndelivery date: "+date+"\nleaving time: "+time+"\ndriver ID: "+driver+"\nsupplier id: "+srcLoc
                        +"\ntarget location: "+targ+"\ndelivery weight: "+weight+"\ntruck id: "+truck+"\norder id: "+orderId+"\nstatus: "+status+"\n");


            }

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateDeliveryDay(String id, java.util.Date deliveryDay) throws Exception {
        try   {
            String sql = "UPDATE Deliveries SET DELIVERY_DATE =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDate(1,new Date(deliveryDay.getTime()));
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateLeavingTime(String id, Time time) throws Exception {
        try   {
            String sql = "UPDATE Deliveries SET DELIVER_TIME =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setTime(1,time);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateDriverId(String id, int driverId) throws Exception {
        try   {
            String sql = "UPDATE Deliveries SET DRIVER_ID =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,driverId);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkDriverForDel(String id,Date date,int driver) throws Exception {
        try   {
            String sql1 = "SELECT * From Deliveries WHERE ID<>? AND ((DELIVERY_DATE=? AND DRIVER_ID=?)) ";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.setDate(2,date);
            pst1.setInt(3,driver);

            ResultSet results1 = pst1.executeQuery();
            if(results1.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public static void updateDelWeight(String id, double weight) throws Exception {
        try   {
            String sql = "UPDATE Deliveries SET WEIGHT =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDouble(1,weight);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }
        public static void deleteItem(String deliveryID,int orderId,int item) throws Exception {
        try   {
            String sql = "DELETE FROM ItemsForOrder WHERE DELIVERY_ID=? AND ORDER_ID=? AND ITEM=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,deliveryID);
            pst.setInt(2,orderId);
            pst.setInt(3,item);

            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

        public static void updatQunt(String delieryID,int orderID, int item, int qunt) throws Exception {
        try   {
            String sql = "UPDATE ItemsForOrder SET QUINTITY =? WHERE DELIVERY_ID=? AND ORDER_ID=? AND ITEM=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,qunt);
            pst.setString(2,delieryID);
            pst.setInt(3,orderID);
            pst.setInt(4,item);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static String getDeliveryByOrderID(int orderID) throws Exception {
        try  {
            String sql = "SELECT ID FROM Deliveries WHERE ORDER_ID = ?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,orderID);
            ResultSet results = pst.executeQuery();
            String id = "";
            while (results.next()) {
                id = results.getString(1);
            }
            return id;
        } catch (Exception e) {
            throw e;        }
    }

    public static boolean checkTruckForDel(String id,Date date,String truck) throws Exception {
        try   {
            String sql1 = "SELECT * From Deliveries WHERE ID<>? AND ((DELIVERY_DATE=? AND TRUCK_ID=?)) ";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.setDate(2,date);
            pst1.setString(3,truck);

            ResultSet results1 = pst1.executeQuery();
            if(results1.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public static void updateTruckID(String id, String  truck) throws Exception {
        try   {
            String sql = "UPDATE Deliveries SET TRUCK_ID =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,truck);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void removeOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        try   {
            String sql1 = "DELETE FROM OrdersForDelivery WHERE DELIVERY_ID=? AND ORDER_ID=? ";
            PreparedStatement pst = Repo.con.prepareStatement(sql1);
            pst.setString(1,id);
            pst.setInt(2,orderId);
            pst.executeUpdate();

            String sql2 = "DELETE FROM LocationsForDelivery WHERE DELIVERY_ID=? AND LOCATION_ID=?";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql2);
            pst1.setString(1,id);
            pst1.setInt(2,locationId);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void addOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        try   {
            String sql1 = "INSERT INTO OrdersForDelivery VALUES (?,?) ";
            PreparedStatement pst = Repo.con.prepareStatement(sql1);
            pst.setString(1,id);
            pst.setInt(2,orderId);
            pst.executeUpdate();

            String sql2 = "INSERT INTO LocationsForDelivery VALUES (?,?)";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql2);
            pst1.setString(1,id);
            pst1.setInt(2,locationId);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateStatus(String id,String status) throws Exception {
        try   {
            String sql = "UPDATE Deliveries SET STATUS =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,status);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkOrder(int id) throws Exception {
        try   {
            String sql1 = "SELECT * From OrdersForDelivery WHERE ORDER_ID=? AND DELIVERY_ID in(SELECT ID FROM Deliveries WHERE STATUS=InTransit OR STATUS=Delivered)";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql1);
            pst1.setInt(1,id);

            ResultSet results1 = pst1.executeQuery();
            if(results1.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
        public static void printTargetLocation(String id) throws Exception {
        try   {
            String sql = "SELECT * From LocationsForDelivery WHERE DELIVERY_ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            System.out.print("target locations: [");
            while (results.next()) {
                int location=results.getInt(2);
                System.out.print(location+" ");
            }
            System.out.print("]");

            System.out.println();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void printOrdersForDel(String id) throws Exception {
        try   {
            String sql = "SELECT * From OrdersForDelivery WHERE DELIVERY_ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            System.out.print("orders: [");
            while (results.next()) {
                int orders=results.getInt(2);
                System.out.print(orders+" ");
            }
            System.out.print("]");

            System.out.println();

        } catch (Exception e) {
            throw e;
        }
    }

    public static OrderDTO findOrder(int orderId) throws Exception {
        String sql = "SELECT * "
                + "FROM Orders WHERE orderId = ? ";

        PreparedStatement pstmt = Repo.con.prepareStatement(sql);
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

}
