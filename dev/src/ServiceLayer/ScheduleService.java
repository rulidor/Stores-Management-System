package ServiceLayer;

import bussinessLayer.Employees.Employee;
import bussinessLayer.Employees.ScheduleHistory;
import bussinessLayer.Employees.WorkingSchedule;
import javafx.util.Pair;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class ScheduleService {
    //decided morning shift is from 6-14 , evening shift 14-22
    private ScheduleHistory scheduleController;

    public ScheduleService() { this.scheduleController = ScheduleHistory.getInstance(); }

    public ScheduleHistory getScheduleController() {
        return scheduleController;
    }

    public HashMap<Pair<LocalDate,String>, WorkingSchedule> getRecord()
    {
        return scheduleController.getRecord();
    }

    public boolean addWorkingSchedule(WorkingSchedule ws) throws Exception {
        return scheduleController.addWorkingSchedule(ws);
    }

    public boolean validWorkingSchedule(WorkingSchedule ws) throws Exception { // check if the shift already exists
        try {
            return scheduleController.validWorkingSchedule(ws);
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean shiftOccur(Pair<LocalDate,String> p)
    {
        return scheduleController.shiftOccur(p);
    }

    public Boolean getShift(LocalDate date,String kind) throws Exception {
        return scheduleController.getShift(date, kind);
    }

    public void deleteEmployeeFromShift(Employee e) throws Exception {
        scheduleController.deleteEmployeeFromShift(e.getID());
    }

    public void addFakeShifts() throws Exception {
        try {
            scheduleController.addFakeShifts();
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean checkForDriver(int id, String shift,LocalDate date) throws Exception {return scheduleController.checkForDriver(id,shift,date);}

    public boolean checkStoreKeeper( String shift,LocalDate date) throws Exception {return scheduleController.checkforStoreKeeper(shift,date);}
}
