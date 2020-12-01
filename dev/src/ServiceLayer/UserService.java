package ServiceLayer;

import bussinessLayer.UserPackage.UserController;

public class UserService {

    private UserController userController;

    public UserService() {
        this.userController = UserController.getInstance();
    }

    public Response register(String pass) {
        int userId;
        try {
            userId=this.userController.register(pass);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Registered successfully. User id: "+userId);
        return response;
    }

    public Response login(int id, String pass){
        try {
            this.userController.login(id, pass);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Logged in successfully");
        return response;
    }

}
