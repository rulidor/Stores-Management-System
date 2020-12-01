package bussinessLayer.Transports.DeliveryPackage;

import DataAccessLaye.Transports.DTO;
import ServiceLayer.BranchService;

import java.util.*;

public class LocationController {

    private Map<Integer, Location> locations;
    private static LocationController locationController = null;
    private int index = 1;
    private LocationController()
    {
        this.locations = new HashMap<>();
    }

    public static LocationController getInstance()
    {
        if(locationController == null)
            locationController = new LocationController();
        return locationController;
    }

    public Location getLocation(int id) throws Exception {
        Location l= DataAccessLaye.Transports.Location.checkLocation(id);
        if(l==null)
            throw new Exception("the location doesn't exists");
        return l;
    }

    public Location createLocation(String name, String address, String telNumber, String contactName, String shippingArea) throws Exception {
        if(!telNumber.matches("[0-9]+"))
            throw new Exception("the telephone number contains illegal numbers");
        if(!contactName.matches("[a-zA-Z]+"))
            throw new Exception("the contact name contains illegal characters");
//        if(DataAccessLaye.Transports.Location.checkLocation(id)!=null)
//            throw new Exception("the location already exists");
        if(shippingArea.compareTo("north") != 0 && shippingArea.compareTo("south") != 0 && shippingArea.compareTo("center") != 0)
            throw new Exception("the location area doesn't exist");
        Location location = new Location(index, name, address, telNumber, contactName, shippingArea);
        index++;
        return location;
    }

    public void addLocation(Location location) throws Exception {
        if(!location.getTelNumber().matches("[0-9]+"))
            throw new Exception("the telephone number contains illegal numbers");
        if(!location.getContactName().matches("[a-zA-Z]+"))
            throw new Exception("the contact name contains illegal characters");
        if(DataAccessLaye.Transports.Location.checkLocation(location.getId())!=null)
            throw new Exception("the location already exists");
        if(location.getShippingArea().compareTo("north") != 0 && location.getShippingArea().compareTo("south") != 0 && location.getShippingArea().compareTo("center") != 0)
            throw new Exception("the location area doesn't exist");
        this.locations.put(location.getId(), location);
        DataAccessLaye.Transports.Location.insertLocation(new DTO.Location(location.getId(),location.getName(),location.getAddress(),location.getTelNumber(),location.getContactName(),location.getShippingArea()));
        if (location.getId() > 3) //**Lidor's update: 3 first branches are already initialized in inventory module initialization
        {
            BranchService branchService = new BranchService();
            branchService.createBranch(location.getId(), location.getName());
        }
    }

    public void removeLocation(Location location) throws Exception {
        if(DataAccessLaye.Transports.Location.checkLocation(location.getId())==null)
            throw new Exception("the location doesn't exists");
        //this.locations.remove(location.getId());
        DataAccessLaye.Transports.Location.deleteLocation(location.getId());
    }

    public void changetelNumber(int id, String telNumber) throws Exception {
        if(!telNumber.matches("[0-9]+"))
            throw new Exception("the telephone number contains illegal numbers");
        if(DataAccessLaye.Transports.Location.checkLocation(id)==null)
            throw new Exception("the location doesn't exists");
        //locations.get(id).setTelNumber(telNumber);
        DataAccessLaye.Transports.Location.updateTel(id,telNumber);
    }

    public void changecontactName(int id, String contactName) throws Exception {
        if(!contactName.matches("[a-zA-Z]+"))
            throw new Exception("the contact name contains illegal characters");
        if(DataAccessLaye.Transports.Location.checkLocation(id)==null)
            throw new Exception("the location doesn't exists");
        //locations.get(id).setContactName(contactName);
        DataAccessLaye.Transports.Location.updateName(id,contactName);
    }

    public Map<Integer, Location> getLocations() {
        return locations;
    }

    public void printLocations() throws Exception {
        DataAccessLaye.Transports.Location.printLocation();
    }
}
