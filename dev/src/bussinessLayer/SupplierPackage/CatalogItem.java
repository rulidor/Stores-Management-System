package bussinessLayer.SupplierPackage;

import bussinessLayer.DTOPackage.CatalogItemDTO;

public class CatalogItem {

    private int itemId;
    private String description;
    private int catalogItemId;
    private double price;

    public CatalogItem(int itemId, int catalogItemId, double price, String description) {
        this.itemId = itemId;
        this.catalogItemId = catalogItemId;
        this.price = price;
    }

    public CatalogItem(CatalogItemDTO catalogItemDTO) {
        catalogItemId = catalogItemDTO.getCatalogItemId();
        price = catalogItemDTO.getPrice();
        itemId = catalogItemDTO.getItemId();
        description = catalogItemDTO.getDescription();
	}

    public int getCatalogItemId() {
        return catalogItemId;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        String s = "";
        s = s + description + " catalog-Item-Id: " + this.getCatalogItemId() + ", price: " + this.getPrice();
        return s;
    }

    public String getDescription() {
        return description;
    }

	public int getItemId() {
		return itemId;
	}

	public CatalogItemDTO converToDTO() {
        return new CatalogItemDTO(catalogItemId, description, price, itemId);
	}


}
