package DataAccessLaye.Employees;

import DataAccessLaye.Repo;
import javafx.util.Pair;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class WorkingSchedule {

    public static void insertWorkingSchedule(DTO.Shifts s) throws Exception {
        try   {
            String query = "INSERT OR IGNORE INTO Shifts VALUES (?, ?, ?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setDate(1,  Date.valueOf(s.Date));
            stmt.setString(2, s.Kind);
            stmt.setBoolean(3, s.ShiftManager);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void insertEmployeesShifts(DTO.EmployeesShifts es) throws Exception {
        try   {
            String query = "INSERT OR IGNORE INTO EmployeeShifts VALUES (?, ?, ?,?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setDate(1,  Date.valueOf(es.Date));
            stmt.setString(2, es.Kind);
            stmt.setInt(3, es.ID);
            stmt.setString(4, es.Role);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean validShift(LocalDate date, String kind) throws Exception {
        try   {
            String sql = "SELECT * From Shifts WHERE Date=? AND Kind=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(date));
            pst.setString(2,kind);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return true;
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    public static HashMap<Pair<Date,String>,LinkedList<Pair<Integer,String>>> displayShifts() throws Exception {
        try   {
            String sql = "SELECT * From EmployeesShifts ";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            HashMap<Pair<Date,String>,LinkedList<Pair<Integer,String>>> map = new HashMap<>();
            while (results.next()) {
                Date date = results.getDate(1);
                String kind = results.getString(2);
                int id=results.getInt(3);
                String role=results.getString(4);
                boolean toContinue = true;
                for (Pair<Date,String> p : map.keySet()) {
                    if(p.getKey().compareTo(date) == 0 && p.getValue().equals(kind)) {
                        map.get(p).add(new Pair<>(id,role));
                        toContinue = false;
                    }
                }
                if(toContinue) {
                    LinkedList<Pair<Integer,String>> list = new LinkedList<>();
                    list.add(new Pair<>(id,role));
                    map.put(new Pair<>(date,kind),list);
                }
            }
            return map;
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean ShiftContainsEmployee(LocalDate date, String kind, Integer id) throws Exception {
        try   {
            String sql = "SELECT ID From EmployeesShifts WHERE Date=? AND Kind=? AND ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(date));
            pst.setString(2,kind);
            pst.setInt(3,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public static boolean CheckShift(LocalDate date, String kind) throws Exception {
        try   {
            String sql = "SELECT * From Shifts WHERE Date=? AND Kind=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(date));
            pst.setString(2,kind);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public static void CheckShiftManagerInShift() throws Exception {
        try {
            String sql = "SELECT Date, Kind From Shifts";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            while (results.next() == true) {
                HelpCheckShiftManagerInShift(results.getDate(1), results.getString(2));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void HelpCheckShiftManagerInShift(Date date, String kind) throws Exception {
        try {
            String sql1 = "SELECT Role From EmployeesShifts WHERE Date=? AND Kind=?";
            PreparedStatement pst1 = Repo.con.prepareStatement(sql1);
            pst1.setDate(1, date);
            pst1.setString(2, kind);
            ResultSet results1 = pst1.executeQuery();
            boolean noShiftManager = false;
            while (results1.next() == true) {
                if (results1.getString(1).equals("Shift_Manager"))
                    noShiftManager = true;
            }
            if (!noShiftManager) {
                removeShift(date.toLocalDate(), kind);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean EmployeeCanWork(String day, String kind, Integer id) throws Exception {
        try   {
            String sql = "SELECT * From EmployeesConstraints WHERE DayConstraint=? AND KindConstraint=? AND ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1, day);
            pst.setString(2,kind);
            pst.setInt(3,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return true;
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    public static void removeShift(LocalDate date, String kind) throws Exception {
        try   {
            String sql = "DELETE From Shifts WHERE Date=? AND Kind=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(date));
            pst.setString(2,kind);

            int results = pst.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean CheckForDriver(int id,LocalDate date,String kind) throws Exception {
        try   {
            String sql = "SELECT * From EmployeesShifts WHERE Date=? AND Kind=? AND ID=? AND Role=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(date));
            pst.setString(2,kind);
            pst.setInt(3,id);
            pst.setString(4,"Driver");

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return false;
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkStoreKeper(LocalDate date,String kind) throws Exception {
        try   {
            String sql = "SELECT * From EmployeesShifts WHERE Date=? AND Kind=?  AND Role=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(date));
            pst.setString(2,kind);
            pst.setString(3,"Store_Keeper");

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return false;
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void deleteEmployeeFromShifts(int id) throws Exception {
        try   {
            String sql = "DELETE * From EmployeesShifts WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet results = pst.executeQuery();
        } catch (Exception e) {
            throw e;
        }
    }


}
