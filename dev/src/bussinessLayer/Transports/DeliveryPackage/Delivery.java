package bussinessLayer.Transports.DeliveryPackage;

import bussinessLayer.DTOPackage.OrderDTO;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Delivery {

    public enum Status {
        Created , InTransit , Delivered ;
    }
    private String id;
    private Date deliveryDay;
    private Time leavingTime;
    private int driverId;
    private int srcLocation;
    //private List<Integer> targetLocation;
    private int targetLocation;
    private double weight;
    private String truckId;
    //private List<Integer> orders;
    private OrderDTO order;
    private Status status;
    private HashMap<Integer,Integer> amountById;

    public Delivery(String id, Date deliveryDay, Time leavingTime, int driverId, int srcLocation, int targetLocation, double weight, String truckId, OrderDTO order) {
        this.id = id;
        this.deliveryDay = deliveryDay;
        this.leavingTime = leavingTime;
        this.driverId = driverId;
        this.srcLocation = srcLocation;
        this.targetLocation = targetLocation;
        this.weight = weight;
        this.truckId = truckId;
        this.order = order;
        this.status = Status.Created;
        this.amountById=new HashMap<>();
    }
    public Delivery(String id, Date deliveryDay, Time leavingTime, int driverId, int srcLocation, int targetLocation, double weight, String truckId, OrderDTO order,String status,HashMap<Integer,Integer> amountItems) {
        this.id = id;
        this.deliveryDay = deliveryDay;
        this.leavingTime = leavingTime;
        this.driverId = driverId;
        this.srcLocation = srcLocation;
        this.targetLocation = targetLocation;
        this.weight = weight;
        this.truckId = truckId;
        this.order = order;
        this.status = Status.valueOf(status);
        this.amountById=amountItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(Date deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public Time getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(Time leavingTime) {
        this.leavingTime = leavingTime;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getSrcLocation() {
        return srcLocation;
    }

    public void setSrcLocation(int srcLocation) {
        this.srcLocation = srcLocation;
    }

    public int getTargetLocation() {
        return targetLocation;
    }

    public HashMap<Integer, Integer> getAmountById(){return amountById;}

    public void setAmountById(HashMap<Integer, Integer> newList) {this.amountById=newList;}

//    public void removeTargetLocation(int targetLocation) {
//        this.targetLocation.remove(targetLocation);
//    }

//    public void addTargetLocation(int targetLocation) {
//        this.targetLocation.add(targetLocation);
//    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public OrderDTO getOrders() {
        return order;
    }

//    public void removeOrder(int orderId) {
//        this.targetLocation.remove(orderId);
//    }
//
//    public void addOrder(int orderId) {
//        this.targetLocation.add(orderId);
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "id:" + id +
                "\ndeliveryDay:" + dateFormat.format(deliveryDay) +
                "\nleavingTime:" + leavingTime +
                "\ndriverId:" + driverId + '\'' +
                "\nsrcLocation:" + srcLocation + '\'' +
                "\ntargetLocation:" + targetLocation +
                "\nweight=" + weight +
                "\ntruckId='" + truckId + '\'' +
                "\norders=" + order.toString() +
                "\nstatus=" + status +'\n';
    }
}
