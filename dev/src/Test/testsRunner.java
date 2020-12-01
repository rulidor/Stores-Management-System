//package test;
//
//import org.junit.internal.TextListener;
//import org.junit.runner.JUnitCore;
//
//public class testsRunner {
//
//    public static void main(String[] args)
//    {
//        try{
//            JUnitCore junit = new JUnitCore();
//            junit.addListener(new TextListener(System.out));
//            junit.run(InventoryTest.class);
//            junit.run(UserControllerTest.class);
//        }catch (Exception e) {
//            if(e != null)
//                System.out.println(e.getMessage());
//        }
//
//
//
//
//
////        Result result1 = JUnitCore.runClasses(InventoryTest.class);
////        Result result2 = JUnitCore.runClasses(UserControllerTest.class);
////        System.out.println("InventoryController: Test running: "+result1.getRunCount());
////        for(Failure failure : result1.getFailures())
////            System.out.println(failure.toString());
////
////        System.out.println(result1.wasSuccessful());
////
////        System.out.println("UserController: Test running: "+result2.getRunCount());
////        for(Failure failure : result2.getFailures())
////            System.out.println(failure.toString());
////
////        System.out.println(result2.wasSuccessful());
//
//    }
//}
// package test;

// import org.junit.internal.TextListener;
// import org.junit.runner.JUnitCore;

// public class testsRunner {

//     public static void main(String[] args)
//     {
//         try{
//             JUnitCore junit = new JUnitCore();
//             junit.addListener(new TextListener(System.out));
//             junit.run(InventoryTest.class);
//             junit.run(UserControllerTest.class);
//         }catch (Exception e) {
//             if(e != null)
//                 System.out.println(e.getMessage());
//         }





// //        Result result1 = JUnitCore.runClasses(InventoryTest.class);
// //        Result result2 = JUnitCore.runClasses(UserControllerTest.class);
// //        System.out.println("InventoryController: Test running: "+result1.getRunCount());
// //        for(Failure failure : result1.getFailures())
// //            System.out.println(failure.toString());
// //
// //        System.out.println(result1.wasSuccessful());
// //
// //        System.out.println("UserController: Test running: "+result2.getRunCount());
// //        for(Failure failure : result2.getFailures())
// //            System.out.println(failure.toString());
// //
// //        System.out.println(result2.wasSuccessful());

//     }
// }
