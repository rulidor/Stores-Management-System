package PresentationLayer;

import ServiceLayer.Service;
import javafx.util.Pair;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
    enum EmployeesMenuOptions {register_Employee, Edit_Employee_Details, Display_Employee, Add_Shift, Edit_Employee_In_Shift,
        Display_Shift,Delete_Employee, Delete_Shift, exit}
    enum DeliveriesMenuOptions {trucks_menu, locations_menu, deliveries_menu, exit}
    enum roleOptions {Cashier, Shift_Manager, Driver, Store_Keeper}
    enum employeeDetails {changeName, changeBankAccount, changeSalary, changeVacationDays, addConstraints, removeConstraint, addRole, removeRole,changeLicenseDate,changeLicenseType, exit}
    enum shift {Morning, Evening}
    enum days {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}
    enum actionInShift {addEmployee, RemoveEmployee, ChangEmployeeRole, Exit}
    private static Service service = Service.getInstance();

    public static void start() {
        //boolean isData=Repo.openDatabase();
        Scanner in = new Scanner(System.in);

        try
        {
            System.out.println("welcome to the system manager");
            System.out.println("please choose how to initialize the system");
            System.out.println("1 for empty DATABASE,2 for initialized DATABASE,3 for previous DATABASE");
            int choice = in.nextInt();
            while (choice<1 && choice>3)
            {
                System.out.println("choice out of range. please enter again");
                System.out.println("1 for empty DATABASE,2 for initialized DATABASE,3 for previous DATABASE");
                choice=in.nextInt();
            }

            while(true)
            {
                System.out.println("please enter the menu number you wish to enter");
                System.out.println("main menu:\n1) Employees menu\n2) Deliveries menu\n3) exit system");
                choice = in.nextInt();
                mainMenu(choice);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void mainMenu(int choice)
    {
        switch (choice)
        {
            case 1:
                try {
                    EmployeesMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                DeliveriesMenu();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("choice out of bounds");
                break;
        }
    }

    public static void EmployeesMenu() throws Exception {
        try {
            Scanner myScanner = new Scanner(System.in);
            boolean returnToMain=false;
            while(true) {
                System.out.println("choose an option: ");
                for (int i = 0; i < EmployeesMenuOptions.values().length; i++) {
                    System.out.println(i + 1 + ") " + EmployeesMenuOptions.values()[i].toString());
                }
                int userInput = myScanner.nextInt();
                switch (userInput) {
                    case 1:
                        if (!Register_Employee()) {
                            System.out.println("registration was not completed");
                        }
                        break;
                    case 2:
                        editEmployee();
                        break;
                    case 3:
                        System.out.print(service.getEmployeeService().getEmployeeController().toString());
                        break;
                    case 4:
                        if (!Add_Shift())
                            System.out.println("WorkingSchedule was not created");
                        break;
                    case 5:
                        Edit_Employee_In_Shift();
                        break;
                    case 6:
                        System.out.println(service.getScheduleService().getScheduleController().toString());
                        break;
                    case 7:
                        Delete_Employee();
                        break;
                    case 8:
                        Delete_Shift();
                        break;
                    default:
                        returnToMain=true;
                        break;
                }
                if(returnToMain)
                    break;
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
    public static void DeliveriesMenu()
    {
        Scanner in = new Scanner(System.in);
        boolean returnToMain=false;
        while(true) {
            System.out.println("choose an option: ");
            for (int i = 0; i < DeliveriesMenuOptions.values().length; i++) {
                System.out.println(i + 1 + ") " + DeliveriesMenuOptions.values()[i].toString());
            }
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    trucksMenu();
                    break;
                case 2:
                    locationsMenu();
                    break;
                case 3:
                    deliveriesMenu();
                    break;
                case 4:
                    returnToMain=true;
                    break;
            }
            if(returnToMain)
                break;
        }
    }
        private static boolean inBounds(Integer arr, Integer index) {
        return index >= 0 && index <= arr;
    }
    public static void trucksMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("trucks menu:\n1) create new truck\n2) delete truck\n3) display trucks\n4) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name;
        double netoWeight, totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("please enter truck details: truck id, model, neto weight, total weight");
                    id = in.next();
                    name = in.next();
                    netoWeight = in.nextDouble();
                    totalWeight = in.nextDouble();
                    service.createTruck(id, name, netoWeight, totalWeight);
                    break;
                case 2:
                    System.out.println("please enter the truck id that you want to erase from the system");
                    id = in.next();
                    service.removeTruck(id);
                    break;
                case 3:
                    service.printTrucks();
                    break;
                case 4:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    public static void locationsMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("locations menu:\n1) create new location\n2) delete location\n3) change telephone number\n" +
                "4) change contact name\n5) display location\n6) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String  name, licenseType, licenseExpDate, s1, s2;
        int id;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("please enter location details: name, address, telephone number,\n" +
                            "contact name, shipping area");
                    //id = in.nextInt();
                    in.nextLine();
                    name = in.nextLine();
                    licenseType = in.nextLine();
                    licenseExpDate = in.nextLine();
                    s1 = in.nextLine();
                    s2 = in.nextLine();
                    service.createLocation(name, licenseType, licenseExpDate, s1, s2);
                    break;
                case 2:
                    System.out.println("please enter the location id that you want to erase from the system");
                    id = in.nextInt();
                    service.removeLocation(id);
                    break;
                case 3:
                    System.out.println("please enter location id and the new telephone number");
                    id = in.nextInt();
                    s1 = in.next();
                    service.changetelNumber(id, s1);
                    break;
                case 4:
                    System.out.println("please enter location id and the new contact name");
                    id = in.nextInt();
                    s1 = in.next();
                    service.changecontactName(id, s1);
                    break;
                case 5:
                    service.printLocations();
                    break;
                case 6:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }


    public static void deliveriesMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("deliveries menu:\n1) create new delivery\n2) delete delivery\n3) change delivery date\n" +
                "4) change delivery time\n5) change truck for delivery\n" +
                "6) change driver for delivery\n7) remove item form order in delivery\n8) change quantity for item in order in delivery\n" +
                "9) change status\n10) display deliveries\n11) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id,name, licenseType, licenseExpDate, s2;
        Date date;
        int s1;
        double totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("to crate delivery please enter the following details: order id,truck id and driver id");
                    id=in.next();
                    licenseExpDate = in.next();
                    int driverID = in.nextInt();


                    service.createDelivery(id,licenseExpDate,driverID);
                    break;
                case 2:
                    System.out.println("please enter the delivery id that you want to erase from the system");
                    id = in.next();
                    service.removeDelivery(id);
                    break;
                case 3:
                    System.out.println("please enter the delivery id and the new delivery day");
                    id = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    service.changeDeliveryDay(id, date);
                    break;
                case 4:
                    System.out.println("please enter the delivery id and the new delivery leaving time");
                    id = in.next();
                    String d = in.next();
                    Time newTime = Time.valueOf(d);
                    service.changeLeavingTime(id, newTime);
                    break;
                case 5:
                    System.out.println("please enter delivery id, truck id");
                    id = in.next();
                    String tid = in.next();
                    service.changeTruckId(id, tid);
                    break;
                case 6:
                    System.out.println("please enter delivery id, driver id");
                    id = in.next();
                    int temp = in.nextInt();
                    service.changeDriverId(id, temp);
                    break;
                case 7:
                    System.out.println("please enter delivery id");
                    id=in.next();
                    System.out.println("please enter item id from the fowling");
                    service.printItemInOrder(id);
                    int item=in.nextInt();
                    service.removeItemFromOrder(id,item);
                    break;
                case 8:
                    System.out.println("please enter delivery id");
                    id = in.next();
                    System.out.println("please enter item id from the fowling and new quantity");
                    service.printItemInOrder(id);
                    s1 = in.nextInt();
                    int ss2 = in.nextInt();
                    service.changeQunForItemInOrder(id,s1,ss2);
                    break;
                case 9:
                    System.out.println("please enter delivery id, new delivery status that could be" +
                            "\nInTransit or Delivered");
                    id = in.next();
                    String sta= in.next();
                    service.changeStatus(id, sta);
                    break;
                case 10:
                    service.printDeliveries();
                    break;
                case 11:


                    break;

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    private static boolean Register_Employee() {
        try {
            Scanner in = new Scanner(System.in);
            boolean moreRoles = true;
            boolean isDriver = false;
            System.out.print("enter employee name: ");
            String name = in.next();
            System.out.print("enter employee ID: ");
            Integer ID = in.nextInt();
            System.out.print("enter employee bank account: ");
            Integer bank = in.nextInt();
            if (bank<0){
                bank=0;
            }
            System.out.print("enter employee vacation days: ");
            Integer vacation = in.nextInt();
            if (vacation<0){
                vacation=0;
            }
            System.out.print("enter employee salary: ");
            Integer salary = in.nextInt();
            if (salary<0){
                salary=0;
            }
            LinkedList<String> role = new LinkedList<>();
            while (moreRoles) {
                System.out.println("choose employee role from the following: ");
                for (int i = 0; i < roleOptions.values().length; i++) {
                    System.out.println(i + 1 + ". " + roleOptions.values()[i].toString());
                }
                Integer roleChoice = in.nextInt();
                if (!inBounds(roleOptions.values().length, roleChoice))
                    System.out.println("index out of bounds");
                else if (!role.contains(roleOptions.values()[roleChoice - 1].toString()))
                    role.add(roleOptions.values()[roleChoice - 1].toString());
                System.out.print("any more roles? choose [Y/N]: ");
                String ans = in.next();
                if (!ans.equals("Y") && !role.isEmpty()) {
                    moreRoles = false;
                }
            }
            if (role.contains("Driver")) {
                System.out.print("enter license type:\nA- up to 5000 KG\nB- up to 10,000 KG\nC- up to 15,000 KG\n"
                        + "D- up to 20,000 KG\n");
                String licenseType = in.next();
                if (!licenseType.equals("A")&!licenseType.equals("B")&!licenseType.equals("C")&!licenseType.equals("D")){
                    System.out.println("license type invalid");
                    return false;
                }
                else {
                    System.out.print("enter license expiration date: ");
                    String licenseExpDate = in.next();
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    service.createDriver(name, ID, bank, salary, vacation, role, licenseType.toUpperCase(), date);
                }
            } else
                service.createEmployee(name, ID, bank, salary, vacation, role);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static void editEmployee() throws ParseException {
        try {
            boolean exit = false;
            Scanner myScanner = new Scanner(System.in);
        /*System.out.print("do you wish to proceed? choose [Y/N]: ");
        String goOut = myScanner.next();
        if (!goOut.equals("Y")) {
            return;
        }*/
            while (!exit) {
                System.out.println("choose an action: ");
                for (int i = 0; i < employeeDetails.values().length; i++) {
                    System.out.println(i + 1 + ") " + employeeDetails.values()[i].toString());
                }

                int userInput = myScanner.nextInt();
                Integer ID = 0;
                if (userInput < 9 && userInput > 0) {
                    System.out.print("enter employee ID: ");
                    ID = myScanner.nextInt();
                    if (service.getEmployeeService().getEmployeeController().validID(ID))
                        userInput = 12;
                }
                switch (userInput) {
                    case 1:
                        System.out.print("enter employee name: ");
                        String name = myScanner.next();
                        service.getEmployeeService().getEmployeeController().setEmployeeName(ID, name);
                        break;
                    case 2:
                        System.out.print("enter bank account number: ");
                        Integer bankAccount = myScanner.nextInt();
                        if (bankAccount<0){
                            bankAccount=0;
                        }
                        service.getEmployeeService().getEmployeeController().setBankAccount(ID, bankAccount);
                        break;
                    case 3:
                        System.out.print("enter new salary: ");
                        Integer salary = myScanner.nextInt();
                        if (salary<0){
                            salary=0;
                        }
                        service.getEmployeeService().getEmployeeController().setSalary(ID, salary);
                        break;
                    case 4:
                        System.out.print("enter new vacation days number: ");
                        Integer vacationDays = myScanner.nextInt();
                        if (vacationDays<0){
                            vacationDays=0;
                        }
                        service.getEmployeeService().getEmployeeController().setVacationDays(ID, vacationDays);
                        break;
                    case 5:
                        boolean moreConstraints = true;
                        while (moreConstraints) {
                            System.out.println("choose constraint day from the following: ");
                            for (int i = 0; i < days.values().length; i++) {
                                System.out.println(i + 1 + ". " + days.values()[i].toString());
                            }
                            Integer constraintDay = myScanner.nextInt();
                            System.out.println("choose constraint shift from the following: ");
                            for (int i = 0; i < shift.values().length; i++) {
                                System.out.println(i + 1 + ". " + shift.values()[i].toString());
                            }
                            Integer constraintShift = myScanner.nextInt();
                            if (inBounds(shift.values().length, constraintShift) && inBounds(days.values().length, constraintDay)) {
                                Pair p = new Pair(days.values()[constraintDay - 1].toString(), shift.values()[constraintShift - 1].toString());
                                service.getEmployeeService().getEmployeeController().addConstraints(ID, p);
                            } else {
                                System.out.println("action was not valid");
                            }
                            System.out.print("any more constraints? choose [Y/N]: ");
                            String ans = myScanner.next();
                            if (!ans.equals("Y")) {
                                moreConstraints = false;
                            }
                        }
                        break;
                    case 6:
                        boolean toContinue = true;
                        List<Pair<String,String>> list = service.getEmployeeService().getEmployeeController().getConstraints(ID);
                        if (list.isEmpty()) {
                            System.out.println("the employee does not have constraints");
                        } else {
                            while (toContinue) {
                                System.out.println("choose constraint to remove from the following: ");
                                for (int i = 0; i < list.size(); i++) {
                                    System.out.println(i + 1 + ". " + list.get(i).toString());
                                }
                                Integer constraint = myScanner.nextInt();
                                if (inBounds(list.size(), constraint)) {
                                    service.getEmployeeService().getEmployeeController().removeConstraints(ID, list.get(constraint - 1));
                                } else {
                                    System.out.println("index out of bounds");
                                }
                                System.out.print("any more constraints to remove? choose [Y/N]: ");
                                String ans = myScanner.next();
                                if (!ans.equals("Y")) {
                                    toContinue = false;
                                }
                            }
                        }
                        break;
                    case 7:
                        boolean moreRoles = true;
                        while (moreRoles) {
                            System.out.println("choose role from the following: ");
                            for (int i = 0; i < roleOptions.values().length; i++) {
                                System.out.println(i + 1 + ". " + roleOptions.values()[i].toString());
                            }
                            Integer role = myScanner.nextInt();
                            if (inBounds(roleOptions.values().length, role)) {
                                String toAdd = roleOptions.values()[role - 1].toString();
                                if(toAdd.compareTo("Driver")==0)
                                {
                                    System.out.print("enter license type:\nA- up to 5000 KG\nB- up to 10,000 KG\nC- up to 15,000 KG\n"
                                            + "D- up to 20,000 KG\n");
                                    String licenseType = myScanner.next();
                                    System.out.print("enter license expiration date: ");
                                    String licenseExpDate = myScanner.next();
                                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                                    service.addRole(ID,toAdd,licenseType,date);
                                }
                                service.getEmployeeService().getEmployeeController().addRole(ID, toAdd);
                            } else {
                                System.out.println("index out of bounds");
                            }
                            System.out.print("any more roles? choose [Y/N]: ");
                            String ans = myScanner.next();
                            if (!ans.equals("Y")) {
                                moreRoles = false;
                            }
                        }
                        break;
                    case 8:
                        boolean toC = true;
                        List<String> roles = service.getEmployeeService().getEmployeeController().getRoles(ID);
                        int size = roles.size();
                        while (toC) {
                            System.out.println("choose role to remove from the following: ");
                            for (int i = 0; i < roles.size(); i++) {
                                System.out.println(i + 1 + ". " + roles.get(i));
                            }
                            Integer role = myScanner.nextInt();
                            if (inBounds(roles.size(), role)) {
                                if (size != 1) {
                                    if(roles.get(role-1).compareTo("Driver")==0)
                                    {
                                        service.removeDriver(ID);
                                        size--;
                                    }
                                    else
                                    {
                                        service.getEmployeeService().getEmployeeController().deleteRole(ID,roles.get(role-1));
                                        size--;
                                    }
                                } else {
                                    System.out.println("role list can not be empty, action was not committed");
                                }
                            } else
                                System.out.println("index out of bounds, action was not committed");

                            System.out.print("any more roles to remove? choose [Y/N]: ");
                            String ans = myScanner.next();
                            if (!ans.equals("Y")) {
                                toC = false;
                            }
                        }
                        break;
                    case 9:
                        System.out.println("please enter the driver id and the new expiration date");
                        int id = myScanner.nextInt();
                        String licenseExpDate = myScanner.next();
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                        service.changeExpDate(id, date);
                        break;
                    case 10:
                        System.out.println("please enter the driver id and the new license type");
                        int id1 = myScanner.nextInt();
                        String licenseExpDate1 = myScanner.next();
                        if (!licenseExpDate1.equals("A")&!licenseExpDate1.equals("B")&!licenseExpDate1.equals("C")&!licenseExpDate1.equals("D")){
                            System.out.println("license type invalid");
                        }
                        else
                            service.changeLicenseType(id1, licenseExpDate1);
                        break;
                    case 11:
                        exit = true;
                        break;
                    default:
                        System.out.println("choice out of bounds");
                        break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    private static boolean Add_Shift() throws Exception {
        Scanner myScanner = new Scanner(System.in);
        /*System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return false;
        }*/
        System.out.print("enter date as so dd/mm/yyyy: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = myScanner.next();
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("choose shift kind from the following: ");
        for (int i = 0; i < shift.values().length; i++) {
            System.out.println(i + 1 + ". " + shift.values()[i].toString());
        }
        Integer shiftKind = myScanner.nextInt();
        System.out.println("choose shift manager id: ");
        Integer shiftManager = myScanner.nextInt();
        if (!inBounds(shift.values().length, shiftKind) || localDate.compareTo(LocalDate.now()) <= 0 || !service.getEmployeeService().getEmployeeController().checkShiftManager(shiftManager)) {
            System.out.println("shift kind or date are invalid or you don't have Shift manager");
            return false;
        }
        if(service.getScheduleService().getScheduleController().addWorkingSchedule(localDate, shift.values()[shiftKind-1].toString())) {
            if(service.getScheduleService().getScheduleController().addWorkersToShift("Shift_Manager",shiftManager,localDate,shift.values()[shiftKind-1].toString()))
                return true;
            else
            {
                service.getScheduleService().getScheduleController().removeShift(localDate,shift.values()[shiftKind-1].toString());
            }
        }
        return false;
    }

    private static void Edit_Employee_In_Shift() throws Exception {
        Scanner myScanner = new Scanner(System.in);
        /*System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return;
        }*/
        System.out.print("enter date as so dd/mm/yyyy: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = myScanner.next();
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("choose shift kind from the following: ");
        for (int i = 0; i < shift.values().length; i++) {
            System.out.println(i + 1 + ". " + shift.values()[i].toString());
        }
        Integer shiftKind = myScanner.nextInt();
        if (!inBounds(shift.values().length, shiftKind) || localDate.compareTo(LocalDate.now()) <= 0) {
            System.out.println("shift kind or date are invalid");
            return;
        }
        if(!service.getScheduleService().getScheduleController().getShift(localDate, shift.values()[shiftKind-1].toString())){
            System.out.println("WorkingSchedule is not in record");
            return;
        }
        System.out.println("choose an action: ");
        for (int i = 0; i < actionInShift.values().length; i++) {
            System.out.println(i + 1 + ". " + actionInShift.values()[i].toString());
        }
        int choice = myScanner.nextInt();
        List<String> roles = null;
        Integer ID = 0;
        if (choice > 0 && choice < actionInShift.values().length) {
            System.out.print("enter employee ID: ");
            ID = myScanner.nextInt();
            if (service.getEmployeeService().getEmployeeController().validID(ID)) {
                System.out.println("employee does not exist");
                choice = 6;
            } else {
                roles = service.getEmployeeService().getEmployeeController().getRoles(ID);
            }
        }
        switch (choice) {
            case 1:
                System.out.println("choose a roll from the following:");
                for (int i = 0; i < roles.size(); i++) {
                    System.out.println(i + 1 + ". " + roles.get(i));
                }
                Integer index = myScanner.nextInt();
                if (inBounds(roles.size(), index)) {
                    if(!service.getScheduleService().getScheduleController().addWorkersToShift(roles.get(index - 1),ID,localDate,shift.values()[shiftKind-1].toString()))
                        System.out.println("the employee is unavailable");
                } else {
                    System.out.println("index out of bound");
                }
                break;
            case 2:
                if (!service.getScheduleService().getScheduleController().removeEmployeeFromShift(ID,localDate,shift.values()[shiftKind-1].toString())) {
                    System.out.println("action was not committed, check your input");
                }
                break;
            case 3:
                System.out.println("choose a roll from the following:");
                for (int i = 0; i < roles.size(); i++) {
                    System.out.println(i + 1 + ". " + roles.get(i));
                }
                Integer roleIndex = myScanner.nextInt();
                if (inBounds(roles.size(), roleIndex)) {
                    if(!service.getScheduleService().getScheduleController().ChangeEmployeeRole(ID,roles.get(roleIndex-1),localDate,shift.values()[shiftKind-1].toString())){
                        System.out.println("employee is not apart of the shift");
                    }
                } else {
                    System.out.println("action was not committed, index out of bound");
                }
                break;
            default:
                break;
        }
    }

    private static void Delete_Employee() throws Exception {
        Scanner myScanner = new Scanner(System.in);
        /*System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return;
        }*/
        System.out.print("enter employee ID: ");
        Integer ID = myScanner.nextInt();
        if (ID>=0 && !service.getEmployeeService().getEmployeeController().validID(ID)){
            //service.getScheduleService().getScheduleController().deleteEmployeeFromShift(ID);
            service.getEmployeeService().getEmployeeController().deleteEmployee(ID);
            return;
        }
        System.out.println("Employee does not exit in record");
    }

    private static void Delete_Shift() throws Exception {
        Scanner myScanner = new Scanner(System.in);
        /*System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return;
        }*/
        System.out.print("enter date as so dd/mm/yyyy: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = myScanner.next();
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("choose shift kind from the following: ");
        for (int i = 0; i < shift.values().length; i++) {
            System.out.println(i + 1 + ". " + shift.values()[i].toString());
        }
        Integer shiftKind = myScanner.nextInt();
        if (!inBounds(shift.values().length, shiftKind) || localDate.compareTo(LocalDate.now()) <= 0) {
            System.out.println("shift kind or date are invalid");
            return;
        }
        service.getScheduleService().getScheduleController().removeShift(localDate, shift.values()[shiftKind-1].toString());
    }


}