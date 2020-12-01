package bussinessLayer.Employees;

import javafx.util.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ScheduleHistory {
    private HashMap<Pair<LocalDate,String>,WorkingSchedule> record;//string is the Business_Layer.kind of shift, ex: morning
    private static ScheduleHistory scheduleController = null;

    private ScheduleHistory(){
        record = new HashMap<>();
    }

    public static ScheduleHistory getInstance()
    {
        if(scheduleController == null)
            scheduleController = new ScheduleHistory();
        return scheduleController;
    }

    public HashMap<Pair<LocalDate,String>,WorkingSchedule> getRecord()
    {
        return record;
    }

    public boolean addWorkingSchedule(WorkingSchedule ws) throws Exception {
        if (validWorkingSchedule(ws)) {
            //record.put(new Pair<>(ws.getDate(), ws.getKind()), ws);
            DataAccessLaye.Employees.WorkingSchedule.insertWorkingSchedule(new DataAccessLaye.Employees.DTO.Shifts(ws.getDate(),ws.getKind(),ws.getShiftManager()));
            return true;
        }
        return false;
    }

    public boolean addWorkersToShift(String role, int id, LocalDate date, String Kind) throws Exception {
        if(!shiftContainsEmployee(date,Kind,id)&&employeeCanWork(date,Kind,id)) {
            DataAccessLaye.Employees.Employee.AddEmployeeToShift(id, role, Date.valueOf(date),Kind);
            return true;
        }
        return false;
    }

    private boolean shiftContainsEmployee(LocalDate date,String kind, int id) throws Exception {
        return DataAccessLaye.Employees.WorkingSchedule.ShiftContainsEmployee(date,kind,id);
    }

    private boolean employeeCanWork(LocalDate date,String kind, int id) throws Exception {
        return DataAccessLaye.Employees.WorkingSchedule.EmployeeCanWork(date.getDayOfWeek().toString(),kind,id);
    }

    public boolean validWorkingSchedule(WorkingSchedule ws) throws Exception { // check if the shift already exists
       /* for (Pair<LocalDate,String> p : record.keySet())
        {
            if(p.getKey().equals(ws.getDate()) && p.getValue().equals(ws.getKind()))
                return false;
        }*/
        return DataAccessLaye.Employees.WorkingSchedule.validShift(ws.getDate(),ws.getKind());
    }

    public boolean removeEmployeeFromShift(int id, LocalDate date, String Kind) throws Exception {
        if(shiftContainsEmployee(date,Kind,id)) {
            DataAccessLaye.Employees.Employee.removeEmployeeFromShift(id, Date.valueOf(date),Kind);
            if(!DataAccessLaye.Employees.Employee.CheckShiftManagerInShift(Date.valueOf(date),Kind)){
                removeShift(date, Kind);
            }
            return true;
        }
        return false;
    }

    public boolean ChangeEmployeeRole(int id, String role, LocalDate date, String Kind) throws Exception {
        if(shiftContainsEmployee(date,Kind,id)) {
            DataAccessLaye.Employees.Employee.ChangeEmployeeRole(id, role, Date.valueOf(date),Kind);
            if(!DataAccessLaye.Employees.Employee.CheckShiftManagerInShift(Date.valueOf(date),Kind)){
                removeShift(date, Kind);
            }
            return true;
        }
        return false;
    }

    public boolean shiftOccur(Pair<LocalDate,String> p)
    {
        for (Pair<LocalDate,String> p1 : record.keySet())
        {
            if(p1.getKey().equals(p.getKey()) && p1.getValue().equals(p.getValue()))
                return (record.get(p1).getShiftManager() && record.get(p1).getDate().compareTo(LocalDate.now()) <= 0);
        }
        return false;
    }

    public boolean getShift(LocalDate date,String kind) throws Exception {
        return DataAccessLaye.Employees.WorkingSchedule.CheckShift(date,kind);
    }

    public void deleteEmployeeFromShift(int id) throws Exception {
        DataAccessLaye.Employees.WorkingSchedule.deleteEmployeeFromShifts(id);
    }

    public void addFakeShifts() throws Exception {
        WorkingSchedule w = new WorkingSchedule(LocalDate.of(2022,5,27),"Evening");
        addWorkingSchedule(w);
        WorkingSchedule w1 = new WorkingSchedule(LocalDate.of(2022,6,7),"Morning");
        addWorkingSchedule(w1);
        WorkingSchedule w2 = new WorkingSchedule(LocalDate.of(2022,6,12),"Evening");
        addWorkingSchedule(w2);
        WorkingSchedule w3 = new WorkingSchedule(LocalDate.of(2022,5,22),"Morning");
        addWorkingSchedule(w3);
        WorkingSchedule w4 = new WorkingSchedule(LocalDate.of(2022,5,15),"Evening");
        addWorkingSchedule(w4);
    }

    public String toString(){
        HashMap<Pair<Date, String>, LinkedList<Pair<Integer, String>>> h = null;
        try {
            h = DataAccessLaye.Employees.WorkingSchedule.displayShifts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ret = "";
        for(Map.Entry<Pair<Date, String>, LinkedList<Pair<Integer, String>>> entry : h.entrySet())
        {
            String date =  entry.getKey().getKey().toString();
            String kind = entry.getKey().getValue();
            ret += date + ": " + kind + "\n" + entry.getValue().toString() + "\n";
        }
        return ret;
    }

    public void removeShift(LocalDate date, String kind) throws Exception {
        DataAccessLaye.Employees.WorkingSchedule.removeShift(date,kind);
    }

    public boolean checkForDriver(int id, String shift,LocalDate date) throws Exception {
        try {
            if(!DataAccessLaye.Employees.WorkingSchedule.CheckForDriver(id,date,shift))
                return false;
            return true;
        }
       catch (Exception e)
       {
           throw e;
       }
    }

    public boolean checkforStoreKeeper(String shift, LocalDate date) throws Exception {
       try{
           if(!DataAccessLaye.Employees.WorkingSchedule.checkStoreKeper(date,shift))
               return false;
           return true;
       }catch (Exception e)
       {
           throw e;
       }
    }

    public boolean addWorkingSchedule(LocalDate date, String kind) throws Exception {
        WorkingSchedule ws = new WorkingSchedule(date,kind);
        if (validWorkingSchedule(ws)) {
            record.put(new Pair<>(date, kind), ws);
            DataAccessLaye.Employees.WorkingSchedule.insertWorkingSchedule(new DataAccessLaye.Employees.DTO.Shifts(ws.getDate(),ws.getKind(),ws.getShiftManager()));
            return true;
        }
        return false;
    }


}
