package bussinessLayer.DTOPackage;

import bussinessLayer.InventoryPackage.ItemFeatures;

public class ItemFeaturesDTO {

    private int id;
    private double weight;
    private String category;
    private String subCategory;
    private String sub2Category;
    private String manufacturer;

    public ItemFeaturesDTO(int id, double weight, String category, String subCategory, String sub2Category, String manufacturer) {
        this.id = id;
        this.weight = weight;
        this.category = category;
        this.subCategory = subCategory;
        this.sub2Category = sub2Category;
        this.manufacturer = manufacturer;
    }
    public ItemFeatures convertFromDTO() {
        return new ItemFeatures(id, weight, category, subCategory ,sub2Category ,manufacturer);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSub2Category() {
        return sub2Category;
    }

    public void setSub2Category(String sub2Category) {
        this.sub2Category = sub2Category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
