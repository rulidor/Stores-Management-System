package ServiceLayer;

import bussinessLayer.Employees.Employee;
import bussinessLayer.Employees.EmployeeController;
import bussinessLayer.Transports.DriverPackage.Driver;
import javafx.util.Pair;

import java.util.Date;
import java.util.LinkedList;


public class EmployeeService {

    private EmployeeController employeeController;

    public EmployeeService()
    {
        employeeController = employeeController.getInstance();
    }

    public boolean registerEmployee(Employee e) throws Exception { return employeeController.registerEmployee(e); }

    public boolean registerDriver(Driver d) throws Exception { return employeeController.registerDriver(d); }

    public Employee createEmployee(String name, Integer ID, Integer bankAccount,
                                   Integer salary, Integer vacationDays, LinkedList<String> roles) throws Exception {
        return employeeController.createEmployee(name, ID, bankAccount, salary, vacationDays, roles);
    }

    public Driver createDriver(String name, Integer ID, Integer bankAccount, Integer salary, Integer vacationDays,
                               LinkedList<String> roles, String licenseType, Date expLicenseDate) throws Exception
    {
        Driver d= employeeController.createDriver(name, ID, bankAccount, salary, vacationDays, roles, licenseType, expLicenseDate);
        return d;
    }

    public void addRole(Integer ID, String role) throws Exception { employeeController.addRole(ID, role); }

    public void setEmployeeName(Integer ID, String name) throws Exception {
        employeeController.setEmployeeName(ID, name);
    }

    public void setBankAccount(Integer ID, Integer bankAccount) throws Exception { employeeController.setBankAccount(ID, bankAccount); }

    public void setSalary(Integer ID, Integer salary) throws Exception {
        employeeController.setSalary(ID, salary);
    }

    public void setVacationDays(Integer ID, Integer vacationDays) throws Exception { employeeController.setVacationDays(ID, vacationDays); }

    public void addConstraints(Integer ID, Pair<String,String> p) throws Exception { employeeController.addConstraints(ID, p); }

    public void deleteEmployee(Integer ID) throws Exception {
        try
        {
            employeeController.deleteEmployee(ID);
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
            employeeController.changeExpDate(id, expLicenseDate);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeLicenseType(Integer id, String licenseType) throws Exception {
        try
        {
            employeeController.changeLicenseType(id, licenseType);
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public void setDriverToDrive(Integer id) throws Exception {
        try
        {
            employeeController.setDriverToDrive(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void setDriverNotToDrive(Integer id) throws Exception {
        try
        {
            employeeController.setDriverNotToDrive(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean checkLicence(int id, Date date) throws Exception {
        try
        {
            return employeeController.checkLicense(id, date);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public String getDriversLicesnesType(int id) throws Exception {
        try {
            return employeeController.getDriversLicesnesType(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setDriversStatus(int id,String status) throws Exception {
        try{
            if(status.compareTo("InTransit") != 0 && status.compareTo("Delivered") != 0)
                throw new Exception("status can be changed only to InTransit or Delivered");
            if(status.compareTo("InTransit")==0)
                employeeController.setDriverToDrive(id);
            if(status.compareTo("Delivered")==0)
                employeeController.setDriverNotToDrive(id);

        }catch (Exception e)
        {
            throw e;
        }

    }

    public EmployeeController getEmployeeController()
    {
        return this.employeeController;
    }

    public void addRole(Integer ID, String role,String licensType,Date date) throws Exception {
       try
       {
           employeeController.addRole(ID,role,licensType,date);
       }catch (Exception e)
       {
           throw e;
       }
    }

    public void removeDriver(int ID) throws Exception {
        try{
            employeeController.removeDriver(ID);
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public void addFakeEmployees() throws Exception
    {
        employeeController.addFakeEmployees();
    }

}
