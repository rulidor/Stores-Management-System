package bussinessLayer.DTOPackage;

import bussinessLayer.InventoryPackage.Inventory;
import bussinessLayer.InventoryPackage.Item;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class InventoryDTO {

    private Map<Integer, ItemDTO> itemsDTO;
    private int idCounter; //counts items

    public InventoryDTO(Map<Integer, ItemDTO> itemsDTO, int idCounter) {
        this.itemsDTO = itemsDTO;
        this.idCounter = idCounter;
    }
    public void updateFromDTO() throws SQLException {
        Inventory.getInstance().setIdCounter(this.idCounter);
        Map<Integer, Item> newMap = new HashMap<>();
        for (Integer itemId: itemsDTO.keySet())
        {
            newMap.put(itemId, itemsDTO.get(itemId).convertFromDTO());
        }
        Inventory.getInstance().setItems(newMap);
    }

    public InventoryDTO(Inventory inventory) {
        Map<Integer, ItemDTO> itemList = new HashMap<>();
        for (Integer itemId: inventory.getItems().keySet())
        {
            ItemDTO itemDTO = new ItemDTO(inventory.getItems().get(itemId));
            itemList.put(itemId, itemDTO);
        }
        this.itemsDTO = itemList;
        this.idCounter = inventory.getIdCounter();
    }

    public Map<Integer, ItemDTO> getItemsDTO() {
        return itemsDTO;
    }

    public void setItemsDTO(Map<Integer, ItemDTO> itemsDTO) {
        this.itemsDTO = itemsDTO;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }
}
