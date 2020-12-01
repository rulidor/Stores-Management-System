package DataAccessLaye.Employees;

import DataAccessLaye.Repo;
import DataAccessLaye.Transports.Driver;
import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Employee {


    public static void insertEmployee(DTO.Employees emp) throws Exception {
        try  {
            String query = "INSERT OR IGNORE INTO Employees VALUES (?, ?, ? ,?,?,?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setInt(1, emp.ID);
            stmt.setString(2, emp.Name);
            stmt.setInt(3, emp.bankAccount);
            stmt.setDate(4, Date.valueOf(emp.startWorkingDate));
            stmt.setInt(5, emp.salary);
            stmt.setInt(6, emp.vacationDays);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

   /* public static void insertEmployeeConstraint(DTO.EmployeeConstraints ec)
    {
        try   {
            String query = "INSERT OR IGNORE INTO Shifts VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ec.DayConstraint);
            stmt.setString(2, ec.KindConstraint);
            stmt.setInt(3, ec.ID);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }*/

    public static void insertEmployeeRoles(DTO.EmployeeRoles er) throws Exception {
        try  {
            String query = "INSERT OR IGNORE INTO EmployeesRoles VALUES (?, ?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setInt(1,  er.ID);
            stmt.setString(2, er.Role);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean validID(int id) throws Exception {
        try  {
            String sql = "SELECT * From Employees WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return true;
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    public static void updateName(int id, String name) throws Exception {
        try {
            String sql = "UPDATE Employees SET Name =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setString(1,name);
            pst.setInt(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateBankAccount(int id, int bankAccount) throws Exception {
        try {
            String sql = "UPDATE Employees SET Bank_Account =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,bankAccount);
            pst.setInt(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateSalary(int id, int salary) throws Exception {
        try  {
            String sql = "UPDATE Employees SET Salary =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,salary);
            pst.setInt(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void removeConstraint(DTO.EmployeeConstraints d) throws Exception {
        try  {
            String sql = "DELETE FROM EmployeesConstraints WHERE ID=? AND DayConstraint=? AND KindConstraint=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,d.ID);
            pst.setString(2,d.DayConstraint);
            pst.setString(3,d.KindConstraint);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static List<Pair<String,String>> getConstraint(int id) throws Exception {
        List<Pair<String,String>> Constraints = new ArrayList<>();
        try  {
            String sql = "SELECT * From EmployeesConstraints WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                Constraints.add(new Pair<>(results.getString(2),results.getString(3)));
            }
        } catch (Exception e) {
            throw e;
        }
        return Constraints;
    }

    public static void updateVacationDays(int id, int vacationDays) throws Exception {
        try  {
            String sql = "UPDATE Employees SET Vacation_Days =? WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,vacationDays);
            pst.setInt(2,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
                    }
    }

    public static void insertEmployeeConstraint(DTO.EmployeeConstraints er) throws Exception {
        try  {
            String query = "INSERT OR IGNORE INTO EmployeesConstraints VALUES (?, ?,?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setInt(1,  er.ID);
            stmt.setString(2, er.DayConstraint);
            stmt.setString(3, er.KindConstraint);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean CheckConstraint(DTO.EmployeeConstraints er) throws Exception {
        try   {
            String sql = "SELECT Date From EmployeesShifts WHERE ID=? AND Kind=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,er.ID);
            pst.setString(2,er.KindConstraint);

            ResultSet results = pst.executeQuery();
            LinkedList<Date> dates = new LinkedList<>();
            while(results.next()==true)
                dates.add(results.getDate(1));
            for (int i = 0; i<dates.size(); i++)
            {
                if(dates.get(i).toLocalDate().getDayOfWeek().toString() == er.DayConstraint)
                    return false;
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void deleteEmployee(int id) throws Exception {
        try   {
            String sql = "DELETE FROM Employees WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);
            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public static void deleteConstraint(int id, String day, String kind) throws Exception {
        try   {
            String sql = "DELETE FROM EmployeesConstraints WHERE ID=? AND DayConstraint=? AND KindConstraint=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);
            pst.setString(2,day);
            pst.setString(3,kind);
            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public static void deleteRole(int id, String role) throws Exception {
        try   {
            String sql = "DELETE FROM EmployeesRoles WHERE ID=? AND Role=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);
            pst.setString(2,role);
            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public static void ChangeEmployeeRole(Integer id, String role, Date date, String kind) throws Exception {
        try   {
            String query = "UPDATE EmployeesShifts SET Role =? WHERE Date=? AND Kind=? AND ID=?";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setString(1,  role);
            stmt.setDate(2,  date);
            stmt.setString(3, kind);
            stmt.setInt(4, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean CheckRole(int id, String role) throws Exception {
        try   {
            String sql = "SELECT Role From EmployeesShifts WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            LinkedList<String> roles = new LinkedList<>();
            while(results.next()==true)
                roles.add(results.getString(1));
            for (int i = 0; i<roles.size(); i++)
            {
                if(roles.get(i).equals(role))
                    return false;
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void removeEmployeeFromShift(Integer id, Date date, String kind) throws Exception {
        try   {
            String query = "DELETE From EmployeesShifts WHERE Date=? AND Kind=? AND ID=?";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setDate(1,  date);
            stmt.setString(2, kind);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            //check if we have another shift manager
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean CheckShiftManager(int id) throws Exception {
        try   {
            String sql = "SELECT Role From EmployeesRoles WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            LinkedList<String> roles = new LinkedList<>();
            while(results.next()==true)
                roles.add(results.getString(1));
            for (int i = 0; i<roles.size(); i++)
            {
                if(roles.get(i).equals("Shift_Manager"))
                    return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean CheckShiftManagerInShift(Date date, String kind) throws Exception {
        try   {
            String sql = "SELECT Role From EmployeesShifts WHERE Date=? AND Kind=? ";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setDate(1,date);
            pst.setString(2,kind);

            ResultSet results = pst.executeQuery();
            //LinkedList<String> roles = new LinkedList<>();
            while(results.next()==true) {
                if (results.getString(1).equals("Shift_Manager")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void AddEmployeeToShift(Integer id, String role, Date date, String kind) throws Exception {
        try   {
            String query = "INSERT OR IGNORE INTO EmployeesShifts VALUES (?, ?,?,?)";
            PreparedStatement stmt = Repo.con.prepareStatement(query);
            stmt.setDate(1,  date);
            stmt.setString(2, kind);
            stmt.setInt(3, id);
            stmt.setString(4, role);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public static bussinessLayer.Employees.Employee checkEmployee(int id) throws Exception {
        try   {
            String sql = "SELECT * From Employees WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            LinkedList<String> roles=getRoles(id);
            LinkedList<Pair<String,String>> consts=getEmployeeConst(id);

            return new bussinessLayer.Employees.Employee(results.getString(2),results.getInt(1),results.getInt(3),results.getDate(4).toLocalDate(),
                    results.getInt(5),results.getInt(6),roles,consts);
        } catch (Exception e) {
            throw e;
        }
    }

    public static LinkedList<String> getRoles(int id) throws Exception {
        LinkedList<String> roles = new LinkedList<>();
        try   {
            String sql = "SELECT * From EmployeesRoles WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                roles.add(results.getString(2));
            }
        } catch (Exception e) {
            throw e;
        }
        return roles;
    }

    public static LinkedList<Pair<String,String>> getEmployeeConst(int id) throws Exception {
        LinkedList<Pair<String,String>> constraints=new LinkedList<>();
        try   {
            String sql = "SELECT * From Employees WHERE ID=?";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet results = pst.executeQuery();

            while (results.next()) {
                constraints.add(new Pair<>(results.getString(2),results.getString(3)));
            }
        } catch (Exception e) {
            throw e;
        }
        return constraints;
    }

    public static void printEmps() throws Exception {
        try   {
            String sql = "SELECT * From Employees ";
            PreparedStatement pst = Repo.con.prepareStatement(sql);

            ResultSet results = pst.executeQuery();
            while (results.next()) {
                int  id = results.getInt(1);
                String name = results.getString(2);
                int bankAcc=results.getInt(3);
                Date statingDate=results.getDate(4);
                int salary=results.getInt(5);
                int VacDays=results.getInt(6);
                System.out.println("name: "+name+" ID: "+id);
                printEmpRoles(id);
                System.out.println("salary: "+salary+"\nbank account: "+bankAcc+"\nvacation days: "+VacDays);
                printEmpConst(id);
                Driver.printDrivers(id);
                System.out.println();
            }


        } catch (Exception e) {
            throw e;
        }
    }

    public static void printEmpConst(int id1) throws Exception {
        try   {
            String sql = "SELECT * From EmployeesConstraints WHERE ID=? ";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id1);
            ResultSet results = pst.executeQuery();
            System.out.print("constraints: [ ");
            while (results.next()) {
                String DayConstraint = results.getString(2);
                String KindConstraint=results.getString(3);
                System.out.print(DayConstraint+"="+KindConstraint+" ");
            }
            System.out.println("]");


        } catch (Exception e) {
            throw e;
        }

    }

    public static void printEmpRoles(int id1) throws Exception {
        try   {
            String sql = "SELECT * From EmployeesRoles WHERE ID=? ";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            pst.setInt(1,id1);
            ResultSet results = pst.executeQuery();
            System.out.print("roles: [ ");
            while (results.next()) {
                String role = results.getString(2);
                System.out.print(role+" ");
            }
            System.out.println("]");


        } catch (Exception e) {
            throw e;
        }

    }

    public static HashMap<Pair<Integer,String>,LinkedList<String>> displayEmployees() throws Exception {
        try   {
            String sql = "SELECT DISTINCT Employees.ID,Employees.Name,EmployeesRoles.Role From Employees JOIN EmployeesRoles WHERE Employees.ID=EmployeesRoles.ID";
            PreparedStatement pst = Repo.con.prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            HashMap<Pair<Integer,String>,LinkedList<String>> map = new HashMap<>();
            while (results.next()) {
                int id=results.getInt(1);
                String Name = results.getString(2);
                String role=results.getString(3);
                boolean toContinue = true;
                for (Pair<Integer,String> p : map.keySet()) {
                    if(p.getKey() == id && p.getValue().equals(Name)) {
                        map.get(p).add(role);
                        toContinue = false;
                    }
                }
                if(toContinue) {
                    LinkedList<String> list = new LinkedList<>();
                    list.add(role);
                    map.put(new Pair<>(id,Name),list);
                }
            }
            return map;
        } catch (Exception e) {
            throw e;
        }
    }
}
