package ServiceLayer;

import java.sql.SQLException;
import java.util.List;

import DataAccessLaye.Repo;
import bussinessLayer.DTOPackage.InventoryDTO;
import bussinessLayer.DTOPackage.ItemDTO;
import bussinessLayer.InventoryPackage.Inventory;
public class InventoryService {

    private Inventory inventory;


    public InventoryService() {
        this.inventory = Inventory.getInstance();
    }

    public void initialInventoryInDB() throws SQLException {
        boolean isAlreadyExist = false;
        isAlreadyExist = Repo.getInstance().isInventoryExist();
        if(isAlreadyExist == true)
            return;
        try {
            this.inventory.initialInventoryInDB();
        } catch (SQLException throwables) {
            throw throwables;
        }
    }

    public double getItemWeight(int itemId) throws SQLException {
        try {
            return this.inventory.getItemWeight(itemId);
        }
        catch (Exception e) {
            throw e;
        }
    }



    public void update() throws SQLException {
        InventoryDTO inventoryDTO = Repo.getInstance().getInventory();
        inventoryDTO.updateFromDTO();
    }

    public Response addItem(String description, double costPrice,
            double salePrice, String position, int minimumQuantity, double weight, String category, String subCategory,
            String sub2Category, String manufacturer) {
        int itemId = -1;
        try {
            update();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            itemId = this.inventory.addItem(description, costPrice, salePrice, position, minimumQuantity, weight, category, subCategory, sub2Category, manufacturer);
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("New item was added, with id: " + itemId);
        return response;
    }

    public Response editMinimumQuantity(int itemId, int quantity) {
        try {
            update();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            this.inventory.editMinimumQuantity(itemId, quantity);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Minimum quantity was edited");
        return response;
    }

    public Response updateItemDescription(int itemId, String description) {
        try {
            update();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            this.inventory.editItemDescription(itemId, description);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Item description was edited");
        return response;
    }


    public Response updateItemCostPrice(int itemId, double newPrice) {
        try {
            update();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            this.inventory.updateItemCostPrice(itemId, newPrice);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Cost price was updated");
        return response;
    }

    public Response updateItemSalePrice(int itemId, double newPrice) {
        try {
            update();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            this.inventory.updateItemSalePrice(itemId, newPrice);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Sale price was updated");
        return response;
    }

	public ResponseT<List<ItemDTO>> getItemsList() {
        try {
            update();
        }
        catch (Exception e) {
            return new ResponseT<List<ItemDTO>>(e.getMessage());
        }
		try{
            return new ResponseT<List<ItemDTO>>(Repo.getInstance().getAllItems());
        } catch(Exception e){
            return new ResponseT<List<ItemDTO>>(e.getMessage());
        }
	}
}
