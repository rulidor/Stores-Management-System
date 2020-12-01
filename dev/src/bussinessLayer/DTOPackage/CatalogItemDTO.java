package bussinessLayer.DTOPackage;


public class CatalogItemDTO {

	private int catalogItemId;
	private int itemId;
	private double price;
	private String description;

	public CatalogItemDTO(int catalogItemId, String description, double price, int itemId) {
		this.catalogItemId = catalogItemId;
		this.description = description;
		this.price = price;
		this.itemId = itemId;
	}
	public CatalogItemDTO(CatalogItemDTO c ) {
		this.catalogItemId = c.catalogItemId;
		this.description = c.description;
		this.price = c.price;
		this.itemId = c.itemId;
	}


	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public int getCatalogItemId() {
		return catalogItemId;
	}

	@Override
	public String toString() {
		return catalogItemId + "\t\t\t" + itemId + "\t\t" + price+ "\t" + description;
	}

	public int getItemId() {
		return itemId;
	}
    
}