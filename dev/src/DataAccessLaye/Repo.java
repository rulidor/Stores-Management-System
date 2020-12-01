package DataAccessLaye;

import bussinessLayer.DTOPackage.*;
import bussinessLayer.SupplierPackage.Supplier;
import javafx.util.Pair;
import org.sqlite.SQLiteConfig;
import DataAccessLaye.Interfaces.*;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repo {
    
    public static final String DB_URL = "jdbc:sqlite:Nituz.db";
    public static final String DRIVER = "org.sqlite.JDBC";  
    
    private static Repo repo = null;
    public static Connection con;
    private IBranchDAO branchDAO;
    private ICatalogItemDAO catalogItemDAO;
    private IContactDAO contactDao;
    private IDamagedControllerDAO damagedItemDAO;
    private IDeliveryDaysDAO deliveryDaysDAO;
    private IInventoryDAO inventoryDAO;
    private IItemDAO itemDAO;
    private IItemStatusDAO itemStatusDAO;
    private ILineCatalogItemInCartDAO lineCatalogItemInCartDAO;
    private IOrderDAO orderDAO;
    private IRangesDAO rangesDAODAO;
    private IScheduledOrderDAO scheduledDAO;
    private ISupplierDAO supplierDAO;
    private IContractDAO contractDAO;

    private Repo() throws SQLException {
    	try {
			con = getConnection();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        branchDAO = new BranchDAOImpl(con);
        catalogItemDAO = new CatalogItemDAOImpl(con);
        contactDao = new ContactDaoImpl(con);
        damagedItemDAO = new DamagedControllerDAOImpl(con);
        deliveryDaysDAO = new DeliveryDaysDAOImpl(con);
        inventoryDAO = new InventoryDAOImpl(con);
        itemDAO = new ItemDAOImpl(con);
        itemStatusDAO = new ItemStatusDAOImpl(con);
        lineCatalogItemInCartDAO = new LineCatalogItemInCartDAOImpl(con);
        orderDAO = new OrderDAOImpl(con);
        rangesDAODAO = new RangesDAODAOImpl(con);
        scheduledDAO = new ScheduledDAOImpl(con);
        supplierDAO = new SupplierDAOImpl(con);
        contractDAO = new ContractDAOImpl(con);
        creatTables();
    }

    public static Repo getInstance() throws SQLException {
        if (repo == null) {
        	repo = new Repo();
        }
        return repo;
    }


    public static Connection getConnection() throws ClassNotFoundException {  
        Class.forName(DRIVER);  
        Connection connection = null;  
        try {  
            SQLiteConfig config = new SQLiteConfig();  
            config.enforceForeignKeys(true);  
            connection = DriverManager.getConnection(DB_URL,config.toProperties());  
        } catch (SQLException ex) {}  
        return connection;  
    }

    public static  void creatTables() throws SQLException {
    	

        String sqlQ = "CREATE TABLE IF NOT EXISTS Suppliers("
                + "supplierName varchar(50),"
                + "supplierId INTEGER NOT NULL,"
                + "bankAccountNumber INTEGER,"
                + "bilingOptions varchar(50),"
                + "CONSTRAINT PK_Supplier Primary KEY(supplierId)"
                + ");";
        
        Statement stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";
        
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Contacts("
                + "supplierId INTEGER ,"
                + "firstName varchar,"
                + "lastName varchar,"
                + "phoneNumber varchar,"
                + "address varchar,"
                + "CONSTRAINT PK_Contact Primary KEY(phoneNumber,supplierId),"
                + "CONSTRAINT FK_Contact FOREIGN KEY (supplierId) references  Suppliers(supplierId) on delete cascade"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Contracts ("
                + "contractId INTEGER,"
                + "isDeliver Boolean,"
                + "CONSTRAINT PK_Contract Primary KEY(contractId),"
                + "CONSTRAINT FK_Contact FOREIGN KEY(contractId) references Suppliers(supplierId) on delete cascade"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";
        
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS DeliveryDays("
                + "contractId INTEGER,"
                + "Deliday varchar,"
                + "CONSTRAINT PK_DeliDays primary KEY(Deliday,contractId),"
                + "CONSTRAINT  FK_DeliDays FOREIGN KEY (contractId) references Contracts(contractId) on delete cascade"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Orders ("
                + "orderId INTEGER ,"
                + "branchId INTEGER ,"
                + "status varchar, "
                + "supplierId INTEGER ,"
                + "creationTime TIMESTAMP ,"
                + "deliveryDate TIMESTAMP,"
                + "CONSTRAINT PK_Orders Primary KEY(orderId),"
                + "CONSTRAINT FK_Orders FOREIGN KEY (supplierId) references Suppliers(supplierId) on delete no action,"
                + "CONSTRAINT FK_ScheduledOrder3 FOREIGN KEY (branchId) references Branch(branchId) on delete no action"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";
        
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Ranges ("
                + "rangeId INTEGER,"
                + "catalogItemId INTEGER ,"
                + "contractId INTEGER ,"
                + "minimum INTEGER ,"
                + "maximum INTEGER ,"
                + "price REAL,"
                + "CONSTRAINT PK_Ranges Primary KEY(rangeId,catalogItemId,contractId),"
                + "CONSTRAINT FK_Ranges FOREIGN KEY(contractId) references Contracts(contractId) on delete cascade ,"
                + "CONSTRAINT FK_Ranges2 FOREIGN key(catalogItemId,contractId) references CatalogItem(catalogItemId,contractId) on delete cascade"
                + ");";
        
        
        
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);

        sqlQ = "";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS LineCatalogItemInCart ("
                + "orderId INTEGER ,"
                + "catalogItemId INTEGER ,"
                + "amount INTEGER ,"
                + "priceAfterDiscount REAL ,"
                + "amountRecieved INTEGER ,"
                + "CONSTRAINT PK_LineCatalogItemInCart Primary KEY(orderId,catalogItemId),"
                + "CONSTRAINT FK_LineCatalogItemInCart FOREIGN KEY (orderId) references Orders(orderId)"
                + ");";
        
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";
        sqlQ = sqlQ + "CREATE INDEX rangeIndex on Ranges(rangeId);"; //TODO NOT WORKING
        stmt = con.createStatement();
        try{stmt.execute(sqlQ);}catch(Exception e) {};
        sqlQ = "";

        //tables for Inventory module

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Branch ("
                + "branchId INTEGER ,"
                + "description varchar ,"
                + "CONSTRAINT PK_Branch Primary KEY(branchID)"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS DamagedItem ("
                + "branchId INTEGER ,"
                + "itemId INTEGER ,"
                + "quantityDamaged INTEGER ,"
                + "CONSTRAINT PK_DamagedItem Primary KEY(branchID, itemId),"
                + "CONSTRAINT FK_DamagedItem FOREIGN KEY (branchId) references Branch(branchId)"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Inventory ("
                + "inventoryId INTEGER,"
                + "idCounter INTEGER ,"
                + "CONSTRAINT PK_Inventory Primary KEY(inventoryId)"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Item ("
                + "itemId INTEGER ,"
                + "description varchar ,"
                + "costPrice REAL ,"
                + "salePrice REAL ,"
                + "minimumQuantity INTEGER ,"
                + "weight REAL ,"
                + "category varchar ,"
                + "subCategory varchar ,"
                + "sub2Category varchar ,"
                + "manufacturer varchar ,"
                + "costCounter INTEGER ,"
                + "saleCounter INTEGER ,"
                + "CONSTRAINT PK_Item Primary KEY(itemId)"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS ItemStatus ("
                + "branchId INTEGER ,"
                + "itemId INTEGER ,"
                + "quantityShelf INTEGER ,"
                + "quantityStock INTEGER ,"
                + "CONSTRAINT PK_ItemStatus Primary KEY(branchId, itemId),"
                + "CONSTRAINT FK_ItemStatus FOREIGN KEY (branchId) references Branch(branchId),"
                + "CONSTRAINT FK_ItemStatus2 FOREIGN KEY (itemId) references Item(itemId)"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";
        

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS OldCostPrice("
                + "itemId INTEGER ,"
                + "counter INTEGER ,"
                + "price INTEGER ,"
                + "CONSTRAINT PK_OldCostPrice Primary KEY(itemId, counter),"
                + "CONSTRAINT FK_OldCostPrice FOREIGN KEY (itemId) references Item(itemId)"
                + ");";
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS OldSalePrice("
                + "itemId INTEGER ,"
                + "counter INTEGER ,"
                + "price INTEGER ,"
                + "CONSTRAINT PK_OldSalePrice Primary KEY(itemId, counter),"
                + "CONSTRAINT FK_OldSalePrice FOREIGN KEY (itemId) references Item(itemId)"
                + ");";
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        
        
        sqlQ = "";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS CatalogItem ("
                + "catalogItemId INTEGER ,"
                + "contractId INTEGER ,"
                + "itemId INTEGER,"
                + "price REAL,"
                + "description varchar(50),"
                + "CONSTRAINT PK_CatalogItem Primary KEY(catalogItemId,contractId),"
                + "CONSTRAINT FK_CatalogItem FOREIGN KEY (contractId) references Contracts(contractId) on delete cascade,"
                + "CONSTRAINT FK_CatalogItemToItem FOREIGN KEY (itemId) references Item(itemId) on delete cascade"
                + ");";
        
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        sqlQ = "";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS ScheduledOrder ("
                + "Sday INTEGER ,"
                + "supplierId INTEGER ,"
                + "catalogItemId INTEGER ,"
                + "amount INTEGER ,"
                + "branchId INTEGER,"
                + "CONSTRAINT PK_ScheduledOrder Primary KEY(Sday,supplierID,catalogItemId,branchId),"
                + "CONSTRAINT  FK_ScheduledOrder FOREIGN KEY (supplierId) references Suppliers(supplierId) on delete cascade,"
                + "CONSTRAINT  FK_ScheduledOrder2 FOREIGN KEY (catalogItemId, supplierId) references CatalogItem(catalogItemId, contractId) on delete cascade,"
                + "CONSTRAINT  FK_ScheduledOrder3 FOREIGN KEY (branchId) references Branch(branchId) on delete cascade" //TODO DOWN FROM COMMENT SEE IF ANY PROBLEMS
                + ");";
        stmt = con.createStatement();
        stmt.execute(sqlQ);
        createEmployeesDeliveries();
    }
    
    public void clean() throws SQLException {

        deleteDataBase();
    	String sql = "";
    	sql = "drop table OldSalePrice;";
    	Statement stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table OldCostPrice;";
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table ItemStatus;";
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table Item;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table Inventory;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table DamagedItem;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table LineCatalogItemInCart;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table Ranges;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table DeliveryDays;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table Contacts;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table Orders;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table CatalogItem;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table Contracts;" ;
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table ScheduledOrder;";
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table Suppliers;";
    	stmt = con.createStatement();
    	stmt.execute(sql);
    	
    	sql = "";
    	sql = "drop table Branch;";
    	stmt = con.createStatement();
    	stmt.execute(sql);


    	
    }

    public static void createEmployeesDeliveries()
    {
        try (Statement stmt = con.createStatement();) {
            createEmployees(con);
            createEmployeeConstraints(con);
            createEmployeeRoles(con);
            creatTruck(con);
            creatLocations(con);
            //creatOrders(con);
            createDrivers(con);
            creatDeliveries(con);
            createItemsForOrder(con);
            //creatOrdersForDelivery(con);
            //creatLocationsForDelivery(con);
            createShifts(con);
            createEmployeesShifts(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void creatTruck(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Trucks " +
                    "(ID VARCHAR(100) PRIMARY KEY NOT NULL," +
                    "MODEL           VARCHAR(100)    NOT NULL, " +
                    "NETO_WEIGHT         DOUBLE NOT NULL ," +
                    "MAX_WEIGHT         DOUBLE NOT NULL, " +
                    "ISUSED INT NOT NULL)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void creatLocations(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Locations " +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "NAME           VARCHAR(100)    NOT NULL, " +
                    "ADDRESS         VARCHAR(100) NOT NULL ," +
                    "TEL_NUMBER         VARCHAR(100) NOT NULL, "+
                    "CONTACT_NAME  VARCHAR(100) NOT NULL,"+
                    "SHIIPING_EREA VARCHAR(100) NOT NULL )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    public static void creatOrders(Connection conn)  {
//        try (Statement stmt = conn.createStatement();) {
//
//            String sql1 = "CREATE TABLE IF NOT EXISTS Orders" +
//                    "(ID INT PRIMARY KEY NOT NULL," +
//                    "SUPPLIER           VARCHAR(100)    NOT NULL, " +
//                    "TARGET_LOCATION         INT NOT NULL ," +
//                    "TOTAL_WEIGHT         DOUBLE NOT NULL,"+
//                    "FOREIGN KEY (TARGET_LOCATION) REFERENCES Locations(ID) ON DELETE Restrict )";
//            stmt.executeUpdate(sql1);
//
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void createDrivers(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Drivers" +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "License_Type VARCHAR (10)   NOT NULL, " +
                    "Expiration_Date DATE NOT NULL," +
                    "STATUS INT NOT NULL,"+
                    "FOREIGN KEY (ID) REFERENCES Employees (ID) ON DELETE CASCADE )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void creatDeliveries(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Deliveries" +
                    "(ID VARCHAR(100) PRIMARY KEY NOT NULL," +
                    "DELIVERY_DATE DATE    NOT NULL, " +
                    "DELIVER_TIME  TIME NOT NULL ," +
                    "DRIVER_ID INT, "+
                    "SUPPLIER_ID INT NOT NULL, " +
                    "TARGET_LOCATION INT NOT NULL,"+
                    "WEIGHT DOUBLE NOT NULL, "+
                    "TRUCK_ID VARCHAR (100) , "+
                    "ORDER_ID INT NOT NULL,"+
                    "STATUS VARCHAR (100) NOT NULL,"+
                    "FOREIGN KEY (SUPPLIER_ID) REFERENCES Suppliers(supplierId) ON DELETE RESTRICT ," +
                    "FOREIGN KEY (TARGET_LOCATION) REFERENCES Locations(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY(ORDER_ID) REFERENCES Orders(orderId))";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createItemsForOrder(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS ItemsForOrder " +
                    "(DELIVERY_ID VARCHAR(100)," +
                    "ORDER_ID INT NOT NULL," +
                    "ITEM INT NOT NULL, " +
                    "QUINTITY INT NOT NULL,"+
                    "PRIMARY KEY (DELIVERY_ID, ORDER_ID, ITEM),"+
                    "FOREIGN KEY (ORDER_ID) REFERENCES Orders(orderId) ," +
                    "FOREIGN KEY (DELIVERY_ID) REFERENCES Deliveries(ID) ON DELETE RESTRICT)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void creatOrdersForDelivery(Connection conn)  {
//        try (Statement stmt = conn.createStatement();) {
//
//            String sql1 = "CREATE TABLE IF NOT EXISTS OrdersForDelivery" +
//                    "(DELIVERY_ID VARCHAR(100)  NOT NULL," +
//                    "ORDER_ID INT  NOT NULL, "+
//                    "PRIMARY KEY (DELIVERY_ID, ORDER_ID),"+
//                    "FOREIGN KEY (DELIVERY_ID) REFERENCES Deliveries(ID) ON DELETE RESTRICT ,"+
//                    "FOREIGN KEY (ORDER_ID) REFERENCES Orders(ID) ON DELETE RESTRICT )";
//            stmt.executeUpdate(sql1);
//
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public static void creatLocationsForDelivery(Connection conn)  {
//        try (Statement stmt = conn.createStatement();) {
//
//            String sql1 = "CREATE TABLE IF NOT EXISTS LocationsForDelivery " +
//                    "(DELIVERY_ID VARCHAR(100)  NOT NULL," +
//                    "LOCATION_ID INT  NOT NULL, " +
//                    "PRIMARY KEY (DELIVERY_ID, LOCATION_ID),"+
//                    "FOREIGN KEY (DELIVERY_ID) REFERENCES Deliveries(ID) ON DELETE RESTRICT ,"+
//                    "FOREIGN KEY (LOCATION_ID) REFERENCES Locations(ID) ON DELETE RESTRICT )";
//
//
//            stmt.executeUpdate(sql1);
//
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void createEmployees(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Employees" +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "Name VARCHAR (20)   NOT NULL, " +
                    "Bank_Account INTEGER NOT NULL," +
                    "Start_Working_Date DATE NOT NULL," +
                    "Salary INTEGER NOT NULL," +
                    "Vacation_Days INTEGER NOT NULL)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createEmployeeRoles(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS EmployeesRoles" +
                    "(ID INT NOT NULL," +
                    "Role VARCHAR (20) NOT NULL," +
                    "PRIMARY KEY (ID,Role),"+
                    "FOREIGN KEY (ID) REFERENCES Employees(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createEmployeeConstraints(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS EmployeesConstraints" +
                    "(ID INT NOT NULL," +
                    "DayConstraint VARCHAR (20) NOT NULL, " +
                    "KindConstraint VARCHAR (20) NOT NULL," +
                    "PRIMARY KEY (ID,DayConstraint,KindConstraint),"+
                    "FOREIGN KEY (ID) REFERENCES Employees(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createShifts(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Shifts" +
                    "(Date DATE NOT NULL," +
                    "Kind VARCHAR (20) NOT NULL, " +
                    "ShiftManager BIT DEFAULT 0 NOT NULL,"+
                    "PRIMARY KEY (Date,Kind))";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createEmployeesShifts(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS EmployeesShifts" +
                    "(Date DATE NOT NULL," +
                    "Kind VARCHAR (20) NOT NULL, " +
                    "ID INTEGER NOT NULL, "+
                    "Role VARCHAR (20) NOT NULL,"+
                    "PRIMARY KEY (Date,Kind,ID),"+
                    "FOREIGN KEY (Date,Kind) REFERENCES Shifts(Date,Kind) ON DELETE CASCADE ," +
                    "FOREIGN KEY (ID) REFERENCES Employees(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void deleteDataBase() {
        try (Statement stmt = con.createStatement();) {


            String sql1 = "DROP TABLE ItemsForOrder";
            stmt.execute(sql1);
            sql1 = "DROP TABLE Deliveries";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Trucks";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Locations";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Drivers";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE EmployeesShifts";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Shifts";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE EmployeesConstraints";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE EmployeesRoles";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Employees";
            stmt.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CatalogItemDTO getCatalogItem(int catalogItemId, int contractId) throws SQLException {
        return this.catalogItemDAO.find(catalogItemId, contractId);
    }

    public void updateCatalogItem(int catalogItemId, int contractId, double price) throws SQLException {

        String sql = "UPDATE CatalogItem SET price = ? where catalogItemId = ? AND contractId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setDouble(1, price);
        pstmt.setInt(2, catalogItemId);
        pstmt.setInt(3, contractId);
        pstmt.executeUpdate();

    }

    public void deleteCatalogItem(int contractId, int catalogItemId) throws SQLException {
        String sql = "DELETE FROM CatalogItem " +
                "WHERE contractId = ? AND  catalogItemId = ?";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, contractId);
        stmp.setInt(2, catalogItemId);
        stmp.executeUpdate();

    }



    public ContactDTO getSpecificContact(int supplierId, String phoneNumber) throws SQLException {
        return this.contactDao.find(supplierId, phoneNumber);
    }

    public void updateContact(String phoneNumber, int supplierId, ContactDTO contactDTO) throws SQLException {
        String sql = "UPDATE Contacts SET phoneNumber = ? , firstName = ? ,lastName = ? , address = ? where supplierId = ? AND phoneNumber = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, phoneNumber);
        pstmt.setString(2, contactDTO.getFirstName());
        pstmt.setString(3, contactDTO.getLastName());
        pstmt.setString(4, contactDTO.getAddress());
        pstmt.setInt(5, supplierId);
        pstmt.setString(6, phoneNumber);
        pstmt.executeUpdate();
    }

    public void insertLineCatalogItem(LineCatalogItemDTO lineCatalogItemDTO, int orderId) throws SQLException {
        this.lineCatalogItemInCartDAO.insert(lineCatalogItemDTO, orderId);
    }

    public List<ContactDTO> getAllContactBySupplier(int supplierId) throws SQLException {

        return this.contactDao.findAllBySupplier(supplierId);
    }


    public ContractDTO getContract(int contractId) throws SQLException {

        return this.contractDAO.find(contractId);

    }


    public void updateContract(ContractDTO contractDTO) throws SQLException {
        String sql = "UPDATE Contracts SET isDeliver = ? where contractId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setBoolean(1, contractDTO.getIsDeliver());
        pstmt.setInt(2, contractDTO.getSupplierId());
        pstmt.executeUpdate();
        this.deliveryDaysDAO.deleteEveryThingByContract(contractDTO.getSupplierId());
        this.deliveryDaysDAO.insertEveryTingByContract(contractDTO);

    }

    public void updateDeliveryDaysByContract(int contractId) {

    }

    public DeliveryDaysDTO getAllDeliveryDaysByContract(int contractId) throws SQLException {
        return this.deliveryDaysDAO.findAllByContract(contractId);
    }

    public void insertDeliveryDays(DeliveryDaysDTO deliveryDaysDTO , int contractId) throws SQLException {
        this.deliveryDaysDAO.insert(deliveryDaysDTO,contractId);

    }

    public void deleteItemFromOrder(int catalogItemId, int orderId) throws SQLException {

        String sql = "DELETE FROM LineCatalogItemInCart " +
                "WHERE catalogItemId = ? AND orderId = ?";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, catalogItemId);
        stmp.setInt(2, orderId);
        stmp.executeUpdate();

    }

    public OrderDTO getOrderByID(int orderId) throws Exception {
        return this.orderDAO.find(orderId);
    }

    public synchronized void updateOrder(OrderDTO orderDTO) throws Exception {
        CartDTO cartDTO = orderDTO.getCart();
        for (LineCatalogItemDTO line : cartDTO.getLineItems()) {
        	try {lineCatalogItemInCartDAO.insert(line, orderDTO.getOrderId());continue;}catch(Exception e) {}
        	lineCatalogItemInCartDAO.updateLineCatalogItem(line,orderDTO.getOrderId());
		}
    }
    
    public CatalogDTO getCatalog(int supplierId) throws SQLException {
        List<CatalogItemDTO> catalogItemDTOS = this.catalogItemDAO.findAll(supplierId);
        return new CatalogDTO(catalogItemDTOS);
    }

    public List<OrderDTO> getSupplierOrders(int supplierId) throws Exception {
        List<OrderDTO> allOrders = this.orderDAO.findAll();
        List<OrderDTO> supplierOrders = new ArrayList<>();
        for (OrderDTO orderDTO : allOrders) {
            if (orderDTO.getSupplierId() == supplierId) supplierOrders.add(orderDTO);
        }
        return supplierOrders;
    }

    public int insertOrder(OrderDTO orderDTO) throws Exception {
        return this.orderDAO.insert(orderDTO);
    }

    public HashMap<Integer, List<Pair<RangeDTO, Double>>> getAllRangesByContract(int contractId) throws SQLException {
        return this.rangesDAODAO.findAll(contractId);
    }

    public List<Pair<RangeDTO, Double>> getAllRangeForCatalogItemId(int contractId, int catalogItemId) throws SQLException {
        HashMap<Integer, List<Pair<RangeDTO, Double>>> allRangesByContract = this.rangesDAODAO.findAll(contractId);
        List<Pair<RangeDTO, Double>> allRangesForCatalogItem = new ArrayList<>();
        for (Pair<RangeDTO, Double> pair : allRangesByContract.get(catalogItemId)) {
            allRangesForCatalogItem.add(pair);
        }
        return allRangesForCatalogItem;
    }

    public void deleteAllRangesByContractId(int contractId, int catalogItemId) throws SQLException {


        String sql = "DELETE FROM Ranges " +
                "WHERE catalogItemId = ? AND contractId = ?";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, catalogItemId);
        stmp.setInt(2, contractId);
        stmp.executeUpdate();

    }

  /*  public void updateRange(RangeDTO rangeDTO, ContractDTO contractDTO, double price) throws SQLException {
        String sql = "DELETE FROM LineCatalogItemInCart\n" +
                "WHERE catalogItemId = ? AND orderId = ?;";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, catalogItemId);
        stmp.setInt(2, orderId);
        stmp.executeUpdate();
    }
*/
    public ScheduledDTO getSpecificScheduled(int branchId, int day, int supplierId) throws Exception {

        List<ScheduledDTO> scheduledDTOS = this.scheduledDAO.findAll();
        for (ScheduledDTO scheduledDTO : scheduledDTOS) {
            if (scheduledDTO.getSupplierId() == supplierId && scheduledDTO.getDay().getValue() == day && scheduledDTO.getBranchId() == branchId) {
                return scheduledDTO;
            }
        }

        throw new Exception("Scheduled order not found by input you provied");
    }

    public List<ScheduledDTO> getAllScheduled() throws SQLException {
        return this.scheduledDAO.findAll();
    }

    public void deleteScheduledBySupplier(int supplierId) {

    }

    public SupplierDTO getSupplierById(int supplierId) throws SQLException {
        return this.supplierDAO.find(supplierId);
    }

    public void updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        String sql = "UPDATE Suppliers SET supplierId = ? ,supplierName = ?, bankAccountNumber = ? ,bilingOptions = ?  where supplierId = ? ";
        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, supplierDTO.getSupplierId());
        stmp.setString(2, supplierDTO.getName());
        stmp.setInt(3, supplierDTO.getBankAccountNumber());
        stmp.setString(4, supplierDTO.getBillingOption().name());
        stmp.setInt(5, supplierDTO.getSupplierId());
        stmp.executeUpdate();
        this.updateContract(supplierDTO.getContractDTO());
        for (ContactDTO contactDTO : supplierDTO.getContactDTOS()) {
            this.updateContact(contactDTO.getPhoneNumber(), supplierDTO.getSupplierId(), contactDTO);
        }

    }

    public List<SupplierDTO> getAllSuppliers() throws SQLException {
        return this.supplierDAO.findAll();
    }

    public void insertSupplier(Supplier supplier) throws SQLException {
        this.supplierDAO.insertSupplier(supplier);
    }

    public int getSupplierIdByOrder(int orderId) throws Exception {
//        List<Integer> supplierId = new ArrayList<>();
        String sql = "select supplierId from Orders where orderId = ? ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, orderId);
        ResultSet rs = pstmt.executeQuery();
//        while (rs.next()) {
//            int id = rs.getInt("supplierId");
//            supplierId.add(id);
//        }
        if(!rs.next()){
            throw new Exception("Order id does not exist");
        }
        else
        return rs.getInt("supplierId");
    }

    @SuppressWarnings("deprecation")
	public boolean getOrderByDateSupplier(int supplierId, int branchId, LocalDateTime deliveryDate) throws Exception {
        String sql = "select deliveryDate from Orders where supplierId = ? AND branchId = ? ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,supplierId);
        pstmt.setInt(2,branchId);
        ResultSet rs = pstmt.executeQuery();
        
        while(rs.next()) {
        	Timestamp ts = rs.getTimestamp("deliveryDate");
        	Timestamp del = Timestamp.valueOf(deliveryDate);
        	if(ts.getDay() != del.getDay() || ts.getMonth() != del.getMonth() || ts.getYear() != del.getYear() ) continue;
        	else {return true;}
        	
        }
        return false;
        
    }

    public List<OrderDTO> getAllOrderByBranchId(int barnchId) throws Exception {
        List<OrderDTO> allOrders = this.orderDAO.findAll();
        List<OrderDTO> orderByBranchId = new ArrayList<>();
        for (OrderDTO orderDTO : allOrders) {
            if (orderDTO.getBranchId() == barnchId)
                orderByBranchId.add(orderDTO);
        }
        return orderByBranchId;
    }

    public void insertScheduled(ScheduledDTO schedule) throws SQLException {
        this.scheduledDAO.insert(schedule);
    }

    /**
     * Get item description of specific item
     *
     * @param itemId The item ID
     * @return item description
     */
    public String getItemDescription(int itemId) throws Exception {
		
		
		  String sql = "select description from Item where itemId = ?";
		  PreparedStatement pstmt = con.prepareStatement(sql);
		  pstmt.setInt(1, itemId);
		  ResultSet rs = pstmt.executeQuery();
		  if(!rs.next()) throw new Exception("Item does not exist");
		  
		  return rs.getString("description");
    }

    public void insertContact(int supplierId, ContactDTO contactDTO) throws SQLException {
        this.contactDao.insert(contactDTO, supplierId);
    }

    public void deleteContact(String phoneNumber, int supplierId) throws SQLException {
        String sql = "DELETE FROM Contacts " +
                "WHERE PhoneNumber = ? AND supplierId = ?";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setString(1, phoneNumber);
        stmp.setInt(2, supplierId);
        stmp.executeUpdate();

    }

    public BranchDTO getBranchById(int branchId) throws SQLException {
	    return this.branchDAO.find(branchId);
    }

    public void createBranch(BranchDTO branch) throws SQLException {
        this.branchDAO.insert(branch);
    }


    public List<BranchDTO> getAllBranches() throws SQLException {
	    return this.branchDAO.findAll();
    }


    public void updateBranchDescription(int branchId, String description) throws SQLException {
	    this.branchDAO.updateDescription(branchId, description);
    }

    public DamagedControllerDTO getDamagedControllerForBranch(int branchId) throws SQLException{
	    return this.damagedItemDAO.findDamageController(branchId);
    }

    public void insertNewDamagedItem(int branchID,int itemId, int quantity) throws SQLException{
	    damagedItemDAO.insertDamagedItem(branchID,itemId, quantity);
    }

    public List<DamagedControllerDTO> getAllDamagedControllers() throws SQLException{
	    return damagedItemDAO.findAll();
    }

    public void updateExistingDamagedItem(int branchId, int itemId, int newQuantity) throws SQLException{
	    damagedItemDAO.updateAnItem( branchId,  itemId,  newQuantity);
    }

    public InventoryDTO getInventory() throws SQLException{
	    return inventoryDAO.find();
    }

    public void createInventory(InventoryDTO inventoryDTO) throws SQLException{
	    inventoryDAO.insert(inventoryDTO);
    }

    public void updateInventoryIdCounter(int idCounter) throws SQLException{
	    inventoryDAO.updateIdCounter(idCounter);
    }

    public ItemDTO getItem(int itemId) throws SQLException{
        return itemDAO.find(itemId);
    }

    public void addNewItem(ItemDTO itemDTO) throws SQLException{
	    itemDAO.insert(itemDTO);
    }

    public List<ItemDTO> getAllItems() throws SQLException{
	    return itemDAO.findAll();
    }

    public void updateAnItemWithoutOldPrices(ItemDTO itemDTO) throws SQLException{
        itemDAO.updateWithoutOldPrices(itemDTO);
    }

    public void updateCostPriceForItem(int itemId, double newPrice,double oldPrice) throws SQLException{
	    itemDAO.updateCostPrice( itemId,  newPrice,oldPrice);
    }

    public void updateSalePriceForItem(int itemId, double newPrice,double oldPrice) throws SQLException{
	    itemDAO.updateSalePrice(itemId,  newPrice,oldPrice);
    }

    public ItemStatusDTO getItemStatus(int branchId, int itemId) throws SQLException{
	    return itemStatusDAO.find(branchId, itemId);
    }

    public void addItemStatus(ItemStatusDTO itemStatusDTO) throws SQLException{
	    itemStatusDAO.insert(itemStatusDTO);
    }
    public List<LineCatalogItemDTO> getAllCatalogItemByOrder(int orderId) throws Exception {
        return this.lineCatalogItemInCartDAO.findAllByOrderId(orderId);
    }

    public List<ItemStatusDTO> getAllItemStatusByBranch(int branchId) throws SQLException{
	    return itemStatusDAO.findAllByBranch(branchId);
    }

    public void updateAnItemStatus(ItemStatusDTO itemStatusDTO) throws SQLException{
	    itemStatusDAO.updateAStatus(itemStatusDTO);
    }



    public void insertRange(RangeDTO rangeDTO, int contractId, int catalogItemId, double price) throws SQLException {
        this.rangesDAODAO.insert(rangeDTO, contractId, catalogItemId, price);
    }

    public void deleteSupplierById(int supplierId) throws SQLException {

        String sql = "DELETE FROM Suppliers " +
                "WHERE supplierId = ?";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, supplierId);
        stmp.executeUpdate();

    }

    public void insertCatalogItem(CatalogItemDTO catalogItemDTO, int contractId) throws SQLException {
        this.catalogItemDAO.insert(catalogItemDTO, contractId);
    }
    
    public void close() throws SQLException {
    	con.close();
    }

	public void deleteConstDelivery(int supplierId) throws SQLException {
		this.deliveryDaysDAO.deleteEveryThingByContract(supplierId);
		
	}

    public boolean isInventoryExist() throws SQLException {
        return this.inventoryDAO.isAlreadyExist();
    }

    public List<OrderDTO> getAllOrders() throws Exception {
        return this.orderDAO.findAll();
    }

    public synchronized void updateOrderStatus(int supplierId, int branchId, int day,int year, String status) {
    	try  {
    		int orderId = getOrderIdBy(supplierId, branchId, day, year);
    		String sql = "UPDATE Orders SET status = ? where orderId = ? ";
    		PreparedStatement stmp = con.prepareStatement(sql);
    		stmp.setString(1, status);
    		stmp.setInt(2, orderId);
    		stmp.executeUpdate();

    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
	
	public boolean isThereScheduledOrderForNextDay(int supplierId,int branchId, int day) {
		String sql = "select branchId,supplierId,Sday from ScheduledOrder where supplierId = ? AND branchId = ? AND Sday = ? ";
		try  {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, supplierId);
			pstmt.setInt(2, branchId);
			pstmt.setInt(3, day);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public int getOrderIdBy(int supplierId, int branchId, int day,int year) {
    	String sqlSelect = "select orderId, deliveryDate from Orders where supplierId = ? AND branchId = ? ";
    	try  {
    		PreparedStatement pstmt = con.prepareStatement(sqlSelect);
    		pstmt.setInt(1, supplierId);
    		pstmt.setInt(2, branchId);
    		ResultSet rs = pstmt.executeQuery();

    		while(rs.next()) {
    			LocalDateTime l = rs.getTimestamp("deliveryDate").toLocalDateTime();
    			if (l.getDayOfYear() != day || year != l.getYear()) continue;
    			return rs.getInt("orderId");
    		}
    		return -1;
    	}catch (Exception e) {
    		e.printStackTrace();
			return -1;
		}
	}

	public List<OrderDTO> getAllOpenOrdersByBranch(int branchId) throws Exception {
		String sqlSelect = "select orderId from Orders where branchId = ? AND status = ? ";
    	try  {
    		PreparedStatement pstmt = con.prepareStatement(sqlSelect);
    		pstmt.setInt(1, branchId);
    		pstmt.setString(2, "OPEN");
    		ResultSet rs = pstmt.executeQuery();
    		List<OrderDTO> orders = new ArrayList<OrderDTO>();
    		while(rs.next()) {
    			orders.add(getOrderByID(rs.getInt("orderId")));
    		}
    		return orders;
    	}catch (Exception e) {
			throw new Exception("Error Occured " + e.getMessage());
		}
	}

	public List<OrderDTO> getAllOrdersByBranch(int branchId) throws Exception {
		String sqlSelect = "select orderId from Orders where branchId = ? ";
    	try  {
    		PreparedStatement pstmt = con.prepareStatement(sqlSelect);
    		pstmt.setInt(1, branchId);
    		ResultSet rs = pstmt.executeQuery();
    		List<OrderDTO> orders = new ArrayList<OrderDTO>();
    		while(rs.next()) {
    			orders.add(getOrderByID(rs.getInt("orderId")));
    		}
    		return orders;
    	}catch (Exception e) {
			throw new Exception("Error Occured " + e.getMessage());
		}
	}

	public void updateAnOrderStatusById(int orderId, String status) {
		String sqlSelect = "UPDATE Orders SET status = ? where orderId = ? ";
    	try  {
    		PreparedStatement pstmt = con.prepareStatement(sqlSelect);
    		pstmt.setString(1, status);
    		pstmt.setInt(2, orderId);
    		pstmt.executeUpdate();
    	}catch (Exception e) {}
		
	}

	public void updateAmountRecieved(int orderId, int catalogItemId, int amount) throws Exception {
		String sqlSelect = "UPDATE LineCatalogItemInCart SET amountRecieved = ? where orderId = ? AND catalogItemId = ? ";
		PreparedStatement pstmt = con.prepareStatement(sqlSelect);
		pstmt.setInt(1, amount);
		pstmt.setInt(2, orderId);
		pstmt.setInt(3, catalogItemId);
		pstmt.executeUpdate();
	}
}
