package bussinessLayer.DTOPackage;

import bussinessLayer.InventoryPackage.Item;
import bussinessLayer.InventoryPackage.ItemFeatures;

import java.util.List;

public class ItemDTO {

    private int id;
    private String description;
    private double costPrice;
    private double salePrice;
    private List<Double> oldCostPrices;
    private List<Double> oldSalePrices;
    private int minimumQuantity;
    private ItemFeaturesDTO featuresDTO;
    private int costCounter;
    private int saleCounter;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.description = item.getDescription();
        this.costPrice = item.getCostPrice();
        this.salePrice = item.getSalePrice();
        this.oldCostPrices = item.getOldCostPrices();
        this.oldSalePrices = item.getOldSalePrices();
        this.minimumQuantity = item.getMinimumQuantity();
        this.featuresDTO = new ItemFeaturesDTO(item.getId(), item.getFeatures().getWeight(), item.getFeatures().getCategory(), item.getFeatures().getSubCategory(), item.getFeatures().getSub2Category(), item.getFeatures().getManufacturer());
        this.costCounter = oldCostPrices.size();
        this.saleCounter = oldSalePrices.size();
    }

	public ItemDTO(int id2, String description2, double costPrice2, double salePrice2,
			List<Double> oldCostPrices2, List<Double> oldSalePrices2, int minimumQuantity2,
			ItemFeaturesDTO itemFeaturesDTO) {
		this.id = id2;
        this.description = description2;
        this.costPrice = costPrice2;
        this.salePrice = salePrice2;
        this.oldCostPrices = oldCostPrices2;
        this.oldSalePrices = oldSalePrices2;
        this.minimumQuantity = minimumQuantity2;
        this.featuresDTO = itemFeaturesDTO;
        this.costCounter = this.oldCostPrices.size();
        this.saleCounter = this.oldSalePrices.size();
	}

	public Item convertFromDTO(){
        ItemFeatures itemFeatures = this.featuresDTO.convertFromDTO();
        return new Item(id, description, costPrice, salePrice, "" ,minimumQuantity, itemFeatures);
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

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public ItemFeaturesDTO getFeaturesDTO() {
        return featuresDTO;
    }

    public void setFeaturesDTO(ItemFeaturesDTO featuresDTO) {
        this.featuresDTO = featuresDTO;
    }

    public int getCostCounter() {
        return costCounter;
    }

    public void setCostCounter(int costCounter) {
        this.costCounter = costCounter;
    }

    public int getSaleCounter() {
        return saleCounter;
    }

    public void setSaleCounter(int saleCounter) {
        this.saleCounter = saleCounter;
    }
}
