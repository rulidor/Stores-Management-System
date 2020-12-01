package bussinessLayer.UserPackage;


import java.util.HashMap;
import java.util.Map;

public class UserController {
    // static variable single_instance of type Singleton
    private static UserController single_instance = null;

    // variable of type String
    private Map<Integer, bussinessLayer.UserPackage.User> users;
    private int idCounter;

    // private constructor restricted to this class itself
    private UserController() {
        this.users = new HashMap<>();
        this.idCounter = 0;
    }

    // static method to create instance of Singleton class
    public static UserController getInstance() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new UserController();
        }
        return single_instance;
    }

    public Map<Integer, bussinessLayer.UserPackage.User> getUsers() {
        return users;
    }

    public void setUsers(Map<Integer, bussinessLayer.UserPackage.User> users) {
        this.users = users;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }

    public int register(String password) throws Exception {
        if (password.length() == 0) {
            throw new Exception("Cannot register with empty password");
        }
        this.idCounter++;
        this.users.put(idCounter, new bussinessLayer.UserPackage.User(idCounter, password));
        return this.idCounter;
    }

    public void login(int id, String password) throws Exception {
        if (!this.users.keySet().contains(id)) {
            throw new Exception("User was not found");
        }
        if (!this.users.get(id).getPassword().equals(password)) {
            throw new Exception("Wrong password");
        }
        this.users.get(id).setLoggedIn(true);
    }

}

