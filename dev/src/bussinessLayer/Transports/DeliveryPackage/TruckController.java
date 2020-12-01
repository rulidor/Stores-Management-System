package bussinessLayer.Transports.DeliveryPackage;

import DataAccessLaye.Transports.DTO;

import java.util.*;

public class TruckController {

    private Map<String, Truck> trucks;
    private static TruckController truckController = null;

    private TruckController() {
        this.trucks = new HashMap<>();
    }

    public static TruckController getInstance() {
        if (truckController == null)
            truckController = new TruckController();
        return truckController;
    }

    public Truck getTruck(String id) throws Exception {
        Truck t= DataAccessLaye.Transports.Truck.checkTruck(id);
        if (t==null)
            throw new Exception("the truck doesn't exists");
        return t;
    }

    public Truck createTruck(String id, String model, double netoWeight, double totalWeight) throws Exception {
        if (DataAccessLaye.Transports.Truck.checkTruck(id)!=null )
            throw new Exception("the truck already exists");
        if (netoWeight < 0 || totalWeight < 0)
            throw new Exception("weight cannot be lower than 0");
        if (netoWeight > totalWeight)
            throw new Exception("neto cannot be bigger than the total");
        Truck truck = new Truck(id, model, netoWeight, totalWeight);
        return truck;
    }

    public void addTruck(Truck truck) throws Exception {
        if (DataAccessLaye.Transports.Truck.checkTruck(truck.getId())!=null)
            throw new Exception("the truck already exists");
        this.trucks.put(truck.getId(), truck);
        DataAccessLaye.Transports.Truck.insertTruck(new DTO.Truck(truck.getId(),truck.getModel(),truck.getNetoWeight(),truck.getTotalWeight(),truck.isUsed()));

    }

    public void removeTruck(String id) throws Exception {
        if (DataAccessLaye.Transports.Truck.checkTruck(id)==null)
            throw new Exception("the truck doesn't exists");
        //this.trucks.remove(id);
        try{
            DataAccessLaye.Transports.Truck.deleteTruck(id);
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void setTruckUsed(String id) throws Exception {
        if (DataAccessLaye.Transports.Truck.checkTruck(id)==null)
            throw new Exception("the truck doesn't exists");
        //trucks.get(id).setUsed();
        DataAccessLaye.Transports.Truck.updateUsed(id,true);
    }

    public void setTruckNotUsed(String id) throws Exception {
        if (DataAccessLaye.Transports.Truck.checkTruck(id)==null)
            throw new Exception("the truck doesn't exists");
        //trucks.get(id).setNotUsed();
        DataAccessLaye.Transports.Truck.updateUsed(id,false);
    }

    public Map<String, Truck> getTrucks() {
        return trucks;
    }

    public void printTrucks() throws Exception {
        DataAccessLaye.Transports.Truck.printTrucks();
    }
}

