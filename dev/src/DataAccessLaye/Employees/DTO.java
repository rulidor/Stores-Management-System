package DataAccessLaye.Employees;

import java.time.LocalDate;

public class DTO {

    public static class Employees{
        protected Integer ID;
        protected String Name;
        protected Integer bankAccount;
        protected LocalDate startWorkingDate;
        protected Integer salary;
        protected Integer vacationDays;

        public Employees(Integer id, String name, Integer bankAccount, LocalDate startWorkingDate, Integer salary, Integer vacationDays) {
            this.ID = id;
            this.Name = name;
            this.bankAccount = bankAccount;
            this.startWorkingDate = startWorkingDate;
            this.salary = salary;
            this.vacationDays = vacationDays;
        }
    }

    public static class EmployeeRoles{
        protected Integer ID;
        protected String Role;

        public EmployeeRoles(Integer id, String role) {
            this.ID = id;
            this.Role = role;
        }
    }

    public static class EmployeeConstraints{
        protected Integer ID;
        protected String DayConstraint;
        protected String KindConstraint;

        public EmployeeConstraints(Integer id, String day, String kind) {
            this.ID = id;
            this.DayConstraint = day;
            this.KindConstraint = kind;
        }
    }

    public static class Shifts{
        protected LocalDate Date;
        protected String Kind;
        protected boolean ShiftManager;

        public Shifts(LocalDate date, String kind, boolean shiftManager) {
            this.Date = date;
            this.Kind = kind;
            this.ShiftManager = shiftManager;
        }
    }

    public static class EmployeesShifts{
        protected LocalDate Date;
        protected String Kind;
        protected Integer ID;
        protected String Role;

        public EmployeesShifts(LocalDate date, String kind, Integer id, String role) {
            this.Date = date;
            this.Kind = kind;
            this.ID = id;
            this.Role = role;
        }
    }
}
