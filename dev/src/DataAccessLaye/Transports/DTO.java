package DataAccessLaye.Transports;

import java.sql.Time;
import java.sql.Date;

public class DTO {
    public static class Delivery{
        protected String id;
        protected Date deliveryDay;
        protected Time leavingTime;
        protected int driverId;
        protected int srcLocation;
        protected int targetLocation;
        protected double weight;
        protected String truckId;
        protected int orderId;
        protected String status;
        public Delivery(String id, java.util.Date deliveryDay, Time leavingTime, int driverId, int srcLocation,
                        int targetLocation, double weight, String truckId, int orderId,String status)
        {
            this.id=id;
            this.deliveryDay=new Date(deliveryDay.getTime());
            this.leavingTime=leavingTime;
            this.driverId=driverId;
            this.srcLocation=srcLocation;
            this.targetLocation =targetLocation;
            this.weight=weight;
            this.truckId=truckId;
            this.orderId=orderId;
            this.status=status;
        }

        public String getStatus()
        {
            return status;
        }
    }

    public static class DeliverytargetLocation
    {
        protected String deliveryId;
        protected int targetLocation;

        public DeliverytargetLocation(String deliveryId,int targetLocation)
        {
            this.deliveryId=deliveryId;
            this.targetLocation=targetLocation;
        }
    }

    public static class OrdersForDelivery{
        protected String deliveryId;
        protected int orederId;

        public OrdersForDelivery(String deliveryId,int orederId){
            this.deliveryId=deliveryId;
            this.orederId=orederId;
        }
    }

    public static class Truck{
        protected String id;
        protected String model;
        protected double netoWeight;
        protected double totalWeight;
        protected boolean isUsed;

        public Truck(String id, String model, double netoWeight, double totalWeight,boolean isUsed) {
            this.id = id;
            this.model = model;
            this.netoWeight = netoWeight;
            this.totalWeight = totalWeight;
            this.isUsed = isUsed;
        }
    }

//    public static class Order{
//        protected int id;
//        protected String supplierId;
//        protected int locationId;
//        protected double totalWeight;
//
//        public Order(int id, String supplierId, int locationId, double totalWeight) {
//            this.id = id;
//            this.supplierId = supplierId;
//            this.locationId = locationId;
//            this.totalWeight = totalWeight;
//        }
//    }

    public static class ItemsForOrders{
        protected String deliveryId;
        protected int orderId;
        protected int item;
        protected int qunt;

        public ItemsForOrders(String deliveryID,int orderId,int item,int qunt)
        {
            this.deliveryId=deliveryID;
            this.orderId=orderId;
            this.item=item;
            this.qunt=qunt;
        }
    }

    public static class Driver{
        protected int id;
        protected String lType;
        protected Date expDate;
        protected boolean status;

        public Driver(int id, String lType, java.util.Date expDate, boolean status)
        {
            this.id=id;
            this.lType=lType;
            this.expDate=new Date(expDate.getTime());
            this.status=status;
        }

        public Date getExpDate(){return expDate;}
        public String getLicenseType(){return lType;}
        public java.util.Date getExpLicenseDate(){return new java.util.Date(expDate.getTime());}
    }

    public static class Location{
        protected int id;
        protected String name;
        protected String address;
        protected String telNumber;
        protected String contactName;
        protected String shippingArea;

        public Location(int id, String name, String address, String telNumber, String contactName, String shippingArea) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.telNumber = telNumber;
            this.contactName = contactName;
            this.shippingArea = shippingArea;
        }
    }



}

