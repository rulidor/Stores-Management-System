package bussinessLayer.InventoryPackage;



import java.util.ArrayList;
import java.util.List;

import bussinessLayer.DTOPackage.ItemDTO;
import bussinessLayer.DTOPackage.ItemFeaturesDTO;

public class Item {
    private int id;
    private String description;
    private double costPrice;
    private double salePrice;
    private String position;
    private List<Double> oldCostPrices;
    private List<Double> oldSalePrices;
    private int minimumQuantity;
    private ItemFeatures features;

    public Item(int id, String description, double costPrice, double salePrice, String position, int minimumQuantity, ItemFeatures features) {
        this.id = id;
        this.description = description;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.position = position;
        this.oldCostPrices = new ArrayList<>();
        this.oldSalePrices = new ArrayList<>();
        this.minimumQuantity = minimumQuantity;
        this.features = features;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Double> getOldCostPrices() {
        return oldCostPrices;
    }

    public void setOldCostPrices(List<Double> oldCostPrices) {
        this.oldCostPrices = oldCostPrices;
    }

    public List<Double> getOldSalePrices() {
        return oldSalePrices;
    }

    public void setOldSalePrices(List<Double> oldSalePrices) {
        this.oldSalePrices = oldSalePrices;
    }

    public ItemFeatures getFeatures() {
        return features;
    }

    public void setFeatures(ItemFeatures features) {
        this.features = features;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public ItemDTO convertToDTO(){
        ItemFeaturesDTO itemFeaturesDTO = this.features.convertToDTO();
        return new ItemDTO(id, description, costPrice, salePrice, oldCostPrices, oldSalePrices, minimumQuantity, itemFeaturesDTO);
    }
}
