//package test;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//
//import Data.Data;
//import bussinessLayer.SupplierPackage.*;
//import bussinessLayer.OrderPackage.*;
//
//import ServiceLayer.OrderService;
//import ServiceLayer.SupplierService;
//
//
//public class TestClass {
//
//    private static SupplierService supService = SupplierService.getInstance();
//    private static OrderService oService = OrderService.getInstance();
//
//    @Before
//    public void setup() {
//        supService.loadFirstSuppliers();
//    }
//
//    @After
//    public void clean() {
//    	Data.clean();
//    }
//
//    @Test
//    public void createOrder() {
//        List<Order> orders = Data.getOrders();
//        int ordersSize = orders.size();
//        oService.createAnOrder(123456);
//        assertEquals("create order wrong", ordersSize + 1, orders.size());
//    }
//
//    @Test
//    public void creatSupplier() {
//        List<Supplier> suppliers = Data.getSuppliers();
//        int suppliersSize = suppliers.size();
//        supService.AddSupplier("d", 0, 1, "EOM30", true);
//        assertEquals("Size of suppliers wrong", suppliersSize + 1, suppliers.size());
//    }
//
//    @Test
//    public void addItemToCatalog() {
//        supService.AddSupplier("d", 0, 1, "EOM30", true);
//        int catalogSize = 0;
//        try {
//            catalogSize = Data.getSupplierById(0).getContract().getCatalog().getItems().size();
//            supService.addCatalogItemToCatalogInContract(0, 1, 10, 6);
//            assertEquals("Size of catalogItem wrong", catalogSize + 1, Data.getSupplierById(0).getContract().getCatalog().getItems().size());
//        } catch (Exception e) {
//        }
//
//    }
//
//    @Test
//    public void RemoveItemFromCatalog() {
//        try {
//            addItemToCatalog();
//            int catalogSize = Data.getSupplierById(0).getContract().getCatalog().getItems().size();
//            supService.deleteCatalogItemFromCatlogInContract(0, 10);
//            assertEquals("Size of catalogItem wrong", catalogSize - 1, Data.getSupplierById(0).getContract().getCatalog().getItems().size());
//        } catch (Exception e) {
//
//        }
//    }
//
//    @Test
//    public void IsExist() {
//        try {
//            supService.AddSupplier("d", 0, 1, "EOM30", true);
//            assertEquals(supService.isSupplierExist(0).getMessage(), "Done");
//            supService.removeSupplier(0);
//            assertEquals(supService.isSupplierExist(0).isErrorOccured(), true);
//        } catch (Exception e) {
//
//        }
//
//
//    }
//
//    @Test
//    public void removeSupplier() {
//        List<Supplier> suppliers = Data.getSuppliers();
//        int suppliersSize = suppliers.size();
//        supService.AddSupplier("d", 0, 1, "EOM30", true);
//        suppliersSize = suppliers.size();
//        supService.removeSupplier(0);
//        suppliers = Data.getSuppliers();
//        assertEquals("Size of suppliers wrong", suppliersSize - 1, suppliers.size());
//    }
//
//    @Test
//    public void addItemToCart() {
//        Cart cart = new Cart();
//        try {
//            int cartSize = cart.getItemsToDelivery().size();
//            cart.addItemToCart(Data.getSupplierById(123456).getCatalogItem(10), 10, 10);
//            assertEquals("add item to cart wrong", cart.getItemsToDelivery().size() - 1, cartSize);
//        } catch (Exception e) {
//        }
//    }
//
//    @Test
//    public void removeFromCart() {
//        Cart cart = new Cart();
//        try {
//            cart.addItemToCart(Data.getSupplierById(123456).getCatalogItem(10), 10, 10);
//            int cartSize = cart.getItemsToDelivery().size();
//            cart.removeFromCart(10);
//            assertEquals("add item to cart wrong", cart.getItemsToDelivery().size() + 1, cartSize);
//        } catch (Exception e) {
//        }
//    }
//
//    @Test
//    public void sendOrder() {
//        int orderId = oService.createAnOrder(123456).getObj();
//        oService.addItemToCart(orderId, 10, 10);
//        String initial = oService.getOrderDetails(orderId).getObj().getOrderStatus();
//        assertEquals("OPEN", initial);
//        oService.sendOrder(orderId);
//        assertEquals("INPROGRESS", oService.getOrderDetails(orderId).getObj().getOrderStatus());
//    }
//
//    @Test
//    public void endOrder() {
//        int orderId = oService.createAnOrder(123456).getObj();
//        oService.addItemToCart(orderId, 10, 10);
//        oService.sendOrder(orderId);
//        assertEquals("INPROGRESS", oService.getOrderDetails(orderId).getObj().getOrderStatus());
//        oService.endOrder(orderId);
//        assertEquals("COMPLETE", oService.getOrderDetails(orderId).getObj().getOrderStatus());
//    }
//}
// package test;

// import static org.junit.Assert.assertEquals;

// import java.util.List;

// import org.junit.After;
// import org.junit.Before;
// import org.junit.Test;


// import Data.Data;
// import bussinessLayer.SupplierPackage.*;
// import bussinessLayer.OrderPackage.*;

// import ServiceLayer.OrderService;
// import ServiceLayer.SupplierService;


// public class TestClass {

//     private static SupplierService supService = SupplierService.getInstance();
//     private static OrderService oService = OrderService.getInstance();

//     @Before
//     public void setup() {
//         supService.loadFirstSuppliers();
//     }
    
//     @After
//     public void clean() {
//     	Data.clean();
//     }

//     @Test
//     public void createOrder() {
//         List<Order> orders = Data.getOrders();
//         int ordersSize = orders.size();
//         oService.createAnOrder(123456);
//         assertEquals("create order wrong", ordersSize + 1, orders.size());
//     }

//     @Test
//     public void creatSupplier() {
//         List<Supplier> suppliers = Data.getSuppliers();
//         int suppliersSize = suppliers.size();
//         supService.AddSupplier("d", 0, 1, "EOM30", true);
//         assertEquals("Size of suppliers wrong", suppliersSize + 1, suppliers.size());
//     }

//     @Test
//     public void addItemToCatalog() {
//         supService.AddSupplier("d", 0, 1, "EOM30", true);
//         int catalogSize = 0;
//         try {
//             catalogSize = Data.getSupplierById(0).getContract().getCatalog().getItems().size();
//             supService.addCatalogItemToCatalogInContract(0, 1, 10, 6);
//             assertEquals("Size of catalogItem wrong", catalogSize + 1, Data.getSupplierById(0).getContract().getCatalog().getItems().size());
//         } catch (Exception e) {
//         }

//     }

//     @Test
//     public void RemoveItemFromCatalog() {
//         try {
//             addItemToCatalog();
//             int catalogSize = Data.getSupplierById(0).getContract().getCatalog().getItems().size();
//             supService.deleteCatalogItemFromCatlogInContract(0, 10);
//             assertEquals("Size of catalogItem wrong", catalogSize - 1, Data.getSupplierById(0).getContract().getCatalog().getItems().size());
//         } catch (Exception e) {

//         }
//     }

//     @Test
//     public void IsExist() {
//         try {
//             supService.AddSupplier("d", 0, 1, "EOM30", true);
//             assertEquals(supService.isSupplierExist(0).getMessage(), "Done");
//             supService.removeSupplier(0);
//             assertEquals(supService.isSupplierExist(0).isErrorOccured(), true);
//         } catch (Exception e) {

//         }


//     }

//     @Test
//     public void removeSupplier() {
//         List<Supplier> suppliers = Data.getSuppliers();
//         int suppliersSize = suppliers.size();
//         supService.AddSupplier("d", 0, 1, "EOM30", true);
//         suppliersSize = suppliers.size();
//         supService.removeSupplier(0);
//         suppliers = Data.getSuppliers();
//         assertEquals("Size of suppliers wrong", suppliersSize - 1, suppliers.size());
//     }

//     @Test
//     public void addItemToCart() {
//         Cart cart = new Cart();
//         try {
//             int cartSize = cart.getItemsToDelivery().size();
//             cart.addItemToCart(Data.getSupplierById(123456).getCatalogItem(10), 10, 10);
//             assertEquals("add item to cart wrong", cart.getItemsToDelivery().size() - 1, cartSize);
//         } catch (Exception e) {
//         }
//     }

//     @Test
//     public void removeFromCart() {
//         Cart cart = new Cart();
//         try {
//             cart.addItemToCart(Data.getSupplierById(123456).getCatalogItem(10), 10, 10);
//             int cartSize = cart.getItemsToDelivery().size();
//             cart.removeFromCart(10);
//             assertEquals("add item to cart wrong", cart.getItemsToDelivery().size() + 1, cartSize);
//         } catch (Exception e) {
//         }
//     }

//     @Test
//     public void sendOrder() {
//         int orderId = oService.createAnOrder(123456).getObj();
//         oService.addItemToCart(orderId, 10, 10);
//         String initial = oService.getOrderDetails(orderId).getObj().getOrderStatus();
//         assertEquals("OPEN", initial);
//         oService.sendOrder(orderId);
//         assertEquals("INPROGRESS", oService.getOrderDetails(orderId).getObj().getOrderStatus());
//     }

//     @Test
//     public void endOrder() {
//         int orderId = oService.createAnOrder(123456).getObj();
//         oService.addItemToCart(orderId, 10, 10);
//         oService.sendOrder(orderId);
//         assertEquals("INPROGRESS", oService.getOrderDetails(orderId).getObj().getOrderStatus());
//         oService.endOrder(orderId);
//         assertEquals("COMPLETE", oService.getOrderDetails(orderId).getObj().getOrderStatus());
//     }
// }
