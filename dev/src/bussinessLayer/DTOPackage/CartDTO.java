package bussinessLayer.DTOPackage;

import java.util.List;

public class CartDTO {

	private List<LineCatalogItemDTO> lineItems;
	private int totalAmount;
	private double totalPrice;
	private int totalAmountRecieved;

	public CartDTO(List<LineCatalogItemDTO> lineItems, int totalAmount, double totalPrice, int totalAmountRecieved) {
		this.lineItems = lineItems;
		this.totalAmount = totalAmount;
		this.totalPrice = totalPrice;
		this.totalAmountRecieved = totalAmountRecieved;
	}

	public List<LineCatalogItemDTO> getLineItems() {
		return lineItems;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	@Override
	public String toString() {
		String s = "";
		for (LineCatalogItemDTO lineItem : lineItems) {
			s += lineItem.toString() +"\n";
		}

		return s + "Total\t\t\t\t\t\t"+totalAmount + "\t\t\t\t\t\t\t" + totalPrice;
	}

	public int getTotalAmountRecieved() {
		return totalAmountRecieved;
	}
    
}