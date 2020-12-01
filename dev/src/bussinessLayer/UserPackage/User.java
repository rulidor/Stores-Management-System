package bussinessLayer.UserPackage;

import bussinessLayer.InventoryPackage.Inventory;

public class User {

    private int id;
    private String password;
    private Inventory inventory;
    private boolean isLoggedIn;

    public User(int id, String password, Inventory inventory) {
        this.id = id;
        this.password = password;
        this.inventory = inventory;
        this.isLoggedIn = false;
    }
    public User(int id, String password) {
        this.id = id;
        this.password = password;
        this.isLoggedIn = false;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
