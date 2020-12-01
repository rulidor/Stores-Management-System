package bussinessLayer.DTOPackage;

import java.text.DecimalFormat;

public class LineCatalogItemDTO {
	private CatalogItemDTO catalogItem;
	private int amount;
	private int amountRecieved;
	private double priceAfterDiscount;

	public LineCatalogItemDTO(CatalogItemDTO catalogItem, int amount, double priceAfterDiscount, int amountRecieved) {
		this.catalogItem = catalogItem;
		this.amount = amount;
		this.priceAfterDiscount = priceAfterDiscount;
	}

	/**
	 * @return the catalogItem
	 */
	public CatalogItemDTO getCatalogItem() {
		return catalogItem;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @return the priceAfterDiscount
	 */
	public double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	@Override
	public String toString() {
		return "" + catalogItem.getCatalogItemId() +"\t\t\t" + catalogItem.getDescription() + "\t\t\t" + amount + "\t\t" +catalogItem.getPrice() +"\t\t" +
			(new DecimalFormat("##.##").format(100 - priceAfterDiscount*100/catalogItem.getPrice())) + "\t\t\t" + priceAfterDiscount + "\n";
	}

	public int getCatalogItemId(){
		return catalogItem.getCatalogItemId();
	}

	public int getAmountRecieved() {
		return amountRecieved;
	}

}
