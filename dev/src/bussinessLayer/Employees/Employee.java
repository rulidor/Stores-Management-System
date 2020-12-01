package bussinessLayer.Employees;

import DataAccessLaye.Employees.DTO;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.LinkedList;

public class Employee {
    private String name;
    private Integer ID;
    private Integer bankAccount;
    private LocalDate startWorkingDate;
    private Integer salary;
    private LinkedList<Pair<String,String>> constrains; //which sift the employee can't work
    private Integer vacationDays;
    private LinkedList<String> roles;

    public Employee(String name, Integer ID, Integer bankAccount, LocalDate startWorkingDate,
                    Integer salary, Integer vacationDays, LinkedList<String> roles){
        this.name = name;
        this.ID = validInput(ID);//need to check it doesn already exist, in EmployeeController
        this.bankAccount = validInput(bankAccount);
        this.startWorkingDate = startWorkingDate;
        this.salary = validInput(salary);
        this.constrains = new LinkedList<>();
        this.vacationDays = validInput(vacationDays);
        this.roles = roles;
    }
    public Employee(String name, Integer ID, Integer bankAccount, LocalDate startWorkingDate,
                    Integer salary, Integer vacationDays, LinkedList<String> roles,LinkedList<Pair<String,String>> constrains) {
        this.name = name;
        this.ID = validInput(ID);//need to check it doesn already exist, in EmployeeController
        this.bankAccount = validInput(bankAccount);
        this.startWorkingDate = startWorkingDate;
        this.salary = validInput(salary);
        this.vacationDays = validInput(vacationDays);
        this.roles = roles;
        this.constrains=constrains;
    }
    private Integer validInput(Integer i){
        if (i<0){
            return 0;
        }
        return i;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getID(){
        return this.ID;
    }

    public Integer getBankAccount(){
        return this.bankAccount;
    }

    public void setBankAccount(Integer bankAccount){
        this.bankAccount = bankAccount;
    }

    public LocalDate getStartWorkingDate(){
        return this.startWorkingDate;
    }

    private void setStartWorkingDate(LocalDate startWorkingDate){
        this.startWorkingDate = startWorkingDate;
    }

    public Integer getSalary(){
        return this.salary;
    }

    public void setSalary(Integer salary){
        this.salary = validInput(salary);
    }

    public LinkedList<Pair<String,String>> getConstrains(){
        return this.constrains;
    }

    public void addConstraints(Pair<String,String> Constraint)
    {
        boolean exist = false;
        for(int i = 0; i<constrains.size(); i++)
        {
            if(constrains.get(i).equals(Constraint))
                exist = true;
        }
        if(!exist)
            constrains.add(Constraint);
    }

    public void removeConstraints(Integer index) throws Exception {
        constrains.remove(constrains.get(index));
        DataAccessLaye.Employees.Employee.deleteConstraint(this.ID,constrains.get(index).getKey(),constrains.get(index).getValue());
    }

    public Integer getVacationDays(){
        return this.vacationDays;
    }

    public void setVacationDays(Integer vactionDays){
        this.vacationDays = validInput(vactionDays);
    }

    public LinkedList<String> getRoles(){
        return this.roles;
    }

    public void setRoles(LinkedList<String> roles){
        this.roles = roles;
    }

    public void addRole(String role) throws Exception {
        /*boolean exist = false;
        for(int i = 0 ; i<roles.size(); i++)
        {
            if(roles.get(i).equals(role))
                exist = true;
        }
        if(!exist)
            roles.add(role);*/
        DataAccessLaye.Employees.Employee.insertEmployeeRoles(new DTO.EmployeeRoles(ID,role));

    }

    public void removeRole(Integer index) throws Exception {
        roles.remove(roles.get(index));
        DataAccessLaye.Employees.Employee.deleteRole(this.ID,roles.get(index));

    }

    public String toString()
    {
        String ret = "name: " + name + "  ID:" + ID + "\nroles: " + roles.toString() +
                "\nsalary: "+salary+"\nbank account: "+bankAccount+
                "\nvacation days: "+vacationDays+
                "\nconstraints: " + constrains.toString()+"\n";
        return ret;
    }

    //private void showHowCanWork(Business_Layer.Employee e){

    //}
}

