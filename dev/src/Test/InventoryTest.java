//package test;
//
//import bussinessLayer.InventoryPackage.Inventory;
//import bussinessLayer.InventoryPackage.Item;
//import org.junit.Before;
//import org.junit.Test;
////import org.junit.jupiter.api.Test;
//import java.util.Map;
//import static org.junit.Assert.assertTrue;
////import static org.junit.jupiter.api.Assertions.assertTrue;
////import static org.junit.jupiter.api.Assertions.fail;
//public class InventoryTest {
//
//    private Inventory inventory;
//    private Item item1;
//    private Item item2;
//
//    @Before
//    public void setUp(){
//        inventory = new Inventory();
//
//    }
//
//
//    @Test
//    public void testAddItem() {
//        this.inventory.addItem("milk",1,2,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        assertTrue(inventory.getItems().get(1).getDescription().equals("milk"));
//        assertTrue(inventory.getItems().get(1).getFeatures().getManufacturer().equals("tnuva"));
//    }
//
//    @Test
//    public void testCounterId() {
//        this.inventory.addItem("milk",1,2,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        assertTrue(inventory.getIdCounter() == 1);
//    }
//
//    @Test
//    public void testEditMinimumQuantity()  {
//        this.inventory.addItem("milk",1,2,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        assertTrue(inventory.getItems().get(1).getMinimumQuantity()==4);
//        try {
//            inventory.editMinimumQuantity(1,10);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(inventory.getItems().get(1).getMinimumQuantity()==10);
//    }
//
//
//    @Test
//    public void testEditShelfQuantity  ()  {
//        this.inventory.addItem("milk",1,2,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        assertTrue(inventory.getItems().get(1).getQuantityShelf()==1);
//        try {
//            inventory.editShelfQuantity(1,20);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(inventory.getItems().get(1).getQuantityShelf()==21);
//    }
//
//    @Test
//    public void testEditStockQuantity  () {
//        this.inventory.addItem("milk",1,15,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        assertTrue(inventory.getItems().get(1).getQuantityStock()==15);
//        try {
//            inventory.editStockQuantity(1,-5);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(inventory.getItems().get(1).getQuantityStock()==10);
//    }
//
//    @Test
//    public void testCancelCard  ()  {
//        this.inventory.addItem("milk",1,15,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        assertTrue(inventory.getItems().get(1).getQuantityShelf()==1);
//        try {
//            inventory.cancelCard(1,6);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(inventory.getItems().get(1).getQuantityShelf()==7);
//    }
//
//    @Test
//    public void testUpdateItemCostPrice  ()  {
//        this.inventory.addItem("milk",1,15,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        assertTrue(inventory.getItems().get(1).getCostPrice()==3);
//        try {
//            inventory.updateItemCostPrice(1,6);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(inventory.getItems().get(1).getCostPrice()==6);
//        assertTrue(inventory.getItems().get(1).getOldCostPrices().get(0)==3);
//    }
//
//    @Test
//    public void testUpdateItemSalePrice  ()  {
//        this.inventory.addItem("milk",1,15,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        assertTrue(inventory.getItems().get(1).getSalePrice()==4);
//        try {
//            inventory.updateItemSalePrice(1,6);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertTrue(inventory.getItems().get(1).getSalePrice()==6);
//        assertTrue(inventory.getItems().get(1).getOldSalePrices().get(0)==4);
//    }
//
//
//    @Test
//    public void testUpdateDamagedItem  ()  {
//        this.inventory.addItem("milk",10,15,3,4,"branch",
//                4,4,"cat","sub","sub2", "tnuva");
//        try {
//            inventory.updateDamagedItem(1, 6);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        assertTrue(inventory.getDamagedController().getQuantityById().get(1) == 6);
//    }
//
//    @Test
//    public void testToOrderReport ()  {
//        this.inventory.addItem("milk",10,15,3,4,"branch",
//                30,4,"cat","sub","sub2", "tnuva");
//        Map<Integer,Integer> warnings = inventory.generateWarningReport();
//        assertTrue(warnings.get(1) == 25);
//    }
//
//}
// package test;

// import bussinessLayer.InventoryPackage.Inventory;
// import bussinessLayer.InventoryPackage.Item;
// import org.junit.Before;
// import org.junit.Test;
// //import org.junit.jupiter.api.Test;
// import java.util.Map;
// import static org.junit.Assert.assertTrue;
// //import static org.junit.jupiter.api.Assertions.assertTrue;
// //import static org.junit.jupiter.api.Assertions.fail;
// public class InventoryTest {

//     private Inventory inventory;
//     private Item item1;
//     private Item item2;

//     @Before
//     public void setUp(){
//         inventory = new Inventory();

//     }


//     @Test
//     public void testAddItem() {
//         this.inventory.addItem("milk",1,2,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         assertTrue(inventory.getItems().get(1).getDescription().equals("milk"));
//         assertTrue(inventory.getItems().get(1).getFeatures().getManufacturer().equals("tnuva"));
//     }

//     @Test
//     public void testCounterId() {
//         this.inventory.addItem("milk",1,2,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         assertTrue(inventory.getIdCounter() == 1);
//     }

//     @Test
//     public void testEditMinimumQuantity()  {
//         this.inventory.addItem("milk",1,2,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         assertTrue(inventory.getItems().get(1).getMinimumQuantity()==4);
//         try {
//             inventory.editMinimumQuantity(1,10);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         assertTrue(inventory.getItems().get(1).getMinimumQuantity()==10);
//     }


//     @Test
//     public void testEditShelfQuantity  ()  {
//         this.inventory.addItem("milk",1,2,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         assertTrue(inventory.getItems().get(1).getQuantityShelf()==1);
//         try {
//             inventory.editShelfQuantity(1,20);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         assertTrue(inventory.getItems().get(1).getQuantityShelf()==21);
//     }

//     @Test
//     public void testEditStockQuantity  () {
//         this.inventory.addItem("milk",1,15,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         assertTrue(inventory.getItems().get(1).getQuantityStock()==15);
//         try {
//             inventory.editStockQuantity(1,-5);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         assertTrue(inventory.getItems().get(1).getQuantityStock()==10);
//     }

//     @Test
//     public void testCancelCard  ()  {
//         this.inventory.addItem("milk",1,15,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         assertTrue(inventory.getItems().get(1).getQuantityShelf()==1);
//         try {
//             inventory.cancelCard(1,6);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         assertTrue(inventory.getItems().get(1).getQuantityShelf()==7);
//     }

//     @Test
//     public void testUpdateItemCostPrice  ()  {
//         this.inventory.addItem("milk",1,15,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         assertTrue(inventory.getItems().get(1).getCostPrice()==3);
//         try {
//             inventory.updateItemCostPrice(1,6);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         assertTrue(inventory.getItems().get(1).getCostPrice()==6);
//         assertTrue(inventory.getItems().get(1).getOldCostPrices().get(0)==3);
//     }

//     @Test
//     public void testUpdateItemSalePrice  ()  {
//         this.inventory.addItem("milk",1,15,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         assertTrue(inventory.getItems().get(1).getSalePrice()==4);
//         try {
//             inventory.updateItemSalePrice(1,6);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         assertTrue(inventory.getItems().get(1).getSalePrice()==6);
//         assertTrue(inventory.getItems().get(1).getOldSalePrices().get(0)==4);
//     }


//     @Test
//     public void testUpdateDamagedItem  ()  {
//         this.inventory.addItem("milk",10,15,3,4,"branch",
//                 4,4,"cat","sub","sub2", "tnuva");
//         try {
//             inventory.updateDamagedItem(1, 6);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         assertTrue(inventory.getDamagedController().getQuantityById().get(1) == 6);
//     }

//     @Test
//     public void testToOrderReport ()  {
//         this.inventory.addItem("milk",10,15,3,4,"branch",
//                 30,4,"cat","sub","sub2", "tnuva");
//         Map<Integer,Integer> warnings = inventory.generateWarningReport();
//         assertTrue(warnings.get(1) == 25);
//     }

// }
