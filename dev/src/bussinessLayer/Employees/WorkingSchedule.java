package bussinessLayer.Employees;

import javafx.util.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;

public class WorkingSchedule {

    private LocalDate date;
    private String kind;
    private boolean shiftManager;
    private LinkedList<Pair<String, Employee>> employeeList;//<role, employee>

    public LocalDate getDate()
    {
        return this.date;
    }

    public String getKind()
    {
        return this.kind;
    }

    public boolean getShiftManager()
    {
        return this.shiftManager;
    }

    public LinkedList<Pair<String, Employee>> getEmployees()
    {
        return this.employeeList;
    }

    public WorkingSchedule(LocalDate date, String kind){
        this.date = date;
        shiftManager = false;
        this.kind = kind;
        this.employeeList = new LinkedList<>();
    }

    public boolean removeEmployeeFromShift(Employee e){
        for(int i = 0; i<employeeList.size();i++){
            if (employeeList.get(i).getValue().equals(e)){
                employeeList.remove(employeeList.get(i));
                updateShiftManager();
                return true;
            }
        }
        return false;
    }

    public boolean addWorkersToShift(String role, Employee e) throws Exception {
        if(!shiftContainsEmployee(e)&&employeeCanWork(e)) {
            employeeList.add(new Pair<>(role, e));
            updateShiftManager();
            DataAccessLaye.Employees.Employee.AddEmployeeToShift(e.getID(), role, Date.valueOf(date),this.kind);
            return true;
        }
        return false;
    }

    private boolean employeeCanWork(Employee e) throws Exception {
        //the employee constraines does not match the shift date or Business_Layer.kind
        /*for (int i = 0; i<e.getConstrains().size();i++){
            if (e.getConstrains().get(i).getKey().equals(date.getDayOfWeek().toString())&&
                    e.getConstrains().get(i).getValue().equals(kind))
                return false;
        }*/
        return DataAccessLaye.Employees.WorkingSchedule.EmployeeCanWork(this.date.getDayOfWeek().toString(),this.kind,e.getID());
    }

    private boolean shiftContainsEmployee(Employee e) throws Exception {
       /* for (int i = 0; i<employeeList.size(); i++)
        {
            if(employeeList.get(i).getValue() == e)
            {
                return true;
            }
        }*/
        return DataAccessLaye.Employees.WorkingSchedule.ShiftContainsEmployee(this.date,this.kind,e.getID());
    }

    private void updateShiftManager()
    {
        shiftManager = false;
        for (int i = 0; i<employeeList.size(); i++)
        {
            if(employeeList.get(i).getKey().equals("Shift_Manager"))
            {
                shiftManager = true;
                break;
            }
        }
    }

    public boolean changeEmployeeRole(Employee e, String role) throws Exception {
        if (shiftContainsEmployee(e)) {
            boolean found = false;
            for (int i = 0; i<employeeList.size() & !found; i++) {
                if(employeeList.get(i).getValue() == e)
                    employeeList.remove(i);
                found = true;
            }
            employeeList.add(new Pair<>(role, e));
            updateShiftManager();
            return true;
        }
        return false;
    }

    /*public boolean checkEmployee(int id)
    {

        for (int i = 0; i<employeeList.size(); i++)
        {
            if(employeeList.get(i).getValue().getID()==id && employeeList.get(i).getKey().compareTo("Driver")==0)
                return true;
        }
        return false;
    }*/

    public boolean checkforStoreKeeper()
    {
        for (int i = 0; i<employeeList.size(); i++)
        {
            if( employeeList.get(i).getKey().compareTo("Store_Keeper")==0)
                return true;
        }
        return false;
    }


    public String toString()
    {
        String toAdd = "";
        for(int i = 0; i<employeeList.size(); i++)
        {
            toAdd += i+1 + ")name: "  + employeeList.get(i).getValue().getName() + ", ID: " + employeeList.get(i).getValue().getID().toString()
                    + ", Role: " + employeeList.get(i).getKey() + "\n";
        }
        String ret = "date: " + date.toString() + ", shift: " + kind + "\n" + "employee list in shift: \n"+ toAdd + "\n";
        return ret;
    }

}

