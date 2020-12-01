// package test;


// import bussinessLayer.UserPackage.User;
// import bussinessLayer.UserPackage.UserController;
// import org.junit.Before;
// import org.junit.Test;
// //import org.junit.jupiter.api.BeforeEach;
// //import org.junit.Test;
// //import org.junit.jupiter.api.Test;

// import static org.junit.Assert.assertTrue;
// //import static org.junit.jupiter.api.Assertions.fail;
// public class UserControllerTest {
//     public UserController userController= UserController.getInstance();
//     public User user1;
//     public User user2;


//     @Before
//     public void setUp(){
//         user1 = new User(1, "123");
//         user2 = new User(2, "456");

//     }


//     @Test
//     public void testRegister() {
//         Exception exception1 = new Exception();
//         Exception exception2 = new Exception();

//         try {
//             userController.register("");
//         }
//         catch (Exception e){
//             exception1 = e;
//         }
//         assertTrue (exception1.getMessage().equals("Cannot register with empty password"));
//         try {
//             userController.register("123");
//         }
//         catch (Exception e){
//             exception2 = e;
//         }
//         assertTrue (exception2.getMessage()==null);
//         assertTrue(userController.getIdCounter()==1);
//         assertTrue(userController.getUsers().get(1).getPassword().equals("123"));

//     }


// }
