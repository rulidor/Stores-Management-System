package bussinessLayer.OrderPackage;

import bussinessLayer.DTOPackage.CatalogItemDTO;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;
import bussinessLayer.SupplierPackage.CatalogItem;

public class LineCatalogItem {

    private bussinessLayer.SupplierPackage.CatalogItem catalogItem;
    private int amount;
    private double priceAfterDiscount;
    private int amountRecieved = 0;

    public LineCatalogItem(bussinessLayer.SupplierPackage.CatalogItem catItem, int amount, double priceAfterDiscount) {
        this.catalogItem = catItem;
        this.amount = amount;
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public LineCatalogItem(CatalogItemDTO catalogItem2, int totalAmount, double priceAfterDiscount, int amountRecieved) {
		this.catalogItem = new CatalogItem(catalogItem2);
		this.amount = totalAmount;
		this.priceAfterDiscount = priceAfterDiscount;
		this.amountRecieved = amountRecieved;
		
	}

	public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCatalogItemId() {
        return catalogItem.getCatalogItemId();
    }

    /**
     * @return the priceAfterDiscount
     */
    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    @Override
    public String toString() {
        return "" + catalogItem.getDescription() + "\t" + catalogItem.getCatalogItemId() + "\t" + priceAfterDiscount + "\t" + amount;
    }

	public LineCatalogItemDTO converToDTO() {
        return new LineCatalogItemDTO(catalogItem.converToDTO(), amount, priceAfterDiscount, amountRecieved);
	}

	public Integer getItemId() {
		return catalogItem.getItemId();
	}

	public void setPriceAfterDiscount(double priceForItemWithAmountAfterDiscount) {
		priceAfterDiscount = priceForItemWithAmountAfterDiscount;
		
	}

	public void setAmountAndPrice(int amount2, double priceAfterDiscount2) {
		amount += amount2;
		priceAfterDiscount = priceAfterDiscount2;
		
	}

	public int getAmountRecieved() {
		return amountRecieved;
	}

	public void setAmountRecieved(int amountRecieved) {
		this.amountRecieved = amountRecieved;
	}
}
