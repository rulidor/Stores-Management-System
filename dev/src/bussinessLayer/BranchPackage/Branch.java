package bussinessLayer.BranchPackage;

import DataAccessLaye.Repo;
import bussinessLayer.DTOPackage.*;
import bussinessLayer.InventoryPackage.Inventory;
import MessageTypes.StockReport;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Branch {

    private int id;
    private String description;
    private DamagedController damagedController;
    private Inventory inventory;
    private Map<Integer, ItemStatus> stockByItemId;

    public Branch(int id, String description) throws SQLException {
        this.id = id;
        this.description = description;
        this.damagedController = new DamagedController(this.id);
        this.inventory = Inventory.getInstance();
        this.stockByItemId = new HashMap<>();
    }



//    public void addItemStatus(int itemId, int quantityShelf, int quantityStock) throws Exception {
//        if (!this.inventory.getItems().keySet().contains(itemId)){
//            throw new Exception("Item was not found in the Inventory");
//        }
//        if (!this.stockByItemId.keySet().contains(itemId)) {
//            throw new Exception("Item already exist in this branch. Did you mean update item status?");
//        }
//        ItemStatus itemStatus = new ItemStatus(this.id, itemId, quantityShelf+quantityStock, quantityShelf, quantityStock);
//        this.stockByItemId.put(itemId, itemStatus);
//    }

    public void editShelfQuantity(int itemId, int delta) throws Exception {
        InventoryDTO inventoryDTO = Repo.getInstance().getInventory();

        if (!inventoryDTO.getItemsDTO().keySet().contains(itemId)) {
            throw new Exception("Item was not found in the Inventory.\n" +
                    "You should first add an item to the inventory, and then you can update your branch quantity.");
        }
        BranchDTO branchDTO = Repo.getInstance().getBranchById(this.id);
        if(!branchDTO.getStockByItemId().keySet().contains(itemId) && delta<0){
            throw new Exception("You can't set quantity that is smaller than 0.");
        }
        else if(!branchDTO.getStockByItemId().keySet().contains(itemId) && delta>=0){
            ItemStatusDTO itemStatusDTO = new ItemStatusDTO(this.id, itemId, delta, 0);
            Repo.getInstance().addItemStatus(itemStatusDTO);
            return;
        }
        else if(branchDTO.getStockByItemId().keySet().contains(itemId)){
            if(delta<0 && Math.abs(delta) > branchDTO.getStockByItemId().get(itemId).getQuantityShelf())
                throw new Exception("Inserted negative delta which is greater than current quantity.");
            ItemStatusDTO itemStatusDTO = Repo.getInstance().getItemStatus(this.id, itemId);
            itemStatusDTO.setQuantityShelf(itemStatusDTO.getQuantityShelf() + delta);
            itemStatusDTO.setQuantityOverall(itemStatusDTO.getQuantityStock()+itemStatusDTO.getQuantityShelf());
            Repo.getInstance().updateAnItemStatus(itemStatusDTO);
        }
    }

    public void editStockQuantity(int itemId, int delta) throws Exception {
        InventoryDTO inventoryDTO = Repo.getInstance().getInventory();

        if (!inventoryDTO.getItemsDTO().keySet().contains(itemId)) {
            throw new Exception("Item was not found in the Inventory.\n" +
                    "You should first add an item to the inventory, and then you can update your branch quantity.");
        }
        BranchDTO branchDTO = Repo.getInstance().getBranchById(this.id);
        if(!branchDTO.getStockByItemId().keySet().contains(itemId) && delta<0){
            throw new Exception("You can't set quantity that is smaller than 0.");
        }
        else if(!branchDTO.getStockByItemId().keySet().contains(itemId) && delta>=0){
            ItemStatusDTO itemStatusDTO = new ItemStatusDTO(this.id, itemId, 0, delta);
            Repo.getInstance().addItemStatus(itemStatusDTO);
            return;
        }
        else if(branchDTO.getStockByItemId().keySet().contains(itemId)){
            if(delta<0 && Math.abs(delta) > branchDTO.getStockByItemId().get(itemId).getQuantityStock())
                throw new Exception("Inserted negative delta which is greater than current quantity.");
            ItemStatusDTO itemStatusDTO = Repo.getInstance().getItemStatus(this.id, itemId);
            itemStatusDTO.setQuantityStock(itemStatusDTO.getQuantityStock() + delta);
            itemStatusDTO.setQuantityOverall(itemStatusDTO.getQuantityStock()+itemStatusDTO.getQuantityShelf());
            Repo.getInstance().updateAnItemStatus(itemStatusDTO);
        }
//        this.stockByItemId.get(itemId).setQuantityStock(delta + this.stockByItemId.get(itemId).getQuantityStock());
    }

    public void cancelCard(int itemId, int quantityToCancel) throws Exception {
        if (!this.stockByItemId.keySet().contains(itemId)) {
            throw new Exception("Item was not found in the branch");
        }
        this.stockByItemId.get(itemId).setQuantityShelf(this.stockByItemId.get(itemId).getQuantityShelf() + quantityToCancel);
    }



    public void updateDamagedItem(int itemId, int delta) throws Exception {
        InventoryDTO inventoryDTO = Repo.getInstance().getInventory();

        if (!inventoryDTO.getItemsDTO().keySet().contains(itemId)) {
            throw new Exception("Item was not found in the Inventory.\n" +
                    "You should first add an item to the inventory, and then you can update your branch quantity.");
        }
        BranchDTO branchDTO = Repo.getInstance().getBranchById(this.id);
        if(!branchDTO.getStockByItemId().containsKey(itemId)){
            throw new Exception("Item was not found in the branch");
        }

        DamagedControllerDTO damagedControllerDTO = Repo.getInstance().getDamagedControllerForBranch(this.id);
        if(!damagedControllerDTO.getQuantityById().containsKey(itemId) && delta<0){
            throw new Exception("You can't set quantity that is smaller than 0.");
        }
        else if(!damagedControllerDTO.getQuantityById().containsKey(itemId) && delta>=0){
            Repo.getInstance().insertNewDamagedItem(this.id, itemId, delta);
            return;
        }
        else if(damagedControllerDTO.getQuantityById().containsKey(itemId)){
            if(delta<0 &&  Math.abs(delta) > damagedControllerDTO.getQuantityById().get(itemId))
                throw new Exception("Inserted negative delta which is greater than current quantity.");
            Repo.getInstance().updateExistingDamagedItem(this.id, itemId, damagedControllerDTO.getQuantityById().get(itemId) + delta);

        }



//        if (!this.inventory.getItems().keySet().contains(itemId)){
//            throw new Exception("Item was not found in the Inventory");
//        }
//        if (!this.stockByItemId.keySet().contains(itemId)) {
//            throw new Exception("Item was not found in the branch");
//        }
//        if(!this.damagedController.getQuantityById().keySet().contains(itemId)) {
//            Repo.getInstance().insertNewDamagedItem(this.id,itemId,delta);
//            this.damagedController.getQuantityById().put(itemId, delta);
//            return;
//        }
//        this.damagedController.getQuantityById().put(itemId, this.damagedController.getQuantityById().get(itemId) + delta);
//        Repo.getInstance().updateExistingDamagedItem(id,itemId,this.damagedController.getQuantityById().get(itemId)+delta);
    }

    /*
         the report will include only items which has overall quantity < 5 * min. quantity
     */
    public Map<Integer, Integer> generateToOrderReport() throws SQLException {
        InventoryDTO inventoryDTO = Repo.getInstance().getInventory();
        BranchDTO branchDTO = Repo.getInstance().getBranchById(this.id);

        Map<Integer, Integer> res = new HashMap<>();
        for (ItemStatusDTO itemStatus: branchDTO.getStockByItemId().values()) {
            int quantity = 5 * inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getMinimumQuantity() - itemStatus.getQuantityOverall();
            if(quantity<=0)
                continue;
            res.put(itemStatus.getItemId(), quantity);
        }
        return res;
    }

    /*
    create warning only for items which have overall quantity<=minimum quantity.
     */
    public Map<Integer, Integer> generateWarningReport() throws SQLException {
        InventoryDTO inventoryDTO = Repo.getInstance().getInventory();
        BranchDTO branchDTO = Repo.getInstance().getBranchById(this.id);

        Map<Integer, Integer> res = new HashMap<>();
        for (ItemStatusDTO itemStatus: branchDTO.getStockByItemId().values()) {
            int quantity = itemStatus.getQuantityOverall() - inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getMinimumQuantity();
            if(quantity>0)
                continue;
            res.put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
        }
        return res;
    }

    public Map<Integer, Integer> generateDamagedReport() throws SQLException {
        DamagedControllerDTO damagedControllerDTO = Repo.getInstance().getDamagedControllerForBranch(this.id);
        return damagedControllerDTO.getQuantityById();
    }

    public StockReport generateStockReport(String[] categories) throws SQLException {
        StockReport res = new StockReport();
        InventoryDTO inventoryDTO = Repo.getInstance().getInventory();
        BranchDTO branchDTO = Repo.getInstance().getBranchById(this.id);
        for (ItemStatusDTO itemStatus:branchDTO.getStockByItemId().values()) {
            if(categories.length == 0){ //case: print all
                res.getItemsIdToBeReported().add(itemStatus.getItemId());
                res.getDescById().put(itemStatus.getItemId(), inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getDescription());
                res.getPositionById().put(itemStatus.getItemId(), "At the branch");
                res.getManufacturerById().put(itemStatus.getItemId(), inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getManufacturer());
                res.getOverallQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
                res.getShelfQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityShelf());
                res.getStockQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityStock());
            }
            else if(categories.length == 1){
                if(!inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getCategory().equals(categories[0]))
                    continue;
                res.getItemsIdToBeReported().add(itemStatus.getItemId());
                res.getDescById().put(itemStatus.getItemId(), inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getDescription());
                res.getPositionById().put(itemStatus.getItemId(), "At the branch");
                res.getManufacturerById().put(itemStatus.getItemId(), inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getManufacturer());
                res.getOverallQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
                res.getShelfQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityShelf());
                res.getStockQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityStock());
            }
            else if(categories.length == 2){
                if(inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getCategory().equals(categories[0])==false  || inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getSubCategory().equals(categories[1])==false)
                    continue;
                res.getItemsIdToBeReported().add(itemStatus.getItemId());
                res.getDescById().put(itemStatus.getItemId(), inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getDescription());
                res.getPositionById().put(itemStatus.getItemId(), "At the branch");
                res.getManufacturerById().put(itemStatus.getItemId(), inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getManufacturer());
                res.getOverallQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
                res.getShelfQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityShelf());
                res.getStockQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityStock());
                
            }
            else if(categories.length == 3){
                if(inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getCategory().equals(categories[0])==false  || inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getSubCategory().equals(categories[1])==false ||
                        inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getSub2Category().equals(categories[2])==false)
                    continue;
                res.getItemsIdToBeReported().add(itemStatus.getItemId());
                res.getDescById().put(itemStatus.getItemId(), inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getDescription());
                res.getPositionById().put(itemStatus.getItemId(), "At the branch");
                res.getManufacturerById().put(itemStatus.getItemId(), inventoryDTO.getItemsDTO().get(itemStatus.getItemId()).getFeaturesDTO().getManufacturer());
                res.getOverallQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
                res.getShelfQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityShelf());
                res.getStockQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityStock());
            }
        }
        return res;
    }

    public bussinessLayer.BranchPackage.DamagedController getDamagedController() {
        return damagedController;
    }

    public void setDamagedController(DamagedController damagedController) {
        this.damagedController = damagedController;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException {
        this.description = description;
        Repo.getInstance().updateBranchDescription(id,description);
    }

    public BranchDTO convertToDTO(){
        InventoryDTO inventoryDTO = new InventoryDTO(inventory);
        Map<Integer, ItemStatusDTO> statusList = new HashMap<>();
        for (Integer itemId : this.stockByItemId.keySet()) {
            ItemStatusDTO itemStatusDTO = new ItemStatusDTO(id, itemId, this.stockByItemId.get(itemId).getQuantityShelf(), this.stockByItemId.get(itemId).getQuantityStock());
            statusList.put(itemId, itemStatusDTO);
        }
        return new BranchDTO(id, description, new DamagedControllerDTO(this.id, this.damagedController.getQuantityById()), inventoryDTO, statusList);
    }

    public Map<Integer, ItemStatus> getStockByItemId() {
        return stockByItemId;
    }

    public void setStockByItemId(Map<Integer, ItemStatus> stockByItemId) {
        this.stockByItemId = stockByItemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
