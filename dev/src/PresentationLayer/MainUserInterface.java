package PresentationLayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import DataAccessLaye.Repo;
import ServiceLayer.Service;
import ServiceLayer.*;
import bussinessLayer.DTOPackage.*;

import static java.lang.System.exit;

public class MainUserInterface {

    enum Job{
        HR, STOCKMANAGER, LOGISTICMANAGER, STOREMANAGER
    }

    private static IOrderService oService = OrderService.getInstance();
    private static ISupplierService supService = SupplierService.getInstance();
    private InventoryService invService = new InventoryService();
    private static BranchService branchService = new BranchService();
    private Service DelAndEmpService=new Service();
    private Repo repo = null;
    private static Scanner sc = new Scanner(System.in);
    private OrderMenu orderMenu = new OrderMenu();
    public static Job job;
    private int branchId;
    private static Service service = Service.getInstance();


    public void start() {
        System.out.println("welcome to the system manager");
        System.out.println("please choose how to initialize the system");
        loadProgramDefault();
        oService.startScheduledOrder();
        String input;

        while(true) {
            /*
             * printMenu(); try { input = Integer.valueOf(getUserInput()); } catch
             * (Exception e) { input = -1; } int branchId = -1;
             */

        /*	try {
				branchId = chooseBranch();
			} catch (Exception e) {
				continue;
			}*/
            chooseJob();
            switch(job) {
                case HR:
                    humanResourcesMenu();
                    break;
                case LOGISTICMANAGER:
                    logisticManagerMenu();
                    break;
                case STOCKMANAGER:
                    stockManagerMenu();
                    break;
                case STOREMANAGER:
                    storeManagerMenu();
                    break;
                default:
                    break;

            }
        }
//            switch (input) {
//                case 1:
//                    try { branchId = chooseBranch(); } catch (Exception e) {System.out.println(e.getMessage()); }
//                    if(branchId != -1) {
//
//                        int supplierId = chooseSupplier();
//
//                        // branchId = 1;
//                        if (branchId == -1 || supplierId == -1) break;
//                        manageSuppliers(supplierId, branchId);
//                    }
//                    break;
//                case 2:
//
//                    creatSupplierAndContract();// CREATE A NEW SUPPLIER AND ADD IT TO SYSTEM
//                    break;
//                case 3:
//                    System.out.println("Please choose a menu:");
//                    System.out.println("1) Inventory menu");
//                    System.out.println("2) Branch menu");
//
//                    int choice = sc.nextInt();
//                    sc.nextLine();
//                    if(choice == 1)
//                        mainMenu.showInventoryMenu();
//                    else if(choice == 2) {
//                        try {
//                            branchId = chooseBranch();
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        if(branchId != -1) {
//                        	mainMenu.currentBranchId = branchId;
//                            mainMenu.showBranchMenu();
//                        }
//                    }
//                    else
//                        System.out.println("Wrong input.");
//                    //TODO MANAGE INVENTORY OPTION
//                    //TODO: update inventory menu's currentBranchId
//                    break;
//                case 4:
//                	orderMenu.manageOrders();
//                	break;
//                case 5:
//                	PresentationLayer.Menu.start();
//                	break;
//                case 6:
//                    Quit();
//                    break;
//                default:
//                    System.out.println("wrong - Input");
//            }
//
//        } while (input != 6);
    }

    private void humanResourcesMenu() {
        try {
            Menu.EmployeesMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logisticManagerMenu() {
        Menu.DeliveriesMenu();
    }

    private void storeManagerMenu() {
        String choice;
        while(true) {
            System.out.println("1) Manage Suppliers\n2) Create Supplier and Contract\n3) display trucks\n4) display locations\n5) display deliveries\n" +
                    "6) Display_Employee\n7) Display_Shift\n8) display inventory and branches' details\n9) To previous menu");//TODO WHAT IS TE PULL REPORTS AND STUFF TO ADD IT TO MENU
            choice = getUserInput();
            switch(choice) {
                case "1":
                    int supplierId = chooseSupplier();//TODO ADD CREATE SUPPLIER TO MANAGE SUPPLIERS
                    manageSuppliers(supplierId, branchId);
                    break;
                case "2":
                    creatSupplierAndContract();
                    break;
                case "3":
                    try {
                        service.printTrucks();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    try {
                        service.printLocations();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "5":
                    try {
                        service.printDeliveries();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "6":
                    System.out.print(service.getEmployeeService().getEmployeeController().toString());
                    break;
                case "7":
                    System.out.println(service.getScheduleService().getScheduleController().toString());
                    break;
                case "8":
                    try {
                        branchId = chooseBranch();
                    } catch (Exception e) {
                        System.out.println("Error - "+e.getMessage());
                        break;
                    }
                    mainMenu.currentBranchId = branchId;
                    mainMenu.showStoreManagerBranchMenu();
                    break;
                case "9":
                    return;
                default:
                    break;
            }
        }

    }

    private void stockManagerMenu() {
        String choice;
        while(true) {
            System.out.println("1) Inventory Menu\n2) Branch Menu\n3) Manage Suppliers\n4) Manage Orders\n5) Create Supplier and Contract\n6) To previous menu");
            choice = getUserInput();
            switch(choice) {
                case "1":
                    mainMenu.showInventoryMenu();
                    break;
                case "2":
                    try {
                        branchId = chooseBranch();
                    } catch (Exception e) {
                        System.out.println("Error - "+e.getMessage());
                        break;
                    }
                    mainMenu.currentBranchId = branchId;
                    mainMenu.showBranchMenu();
                    break;
                case "3":
                    int supplierId = chooseSupplier();//TODO ADD CREATE SUPPLIER TO MANAGE SUPPLIERS
                    manageSuppliers(supplierId, branchId);
                    break;
                case "4":
                    orderMenu.manageOrders();
                    break;
                case "5":
                    creatSupplierAndContract();
                case "6":
                    return;
            }
        }
    }

    private void chooseJob() {
        String choice = "";
        while(true) {
            System.out.println("Choose your job:\n1) Human Resources manager\n2) Stock Manager\n3) Logistic Manager\n4) Store Manager\n5) Exit the system");
            choice = getUserInput();
            switch(choice) {
                case "1":
                    job = Job.HR;
                    return;
                case "2":
                    job = Job.STOCKMANAGER;
                    return;
                case "3":
                    job = Job.LOGISTICMANAGER;
                    return;
                case "4":
                    job = Job.STOREMANAGER;
                    return;
                case "5":
                    exit(0);
                /*default:
                    System.out.println("enter again");*/
            }
        }

    }

    /**
     * Menu to choose branch
     *
     * @return The Branch ID
     */
    private int chooseBranch() throws Exception {
        String choice = "";
        while (true) {
            if(getBranchCounter()>0) {
                System.out.println("Printing branches:");
                printAllBranches();
            }
            else{
                System.out.println("No branches created. Please create a new branch:");
                mainMenu.createFirstBranch();
            }
            System.out.println("Enter the Branch ID you wish to manage:");
            choice = getUserInput();
            boolean isExist = false;
            try {
                isExist = branchExist(choice);
            }catch (Exception e)
            {
                throw new Exception("Error while trying to get branches from Repo" + e.getMessage());
            }
            if (!isExist) {
                throw new Exception("Branch does not exist");
            } else {
                return Integer.parseInt(choice);
            }
        }
    }

    private int getBranchCounter() throws SQLException {
        return branchService.getAllDTOBranches().getObj().size();
    }

    private boolean branchExist(String choice) {
        ResponseT<BranchDTO> response = branchService.getBranchDTOById(choice);
        System.out.println(response.getMessage());
        return !response.isErrorOccured();
    }

    static void printAllBranches(){
        ResponseT<List<BranchDTO>> response = branchService.getAllDTOBranches();
        if(response.isErrorOccured()) {
            System.out.println(response.getMessage());
            return;
        }
        for (BranchDTO branch: response.getObj()) {
            System.out.println("Branch id: "+branch.getId() +", name: "+branch.getDescription());
        }
    }

    /**
     * Choose supplier menu
     *
     * @return supplier ID
     */
    private int chooseSupplier() {
        String choice = "";
        while (true) {
            printSuppliers();
            System.out.println("Enter the supplier's ID or \"b\" to return to menu:");
            choice = getUserInput();
            Response response = supService.isSupplierExist(choice);
            if (choice.equals("b")) return -1;
            if (!response.isErrorOccured()) {
                System.out.println(response.getMessage());
                break;
            }
            System.out.println(response.getMessage());
        }

        return Integer.valueOf(choice);
    }

    /**
     * Print all suppliers
     */
    static void printSuppliers() {
        ResponseT<List<SupplierDTO>> r = supService.getSuppliersInfo();
        String s = "";
        if (r.isErrorOccured()) {
            System.out.println("There are no Suppliers");
            return;
        }
        for (SupplierDTO sup : r.getObj()) {
            s += "\n" + sup.getSupplierId() + "\t" + sup.getName();
        }

        System.out.println(s);
    }

    /**
     * Manage Supplier menu showing all the option
     * which the user could perform on a supplier
     *
     * @param supplierId The supplier ID to act on
     */
    private void manageSuppliers(int supplierId, int branchId) {

        int input = 0;
        do {
            printSupplierMenu();
            try {
                input = Integer.valueOf(getUserInput());
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 1:
                    System.out.println("are you sure? [y/n] ");
                    if (getUserInput().equals("n"))
                        break;
                    System.out.println(supService.removeSupplier(supplierId).getMessage());// DELETE SUPPLIER FROM THE SYSTEM
                    return;
                case 2:
                    updateSupplier(supplierId);// UPDATE FIELDS OF SUPPLIER
                    break;
                case 3:
                    deleteContactFromSupplier(supplierId); // DELETE CONTACT LIST FROM SPECIFIC SUPPLIER
                    break;
                case 4:
                    updateContactForSupplier(supplierId); // UPDATE CONTACT INFO FROM SPECIFIC SUPPLIER
                    break;
                case 5:
                    addItemToSupplierCatalog(supplierId); // ADD NEW ITEM TO CATALOG FOR SPECIFIC SUPPLIER
                    break;
                case 6:
                    deleteItemFromCatalog(supplierId);// DELETE ITEM FROM CATALOG FOR SPECIFIC SUPPLIER
                    break;
                case 7:
                    getSuppliersInfo(supplierId); // PRINT THE SUPPLIERS INFORMATION (NAME,ID,BANK-ACCOUNT) //TODO
                    // CHANGE TO ONLY SPECIFIC SUPPLIER
                    break;
                case 8:
                    return; // RETURN TO PREVIOUS MENU
                default:
                    System.out.println("Invalid input");
            }
        } while (input != 8);

    }

    /**
     * Prints the main menu options
     */
    private void printMenu() {
        System.out.println("1) Manage Suppliers\n2) Create new Supplier\n3) Manage Inventory and Branches\n4) Manage Orders\n5) plMenu\n6) Quit");

    }

    /**
     * Menu for showing supplier's details
     *
     * @param supplierId The supplier ID
     */
    private void getSuppliersInfo(int supplierId) {
        int input;
        do {
            System.out.println(
                    "1) present the catalogItem for Supplier\n2) present the contact list for Supplier\n3) present payment option for Supplier\n4) present what days Supplier do delivery\n5) Return to previous menu");
            String userInput = getUserInput();
            if (userInput.equals("b"))
                return;
            try {
                input = Integer.valueOf(userInput);
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 1:
                    System.out.println(supService.getCatalog(supplierId).getObj());
                    break;
                case 2:
                    System.out.println(printContacts(supService.getContactsList(supplierId)));
                    break;
                case 3:
                    System.out.println(supService.getSupplierInfo(String.valueOf(supplierId)).getObj());
                    break;
                case 4:
                    System.out.println(supService.getContractDetails(supplierId).getObj());
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        } while (input != 5);

    }

    /**
     * Menu for deleting item from Supplier's catalog
     *
     * @param supplierId The supplier ID
     */
    private void deleteItemFromCatalog(int supplierId) {
        int catalogItemId = 0;
        String string = "";
        System.out.println("Please enter CatalogItemId For the item you want to remove");
        string = getUserInput();
        if (string.equals("b"))
            return;
        try {
            catalogItemId = Integer.valueOf(string);
            System.out.println(supService.deleteCatalogItemFromCatlogInContract(supplierId, catalogItemId));
        } catch (Exception e) {
            System.out.println("catalog item id invalid input");
        }
    }


    /**
     * Menu for adding item to Supplier's catalog
     *
     * @param supplierId The supplier ID
     */
    private void addItemToSupplierCatalog(int supplierId) {
        int ItemId = 0;
        int catalogItemId = 0;
        double price = 0;
        String string = "";
        System.out.println("Please enter ItemId from the list of items");
        ResponseT<List<ItemDTO>> itemsList = invService.getItemsList();
        printItemsFromInventory(itemsList);

        string = getUserInput();
        if (string.equals("b"))
            return;
        try {
            ItemId = Integer.valueOf(string);
            System.out.println("Please enter CatalogItemId For the item you chose");
            string = getUserInput();
            if (string.equals("b"))
                return;
            catalogItemId = Integer.valueOf(string);
            System.out.println("Please enter price for CatalogItem you chose");
            string = getUserInput();
            if (string.equals("b"))
                return;
            price = Double.valueOf(string);
            System.out.println(supService.addCatalogItemToCatalogInContract(supplierId, ItemId, catalogItemId, price).getMessage());
            addNewAgreementToItem(supplierId, catalogItemId);
        } catch (Exception e) {
            System.out.println("Input Invalid");
            return;
        }
    }

    /**
     * Prints all items from inventory
     *
     * @param itemsList list of all items
     */
    private void printItemsFromInventory(ResponseT<List<ItemDTO>> itemsList) {
        if(itemsList.getObj().isEmpty()) {
            System.out.println("There are not items in this moment");
            return;
        }
        System.out.println("ID\tDescription");
        for (ItemDTO item : itemsList.getObj()) {
            System.out.println(item.getId() + "\t" + item.getDescription());
        }
    }

    /**
     * Menu for adding new agreement(Ranges) for catalog item related to specific
     * supplier
     *
     * @param supplierId    The supplier ID
     * @param catalogItemId The catalog item ID
     */
    private void addNewAgreementToItem(int supplierId, int catalogItemId) {
        String string;
        int max = 0;
        int min = 0;
        double priceafterDisc = 0;
        supService.cleanRangeListItemFromMap(supplierId, catalogItemId);
        do {
            System.out.println(
                    "Enter item amount price agreement as follow: min:max:price if entered max = -1 means done");
            string = getUserInput();
            String split[] = string.split(":");
            try {
                min = Integer.valueOf(split[0]);
                max = Integer.valueOf(split[1]);
                priceafterDisc = Double.valueOf(split[2]);
            } catch (Exception e) {
                System.out.println("Not Numbers");
            }
            System.out.println(supService.UpdateMap(supplierId, catalogItemId, min, max, priceafterDisc).getMessage()); // TODO
        } while (max != -1);
    }

    /**
     * Opens menu for updating supplier's details
     *
     * @param supplierId The supplier ID
     */
    private void updateContactForSupplier(int supplierId) {
        String phoneNum = "";
        for (ContactDTO it : supService.getContactsList(supplierId).getObj()) {
            System.out.println("\n" + it +"\n");
        }
        System.out.println("Please enter phone Number of the contact you would like to change from the list above");
        phoneNum = getUserInput();
        if (phoneNum.equals("b")) return;
        System.out.println(
                "Please enter details as follow: firstName:lastName:phoneNumber:address if change is not needed for one of the fields just enter empty like this: ''  ");
        System.out.println("example: ' dov:itzhak::bit yani");
        String split = getUserInput();
        if (split.equals("b"))
            return;
        String[] update = split.split(":");
        System.out.println(supService.updateContact(supplierId, update, phoneNum));

    }

    /**
     * Opens a menu for deleting contact from supplier
     *
     * @param supplierId The supplier ID
     */
    private void deleteContactFromSupplier(int supplierId) {
        System.out.println("Please enter phone number of the contact you would like to delete from list Of contact");
        System.out.println(printContacts(supService.getContactsList(supplierId)));
        String s = getUserInput();
        if (s.equals("b"))
            return;
        System.out.println(supService.deleteContact(supplierId, s).getMessage());
    }

    private String printContacts(ResponseT<List<ContactDTO>> contactsList) {
        String s = "Phone\t\tAddress\t\tLast Name\t\tFirst Name\n";
        for (ContactDTO contact : contactsList.getObj()) {
            s+=contact.toString();
        }
        return s;
    }

    /**
     * Opens a menu for updating supplier
     *
     * @param supplierId The supplier ID
     */
    private void updateSupplier(int supplierId) {
        int input = 0;
        String s = "";

        do {
            System.out.println(
                    "1) update Supplier name \n2) update bank Account Number\n3) update billingOption \n4) update if supplier is deliver\n5)update const day delivery days \n6)Add Contact\n7)Change agreement contract for specific Item\n8)exit");
            s = getUserInput();
            if (s.equals("b"))
                return;
            try {
                input = Integer.valueOf(s);
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 1:
                    System.out.println("Please enter new Supplier name:");
                    s = getUserInput();
                    if (s.equals("b"))
                        return;
                    String name = s;
                    System.out.println(supService.updateSupplierName(supplierId, name).getMessage());
                    break;
                case 2:
                    System.out.println("Please enter new Supplier bank Account");
                    s = getUserInput();
                    if (s.equals("b"))
                        return;
                    int bankAccount;
                    try {
                        bankAccount = Integer.valueOf(s);
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                        break;
                    }
                    System.out.println(supService.updateSupplierBankAccount(supplierId, bankAccount).getMessage());
                    break;
                case 3:
                    System.out.println(
                            "Please enter one of Supplier billing option -> {EOM30 / EOM60 / CASH / BANKTRANSFER / CHECK}");
                    s = getUserInput();
                    if (s.equals("b"))
                        return;
                    String bilingOption = s;
                    System.out.println(supService.updateBillingOptions(supplierId, bilingOption).getMessage());
                    break;
                case 4:
                    System.out.println(
                            "Please enter if supplier has Deliveries or not -> press (y/other button except b)");
                    s = getUserInput();
                    if (s.equals("b"))
                        return;
                    String IsDelivery = s;
                    boolean isDeliver = false;
                    if (IsDelivery.equals("y")) {
                        isDeliver = true;
                    }
                    String error = supService.updateContractIsDeliver(supplierId, isDeliver).getMessage();
                    System.out.println(error);
                    if (error.equals("Done")&&isDeliver)
                        addConstDayDelivery(supplierId);
                    break;
                case 5:
                    addConstDayDelivery(supplierId);
                    break;
                case 6:
                    addContact(supplierId);
                    break;
                case 7:
                    System.out.println("Please enter Catalog-item-id to change in the agreement:");
                    String userInput = getUserInput();
                    if (userInput.equals("b"))
                        break;
                    int catalogItemId;
                    try {
                        catalogItemId = Integer.valueOf(userInput);
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                        break;
                    }
                    addNewAgreementToItem(supplierId, catalogItemId);
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;

            }
        } while (input != 8);
    }

    /**
     * Opens menu for adding contact to supplier
     *
     * @param supplierId The supplier ID
     */
    private void addContact(int supplierId) {
        String s;
        System.out.println("add contact in this way -> firstName:lastName:phoneNumber:address ,  to add contact pleas pres enter to finish pres 0 and then enter");
        // input = Integer.valueOf(getUserInput());
        s = getUserInput();
        if (s.equals("b") || s.equals("0"))
            return;
        String[] contact = s.split(":");
        System.out.println(supService.addContact(supplierId, contact[0], contact[1], contact[2], contact[3]).getMessage());

    }

    /**
     * Opens Menu for creating supplier and contract
     */
    private void creatSupplierAndContract() {
        try {
            System.out.println("Enter supplier ID:");
            String s = getUserInput();
            if (s.equals("b"))
                return;
            int supplierId = Integer.valueOf(s);
            System.out.println("Enter supplier Name:");
            s = getUserInput();
            if (s.equals("b"))
                return;
            String SupplierName = s;
            System.out.println("Enter supplier BankAccount:");
            s = getUserInput();
            if (s.equals("b"))
                return;
            int bankAcount = Integer.valueOf(s);
            System.out.println("Enter billing Options, choose one of the presented potions");
            System.out.println(
                    "please enter the details exactly in the next format: {EOM30 / EOM60 / CASH / BANKTRANSFER / CHECK} ");
            s = getUserInput();
            if (s.equals("b"))
                return;
            String bilingOptions = s;
            boolean isDeliver = false;
            System.out.println("the supplier is deliver if y/n ?");
            s = getUserInput();
            if (s.equals("b"))
                return;
            String IsDelivery = s;
            if (IsDelivery.equals("y")) {
                isDeliver = true;
            }
            String error = supService.AddSupplier(SupplierName, supplierId, bankAcount, bilingOptions, isDeliver).getMessage();
            System.out.println(error);
            if (!error.equals("Done")) {
                return;
            }

            if (IsDelivery.equals("y"))
                addConstDayDelivery(supplierId);

            completeContract(supplierId);

            do {
                System.out.println(
                        "add contact in this way -> firstName:lastName:phoneNumber:address ,  to add contact please press enter to finish the process pres 0");
                // input = Integer.valueOf(getUserInput());
                s = getUserInput();
                if (s.equals("b") || s.equals("0"))
                    return;
                String[] contact = s.split(":");
                System.out.println(supService.addContact(supplierId, contact[0], contact[1], contact[2], contact[3]).getMessage());
            } while (true);
        } catch (Exception e) {
            System.out.println("Invalid Input");
            return;
        }

    }

    /**
     * Adding const day delivery
     *
     * @param supplierId
     */
    private void addConstDayDelivery(int supplierId) {

        System.out.println(
                "Please enter const day delivery with big letters in this way -> MONDAY:SUNDAY:..: , if not deliver in const days press 'b' ");
        String input = getUserInput();
        if (input.equals("b"))
            return;
        String[] constDayDeli = input.split(":");
        System.out.println(supService.addConstDeliveryDays(constDayDeli, supplierId).getMessage());
    }

    private void completeContract(int supplierId) {
        System.out.println("Now you should add items to catalog");
        do {
            System.out.println("continue? [y/n]");
            if (getUserInput().equals("n"))
                return;
            addItemToSupplierCatalog(supplierId);
        } while (true);
    }

    /**
     * closing the scanner and prints goodbye
     */
    private void Quit() {
        sc.close();
        oService.purgeTimer();
        try {
            Repo.getInstance().close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("BYE!");
    }

    static boolean printCatalogItemsOfSupplier(String supplierId) {
        ResponseT<SupplierDTO> response = supService.getSupplierInfo(supplierId);
        if(response.isErrorOccured()) {
            System.out.println(response.getMessage());
            return response.isErrorOccured();
        }
        System.out.println(response.getObj().getCatalog());
        return false;
    }

    /**
     * Loading the program with basic objects or clean start
     */
    @SuppressWarnings("static-access")
    public void loadProgramDefault() {

        try{
            System.out.println("remain with old data[y/any other key]");
            if(getUserInput().equals("y"))return;
            repo = repo.getInstance();
            try{repo.clean();}catch (Exception e) {}
            repo.creatTables();
        }catch (Exception e) {
            System.out.println("Something went wrong try again!");
            loadProgramDefault();
            return;
        }

        while (true) {
            System.out.println("1) Load with objects\n2) Clean start");
            int input;
            try {
                input = Integer.valueOf(getUserInput());
            } catch (Exception e) {
                System.out.println("Invalid Input");
                continue;
            }
            switch(input) {
                case 1:
                    try {
                        loadFirstObjectsToProgram();
                        invService.initialInventoryInDB();
                        DelAndEmpService.addFakeEmployes();
                        DelAndEmpService.addFakeShifts();
                        DelAndEmpService.init();
                    } catch (Exception throwables) {
                        continue;
                    }
                    return;
                case 2:
                    try {
                        invService.initialInventoryInDB();// DON'T CHANGE!! ALREADY DROPED TABLES ABOVE SO ITS CLEAN!!
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return;
            }
        }
    }

    /**
     * Loads first object to the program
     */
    private void loadFirstObjectsToProgram() throws SQLException {
        try {
            invService.initialInventoryInDB();
        } catch (SQLException throwables) {
            throw throwables;
        }
        mainMenu.initData();
        supService.loadFirstSuppliers();
        oService.loadFirstOrders();
    }

    /**
     * Prints the Options of manage suppliers
     */
    public void printSupplierMenu() {
        System.out.println("1) Delete Supplier\n2) Update supplier\n3) Delete Contact\n4) Update Contact\n"
                +"5) Add Item to supplier's catalog\n6) Delete item from catalog\n7) Print supplier Info\n8) Return to previous menu");
    }

    /**
     * Gets input from the user
     *
     * @return The input received from the user as string
     */
    public static String getUserInput() { // GET USER INPUT
        String input = sc.nextLine();
        return input;
    }

}