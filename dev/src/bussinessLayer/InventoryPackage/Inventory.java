package bussinessLayer.InventoryPackage;


import DataAccessLaye.Repo;
import bussinessLayer.DTOPackage.InventoryDTO;
import bussinessLayer.DTOPackage.ItemDTO;
import bussinessLayer.DTOPackage.ItemFeaturesDTO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Inventory {
    // static variable single_instance of type Singleton
    private static Inventory single_instance = null;


    private Map<Integer, bussinessLayer.InventoryPackage.Item> items;
    private int idCounter;

    private Inventory() {
        this.items = new HashMap<>();
        this.idCounter = 0;

    }

    public void initialInventoryInDB() throws SQLException {
        try {
            Repo.getInstance().createInventory(new InventoryDTO(new HashMap<Integer, ItemDTO>(), 0));
        } catch (SQLException throwables) {
            throw throwables;
        }
    }

    // static method to create instance of Singleton class
    public static Inventory getInstance() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new Inventory();
        }
        return single_instance;
    }

    public Map<Integer, ItemDTO> getItemDTOsMap() throws SQLException {
        InventoryDTO inventoryDTO = Repo.getInstance().getInventory();
        return inventoryDTO.getItemsDTO();
    }

    //user getItemsDTO indtead!
    public Map<Integer, bussinessLayer.InventoryPackage.Item> getItems() {
        return items;
    }

    public void setItems(Map<Integer, bussinessLayer.InventoryPackage.Item> items) {
        this.items = items;
    }

    public int addItem(String description, double costPrice, double salePrice, String position,
                        int minimumQuantity,
                        double weight, String category, String subCategory, String sub2Category, String manufacturer) throws SQLException {
        //this.idCounter++;
        this.idCounter = getItemsCounter() + 1;
        this.items.put(idCounter, new bussinessLayer.InventoryPackage.Item(idCounter, description, costPrice, salePrice, position,
        minimumQuantity, new ItemFeatures(idCounter, weight, category, subCategory, sub2Category, manufacturer)));
        ItemFeaturesDTO itemFeaturesDTO = new ItemFeaturesDTO(idCounter, weight,category,subCategory,sub2Category,manufacturer);
        Repo.getInstance().addNewItem(new ItemDTO(idCounter, description,costPrice,salePrice,new ArrayList<>(), new ArrayList<>(),  minimumQuantity, itemFeaturesDTO));
        Repo.getInstance().updateInventoryIdCounter(idCounter);
        return idCounter;
    }

    private int getItemsCounter() throws SQLException {
        List<ItemDTO> itemDTOS = Repo.getInstance().getAllItems();
        return itemDTOS.size();
    }

    public void editMinimumQuantity(int itemId, int quantity) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        //this.items.get(itemId).setMinimumQuantity(quantity);
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        itemDTO.setMinimumQuantity(quantity);
        Repo.getInstance().updateAnItemWithoutOldPrices(itemDTO);
    }

    public void editItemDescription(int itemId, String description) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        //this.items.get(itemId).setDescription(description);
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        itemDTO.setDescription(description);
        Repo.getInstance().updateAnItemWithoutOldPrices(itemDTO);
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }


    public void updateItemCostPrice(int itemId, double newPrice) throws Exception{
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        //this.items.get(itemId).getOldCostPrices().add(this.items.get(itemId).getCostPrice());
        //this.items.get(itemId).setCostPrice(newPrice);
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        itemDTO.setCostPrice(newPrice);
//        itemDTO.setCostCounter(itemDTO.getCostCounter() + 1);
        Repo.getInstance().updateCostPriceForItem(itemId,newPrice,itemDTO.getCostPrice());
    }

    public void updateItemSalePrice(int itemId, double newPrice) throws Exception{
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        //this.items.get(itemId).getOldSalePrices().add(this.items.get(itemId).getSalePrice());
        //this.items.get(itemId).setSalePrice(newPrice);
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        itemDTO.setSalePrice(newPrice);
        itemDTO.setSaleCounter(itemDTO.getSaleCounter() + 1);
        Repo.getInstance().updateSalePriceForItem(itemId,newPrice,itemDTO.getSalePrice());
    }

    public InventoryDTO convertToDTO(){
        Map<Integer, ItemDTO> itemsDTO = new HashMap<>();
        for (Integer itemId: items.keySet()) {
            ItemDTO itemDTO = items.get(itemId).convertToDTO();
            itemsDTO.put(itemId, itemDTO);
        }
        return new InventoryDTO(itemsDTO, idCounter);
    }

    public double getItemWeight(int itemId) throws SQLException {
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        return itemDTO.getFeaturesDTO().getWeight();
    }

}