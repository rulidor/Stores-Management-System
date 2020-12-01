package ServiceLayer;

import bussinessLayer.Transports.DeliveryPackage.*;
import bussinessLayer.DTOPackage.OrderDTO;

import java.sql.Time;
import java.util.Date;

public class DeliveryService {

    private DeliveryController deliveryController;
    private static DeliveryService deliveryService = null;

    private DeliveryService()
    {
        deliveryController = DeliveryController.getInstance();
    }

    public static DeliveryService getInstance()
    {
        if(deliveryService == null)
            deliveryService = new DeliveryService();
        return deliveryService;
    }

    public Location createLocation(String name, String address, String telNumber, String contactName, String shippingArea) throws Exception
    {
        try
        {
            Location l = deliveryController.createLocation(name, address, telNumber, contactName, shippingArea);
            return l;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addLocation(Location location) throws Exception
    {
        try
        {
            deliveryController.addLocation(location);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeLocation(int id) throws Exception
    {
        try
        {
            deliveryController.removeLocation(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void changetelNumber(int id, String telNumber) throws Exception
    {
        try
        {
            deliveryController.changetelNumber(id, telNumber);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void changecontactName(int id, String contactName) throws Exception
    {
        try
        {
            deliveryController.changecontactName(id, contactName);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Truck createTruck(String id, String model, double netoWeight, double totalWeight) throws Exception
    {
        try
        {
            Truck t = deliveryController.createTruck(id, model, netoWeight, totalWeight);
            return t;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addTruck(Truck truck) throws Exception
    {
        try
        {
            deliveryController.addTruck(truck);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Truck getTruck(String id) throws Exception {
        try
        {
            return deliveryController.getTruck(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeTruck(String id) throws Exception
    {
        try
        {
            deliveryController.removeTruck(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setTruckUsed(String id) throws Exception
    {
        try
        {
            deliveryController.setTruckUsed(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setTruckNotUsed(String id) throws Exception
    {
        try
        {
            deliveryController.setTruckNotUsed(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Delivery createDelivery( Date deliveryDay, Time leavingTime, int driverId, int srcLocation, int targetLocation,
                                   String truckId, OrderDTO order) throws Exception
    {
        try {
            Delivery d= deliveryController.createDelivery(deliveryDay, leavingTime, driverId, srcLocation, targetLocation, truckId, order);

            return d;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Delivery createDelivery(Date deliveryDay, int srcLocation, int targetLocation, OrderDTO order) throws Exception {
        try
        {
            Delivery d = deliveryController.createDelivery(order);
            return d;
        }
        catch (Exception e)
        {
            throw e;
        }
    }


//    public boolean checkArea(List<Integer> locationAreas){
//        try
//        {
//            return deliveryController.checkArea(locationAreas);
//        }
//        catch (Exception e)
//        {
//            return false;
//        }
//    }

    public Delivery getDelivery(String id) throws Exception {
        try
        {
            return deliveryController.getDelivery(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addDelivery(Delivery delivery) throws Exception {
        try
        {
            deliveryController.addDelivery(delivery);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeDelivery(int id) throws Exception {
       try
       {
           deliveryController.removeDelivery(DataAccessLaye.Transports.Delivery.getDeliveryByOrderID(id));
       }
       catch (Exception e)
       {
           throw e;
       }
    }

    public void changeDeliveryDay(String id, Date deliveryDay) throws Exception {
        try
        {
            deliveryController.changeDeliveryDay(id, deliveryDay);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeLeavingTime(String id, Time leavingTime) throws Exception {
        try
        {
            deliveryController.changeLeavingTime(id, leavingTime);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeDriverId(String id, int driverId) throws Exception {
        try
        {
            deliveryController.changeDriverId(id, driverId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeItemFromOrder(String deliveryId,int itemId) throws Exception {
        try
        {
            deliveryController.removeItem(deliveryId,itemId);
        }catch (Exception e) {
            throw e;
        }
    }

    public void changeQunForItem(String deliveryId,int item,int Quantity) throws Exception {
        try{
            deliveryController.changeQuantity(deliveryId,item,Quantity);
        }catch(Exception e)
        {
            throw e;
        }
    }


    public void changeTruckId(String id, String truckId) throws Exception {
        try
        {
            deliveryController.changeTruckId(id, truckId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean changeStatus(String id, String status) throws Exception {
        try
        {
           return deliveryController.changeStatus(id, status);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Date getDeliveryDate(String id) throws Exception {
        try {
            return deliveryController.getDeliveryDate(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public double getDeliveryTruckWeight(String id) throws Exception {
        try {
            return deliveryController.getDeliveryTruckWeight(id);
        }
        catch (Exception e){
            throw e;
        }
        }
    public double getWeightForType(String type)
    {
        return deliveryController.getWeightForType(type);
    }
    public int getDeliveryDriverID(String id) throws Exception
    {
        try
        {
            return deliveryController.getDeliveryDriverID(id);
        }catch (Exception e){
            throw e;
        }
    }

    public Time getDeliveryLeavingTime(String id) throws Exception
    {
        try {
            return deliveryController.getDeliveryLeavingTime(id);
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void printDeliveries() throws Exception { deliveryController.printDeliveries();}
    //public void printOrders() throws SQLException { deliveryController.printOrders();}
    public void printTrucks() throws Exception { deliveryController.printTrucks();}
    public void printLocations() throws Exception { deliveryController.printLocations();}
}
