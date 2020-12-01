package ServiceLayer;

import bussinessLayer.Employees.Employee;
import bussinessLayer.Employees.WorkingSchedule;
import bussinessLayer.Transports.DeliveryPackage.Delivery;
import bussinessLayer.Transports.DeliveryPackage.Location;
//import bussinessLayer.Transports.DeliveryPackage.Order;
import bussinessLayer.Transports.DeliveryPackage.Truck;
import bussinessLayer.Transports.DriverPackage.Driver;
import bussinessLayer.DTOPackage.OrderDTO;
import javafx.util.Pair;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Service {

    private DeliveryService deliveryService;
    private EmployeeService employeeService;
    private ScheduleService scheduleService;
    private OrderService orderService;
    private static Service service = null;

    public Service(){
        deliveryService =DeliveryService.getInstance();
        employeeService = new EmployeeService();
        scheduleService = new ScheduleService();
        orderService=OrderService.getInstance();
    }

    public static Service getInstance()
    {
        if(service == null)
            service = new Service();
        return service;
    }
/////////////////////////////////////////////////DELIVERY//////////////////////////////////////////////////////////////
    public Delivery createDelivery( String orderID,String truckId,int driverId) throws Exception
    {
        try
        {
            ResponseT<OrderDTO> res= orderService.getOrderDetails(orderID);
            Date deliveryDate=Date.from(res.getObj().getDeliveryDate().atZone(ZoneId.systemDefault()).toInstant());
            Time time = new Time(res.getObj().getDeliveryDate().getHour(), res.getObj().getDeliveryDate().getMinute(), res.getObj().getDeliveryDate().getSecond());
            boolean isValid = employeeService.checkLicence(driverId, deliveryDate);
            Delivery d = null;
            if(isValid)
                d = deliveryService.createDelivery(deliveryDate, time, driverId, res.getObj().getSupplierId(), res.getObj().getBranchId(), truckId, res.getObj());
            return d;
        }
        catch (Exception e)
        {
            throw e;
        }
    }


    public Location createLocation(String name, String address, String telNumber, String contactName, String shippingArea) throws Exception
    {
        try
        {
            return deliveryService.createLocation(name, address, telNumber, contactName, shippingArea);

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
            deliveryService.addLocation(location);
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
            deliveryService.removeLocation(id);
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
            deliveryService.changetelNumber(id, telNumber);
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
            deliveryService.changecontactName(id, contactName);
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
            return deliveryService.createTruck(id, model, netoWeight, totalWeight);

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
            deliveryService.addTruck(truck);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Truck getTruck(String id) throws Exception {
        try
        {
            return deliveryService.getTruck(id);
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
            deliveryService.removeTruck(id);
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
            deliveryService.setTruckUsed(id);
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
            deliveryService.setTruckNotUsed(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
//    public Order createOrder(int id, Map<String, Integer> items, String supplierId, int locationId, double totalWeight) throws Exception
//    {
//        try
//        {
//             return deliveryService.createOrder(id, items, supplierId, locationId, totalWeight);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//
//    public Map<Integer, Order> getOrders()
//    {
//        return deliveryService.getOrders();
//    }
//
//    public void addOrder(Order order) throws Exception
//    {
//        try
//        {
//            deliveryService.addOrder(order);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void removeOrder(int id) throws Exception
//    {
//
//        try
//        {
//            deliveryService.removeOrder(id);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void addItem(int id, String item, int quantity) throws Exception
//    {
//        try
//        {
//            deliveryService.addItem(id, item, quantity);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void removeItem(int id, String item) throws Exception
//    {
//        try
//        {
//            deliveryService.removeItem(id, item);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void changeQuantity(int id, String item, int quantity) throws Exception
//    {
//        try
//        {
//            deliveryService.changeQuantity(id, item, quantity);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void changeTotalWeight(int id, double totalWeight) throws Exception
//    {
//        try
//        {
//            deliveryService.changeTotalWeight(id, totalWeight);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }

//    public boolean checkArea(List<Integer> locationAreas){
//        try
//        {
//            return deliveryService.checkArea(locationAreas);
//        }
//        catch (Exception e)
//        {
//            return false;
//        }
//    }

    public void addDelivery(Delivery delivery) throws Exception {
        try
        {
            deliveryService.addDelivery(delivery);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeDelivery(String id) throws Exception {
        try
        {
            deliveryService.removeDelivery(Integer.parseInt(id));
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeDeliveryDay(String id, Date deliveryDay) throws Exception {
        try
        {
            deliveryService.changeDeliveryDay(id, deliveryDay);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeLeavingTime(String id, Time leavingTime) throws Exception {
        try
        {
            deliveryService.changeLeavingTime(id, leavingTime);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeDriverId(String id, int driverId) throws Exception {
        try
        {
            Date d=deliveryService.getDeliveryDate(id);
            boolean b=employeeService.checkLicence(driverId,d);

            if(!b)
                throw new Exception("drivers license not valid");
            String lType=employeeService.getDriversLicesnesType(driverId); // drivers license type
            double lTypeWe=deliveryService.getWeightForType(lType); // max weight driver can drive
            double weight=deliveryService.getDeliveryTruckWeight(id); // weight of truck for delivery

            if(lTypeWe < weight)
                throw new Exception("the driver cannot drive the truck");
            deliveryService.changeDriverId(id,driverId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeItemFromOrder(String deliveryId, int itemId) throws Exception {
        try{
            deliveryService.removeItemFromOrder(deliveryId,itemId);
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void changeQunForItemInOrder(String deliveryId,int itemID,int quantity) throws Exception {
        try{
            deliveryService.changeQunForItem(deliveryId,itemID,quantity);
        }catch (Exception e)
        {
            throw e;
        }
    }
    
    public void printItemInOrder(String delivaryId) throws Exception {
        try
        {
            HashMap<Integer,Integer> order=deliveryService.getDelivery(delivaryId).getAmountById();
            for (Map.Entry<Integer, Integer> entry : order.entrySet()) {
                System.out.println("item id: "+entry.getKey()+", item quantity: "+entry.getValue());
            }
        }catch(Exception e)
        {
          throw e;
        }
    }
    /*public void removeOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        try
        {
            deliveryService.removeOrderAndLocation(id, locationId, orderId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void addOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        try
        {
            deliveryService.addOrderAndLocation(id, locationId, orderId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeWeight(String id, double weight) throws Exception {
        try
        {
            deliveryService.changeWeight(id, weight);
        }
        catch (Exception e)
        {
            throw e;
        }
    }*/

    public void changeTruckId(String id, String truckId) throws Exception {
        try
        {
            /*int driverid=deliveryService.getDeliveryDriverID(id);
            String lType=employeeService.getDriversLicesnesType(driverid); // drivers license type
            double lTypeWe=deliveryService.getWeightForType(lType); // max weight driver can drive
            double weight=deliveryService.getDeliveryTruckWeight(id); // weight of truck for delivery
            if(lTypeWe < weight)
                throw new Exception("the driver cannot drive the truck");
*/
            deliveryService.changeTruckId(id, truckId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeStatus(String id, String status) throws Exception {
        // need to add check if driver has a shift during the same time a the delivery
        try
        {
            int driverId=deliveryService.getDeliveryDriverID(id);
            String lType=employeeService.getDriversLicesnesType(driverId); // drivers license type
            double lTypeWe=deliveryService.getWeightForType(lType); // max weight driver can drive
            double weight=deliveryService.getDeliveryTruckWeight(id); // weight of truck for delivery
            if(lTypeWe < weight)
                throw new Exception("the driver cannot drive the truck");
            Time t=deliveryService.getDeliveryLeavingTime(id);
            Date date=deliveryService.getDeliveryDate(id);
            String shifTime;
            if(t.after(Time.valueOf("7:00:00")) && t.before(Time.valueOf("14:00:00")))
                shifTime="Morning";
            else
                shifTime="Evening";
            LocalDate d = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(date) ); // convert to localDate
            if(!scheduleService.checkForDriver(driverId,shifTime,d))
                throw new Exception("the driver isn't working during the shift");
            if(!scheduleService.checkStoreKeeper(shifTime,d))
                throw new Exception("the store keeper isn't working during the shift");
            boolean statusChanged=deliveryService.changeStatus(id,status);

            if(statusChanged)
            {
                employeeService.setDriversStatus(driverId,status);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void printDeliveries() throws Exception {
        try {
            deliveryService.printDeliveries();
        } catch (SQLException e) {
            throw e;
        }
    }
    //public void printOrders() throws SQLException { deliveryService.printOrders();}
    public void printTrucks() throws Exception { deliveryService.printTrucks();}
    public void printLocations() throws Exception { deliveryService.printLocations();}
/////////////////////////////////////////////EMPLOYEE/////////////////////////////////////////////////////////
    public boolean registerEmployee(Employee e) throws Exception { return employeeService.registerEmployee(e); }

    public boolean registerDriver(Driver d) throws Exception { return employeeService.registerDriver(d); }

    public Employee createEmployee(String name, Integer ID, Integer bankAccount,
                                   Integer salary, Integer vacationDays, LinkedList<String> roles) throws Exception {
        Employee e = employeeService.createEmployee(name, ID, bankAccount, salary, vacationDays, roles);

        if (e == null)
            throw new Exception("registration was not completed");

        return e;
    }

    public Driver createDriver(String name, Integer ID, Integer bankAccount, Integer salary, Integer vacationDays,
                               LinkedList<String> roles, String licenseType, Date expLicenseDate) throws Exception
    {
        return employeeService.createDriver(name, ID, bankAccount, salary, vacationDays, roles, licenseType, expLicenseDate);
    }

    public void addRole(Integer ID, String role) throws Exception { employeeService.addRole(ID, role); }

    public void setEmployeeName(Integer ID, String name) throws Exception {
        employeeService.setEmployeeName(ID, name);
    }

    public void setBankAccount(Integer ID, Integer bankAccount) throws Exception { employeeService.setBankAccount(ID, bankAccount); }

    public void setSalary(Integer ID, Integer salary) throws Exception {
        employeeService.setSalary(ID, salary);
    }

    public void setVacationDays(Integer ID, Integer vacationDays) throws Exception { employeeService.setVacationDays(ID, vacationDays); }

    public void addConstraints(Integer ID, Pair<String,String> p) throws Exception { employeeService.addConstraints(ID, p); }

    public void deleteEmployee(Integer ID) throws Exception {
        try
        {
            employeeService.deleteEmployee(ID);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /*public Driver getDriver(String id) throws Exception {
        if(!drivers.containsKey(id))
            throw new Exception("the driver doesn't exists");
        return drivers.get(id);
    }

    public void addDriver(Driver driver) throws Exception {
        if(drivers.containsKey(driver.getID()))
            throw new Exception("the driver already exists");
        this.drivers.put(driver.getID(), driver);
    }*/

    public void changeExpDate(Integer id, Date expLicenseDate) throws Exception {
        try
        {
            employeeService.changeExpDate(id, expLicenseDate);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeLicenseType(Integer id, String licenseType) throws Exception {
        try
        {
            employeeService.changeLicenseType(id, licenseType);
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public void addRole(Integer ID, String role,String licensType,Date date) throws Exception {
        try
        {
            employeeService.addRole(ID,role,licensType,date);
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void removeDriver(int ID) throws Exception {
        try{
            employeeService.removeDriver(ID);
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public void addFakeEmployes() throws Exception
    {
        employeeService.addFakeEmployees();
    }
/////////////////////////////////////////////////////SCHEDULE//////////////////////////////////////////////////////////
    public HashMap<Pair<LocalDate,String>, WorkingSchedule> getRecord()
    {
        return scheduleService.getRecord();
    }

    public boolean addWorkingSchedule(WorkingSchedule ws) throws Exception {
        try {
            return scheduleService.addWorkingSchedule(ws);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validWorkingSchedule(WorkingSchedule ws) throws Exception { // check if the shift already exists
        try {
            return scheduleService.validWorkingSchedule(ws);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean shiftOccur(Pair<LocalDate,String> p)
    {
        return scheduleService.shiftOccur(p);
    }

    public Boolean getShift(LocalDate date,String kind) throws Exception {
        return scheduleService.getShift(date, kind);
    }

    public void deleteEmployeeFromShift(Employee e) throws Exception {
        scheduleService.deleteEmployeeFromShift(e);
    }

    public void addFakeShifts() throws Exception {
        scheduleService.addFakeShifts();
    }

    public DeliveryService getDeliveryService()
    {
        return this.deliveryService;
    }
    public EmployeeService getEmployeeService()
    {
        return this.employeeService;
    }
    public ScheduleService getScheduleService()
    {
        return this.scheduleService;
    }

    public  void init()
    {
        try
        {

            deliveryService.createLocation( "superli", "lachish 151 shoham", "0543160553", "yossi", "center");
            deliveryService.createLocation( "maxstock", "shoham 26 haifa", "0504616909", "ben", "north");
            deliveryService.createLocation( "shufersal", "azrieli tel aviv", "0543160550", "ronit", "center");
//            deliveryService.createLocation( "tara", "bialik 32 ramat gan", "0581234567", "moshe", "center");
//            deliveryService.createLocation( "tnuva", "rabin 12 beer sheva", "0538523644", "assaf", "south");
//            deliveryService.createLocation( "osem", "shimshon 24 krayot", "0528549847", "shoshana", "north");
            deliveryService.createTruck("2360154", "volvo", 1000.0, 4500.0);
            deliveryService.createTruck("30122623", "chevrolet", 5000.0, 9000.5);
            deliveryService.createTruck("11122333", "honda", 10000.0, 15000.0);
//            Map<String, Integer> items1 = new HashMap<String, Integer>() {
//                {
//                    put("milk", 20);
//                    put("pasta", 50);
//                    put("chocolate", 10);
//                    put("cola", 10);
//                }
//            };
//            Map<String, Integer> items2 = new HashMap<String, Integer>() {
//                {
//                    put("milk", 25);
//                    put("rice", 30);
//                    put("cheese", 40);
//                    put("eggs", 45);
//                }
//            };
//            Map<String, Integer> items3 = new HashMap<String, Integer>() {
//                {
//                    put("eggs", 10);
//                    put("cola zero", 15);
//                    put("beer", 23);
//                    put("candy", 17);
//                }
//            };
//            Map<String, Integer> items4 = new HashMap<String, Integer>() {
//                {
//                    put("eggs", 10);
//                    put("milk", 15);
//                    put("tomato", 23);
//                    put("cucumber", 17);
//                }
//            };
//            Map<String, Integer> items5 = new HashMap<String, Integer>() {
//                {
//                    put("ice cream", 20);
//                    put("toilet paper", 15);
//                    put("cucumber", 50);
//                    put("fish", 10);
//                }
//            };
//            deliveryService.createOrder(12, items1, "487", 1, 1000.0);
//            deliveryService.createOrder(34, items2, "159", 2, 3500.0);
//            deliveryService.createOrder(56, items3, "263", 3, 2500.0);
//            deliveryService.createOrder(78, items4, "546", 1, 2000.0);
//            deliveryService.createOrder(98, items5, "943", 3, 2000.0);
            Date newDate1 = new GregorianCalendar(2022, Calendar.MAY, 11).getTime();
            Date newDate2 = new GregorianCalendar(2020, Calendar.DECEMBER, 31).getTime();
            Date newDate3 = new GregorianCalendar(2020, Calendar.JULY, 7).getTime();
            Time newTime1 = Time.valueOf("12:30:00");
            Time newTime2 = Time.valueOf("13:00:00");
            Time newTime3 = Time.valueOf("11:25:30");
            List<Integer> centerLocations = new ArrayList<Integer>() {
                {
                    add(1);
                    add(3);
                }
            };
            List<Integer> northLocations = new ArrayList<Integer>() {
                {
                    add(2);
                }
            };
//            List<Integer> orders1 = new ArrayList<Integer>() {
//                {
//                    add(12);
//                    add(56);
//                }
//            };
//            List<Integer> orders2 = new ArrayList<Integer>() {
//                {
//                    add(34);
//                }
//            };
//            List<Integer> orders3 = new ArrayList<Integer>() {
//                {
//                    add(78);
//                    add(98);
//                }
//            };
            OrderService os = OrderService.getInstance();
            OrderDTO orders1 = os.getOrderDetails("1").getObj();
            OrderDTO orders2 = os.getOrderDetails("2").getObj();
            OrderDTO orders3 = os.getOrderDetails("3").getObj();
//            deliveryService.createDelivery(newDate1, newTime1, 208938985, 1, 1, "2360154", orders1);
//            deliveryService.createDelivery(newDate2, newTime2, 312164668, 2, 1, "30122623", orders2);
//            deliveryService.createDelivery(newDate3, newTime3, 123456789, 2, 2, "11122333", orders3);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}


