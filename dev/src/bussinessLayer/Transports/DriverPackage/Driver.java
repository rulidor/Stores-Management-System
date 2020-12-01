package bussinessLayer.Transports.DriverPackage;

import bussinessLayer.Employees.Employee;
import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Driver extends Employee {

    private String licenseType;
    private Date expLicenseDate;
    private boolean isDriving;

    public Driver(String name, Integer ID, Integer bankAccount, LocalDate startWorkingDate,
                  Integer salary, Integer vacationDays, LinkedList<String> roles, String licenseType, Date expLicenseDate) {
        super(name, ID, bankAccount, startWorkingDate, salary, vacationDays, roles);
        this.licenseType = licenseType;
        this.expLicenseDate = expLicenseDate;
        this.isDriving = false;
    }
    public Driver(String name, Integer ID, Integer bankAccount, LocalDate startWorkingDate,
                  Integer salary, Integer vacationDays, LinkedList<String> roles, LinkedList<Pair<String,String>> constrains, String licenseType, Date expLicenseDate,boolean isDriving) {
        super(name, ID, bankAccount, startWorkingDate, salary, vacationDays, roles,constrains);
        this.licenseType = licenseType;
        this.expLicenseDate = expLicenseDate;
        this.isDriving = isDriving;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Date getExpLicenseDate() {
        return expLicenseDate;
    }

    public void setExpLicenseDate(Date expLicenseDate) {
        this.expLicenseDate = expLicenseDate;
    }

    public boolean isDriving() {
        return isDriving;
    }

    public void setDriving() {
        isDriving = true;
    }

    public void setNotDriving() {
        isDriving = false;
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String ret = "name: " + this.getName() + "  ID:" + this.getID() + "\nroles: " + this.getRoles().toString() +
                "\nsalary: "+this.getSalary()+"\nbank account: "+this.getBankAccount()+
                "\nvacation days: "+this.getVacationDays()+
                "\nconstraints: " + this.getConstrains().toString()+
                "\nlicense type: "+licenseType+"\nlicenses expiration date: "+dateFormat.format(expLicenseDate)+"\n";
        return ret;
    }
}
