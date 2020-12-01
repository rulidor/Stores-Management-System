package bussinessLayer.BranchPackage;

import bussinessLayer.DTOPackage.ItemStatusDTO;

public class ItemStatus {

    private int branchId;
    private int itemId;
    private int quantityOverall;
    private int quantityShelf;
    private int quantityStock;

    public ItemStatus(int branchId, int itemId, int quantityOverall, int quantityShelf, int quantityStock) {
        this.branchId = branchId;
        this.itemId = itemId;
        this.quantityOverall = quantityOverall;
        this.quantityShelf = quantityShelf;
        this.quantityStock = quantityStock;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantityOverall() {
        return quantityOverall;
    }

    public void setQuantityOverall(int quantityOverall) {
        this.quantityOverall = quantityOverall;
    }

    public int getQuantityShelf() {
        return quantityShelf;
    }

    public void setQuantityShelf(int quantityShelf) {
        this.quantityShelf = quantityShelf;
        this.quantityOverall = this.quantityShelf + this.quantityStock;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
        this.quantityOverall = this.quantityShelf + this.quantityStock;
    }

    public ItemStatusDTO convertToDTO() {
        ItemStatusDTO itemStatusDTO = new ItemStatusDTO(branchId,itemId,quantityShelf,quantityStock );
        return itemStatusDTO;
    }
}
