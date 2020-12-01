package bussinessLayer.DTOPackage;

import bussinessLayer.BranchPackage.ItemStatus;

public class ItemStatusDTO {

    private int branchId;
    private int itemId;
    private int quantityOverall;
    private int quantityShelf;
    private int quantityStock;

    public ItemStatusDTO(int branchId, int itemId, int quantityShelf, int quantityStock) {
        this.quantityOverall = quantityShelf + quantityStock;
        this.branchId = branchId;
        this.itemId = itemId;
        this.quantityShelf = quantityShelf;
        this.quantityStock = quantityStock;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantityShelf() {
        return quantityShelf;
    }

    public void setQuantityShelf(int quantityShelf) {
        this.quantityShelf = quantityShelf;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public int getQuantityOverall() {
        return quantityOverall;
    }

    public void setQuantityOverall(int quantityOverall) {
        this.quantityOverall = quantityOverall;
    }

    public ItemStatus convertFromDTO() {
        return new ItemStatus(branchId, itemId, quantityShelf+quantityStock, quantityShelf, quantityStock);
    }
}
