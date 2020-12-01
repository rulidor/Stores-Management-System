package bussinessLayer.OrderPackage;


import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import bussinessLayer.DTOPackage.CartDTO;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;

public class Cart {
    private List<LineCatalogItem> itemsToDelivery;
    private int totalAmount;
    private int totalAmountRecieved;
    private double totalPrice;

    public Cart() {
        this.totalAmount = 0;
        this.totalPrice = 0;
        itemsToDelivery = new ArrayList<LineCatalogItem>();
    }

    public Cart(CartDTO cart) {
    	this();
    	getLineItems(cart);
    	this.totalAmount = cart.getTotalAmount();
    	this.totalPrice = cart.getTotalPrice();
    	this.totalAmountRecieved = cart.getTotalAmountRecieved();
	}

	private void getLineItems(CartDTO cart) {
		for (LineCatalogItemDTO lineCatalogItem : cart.getLineItems()) {
			itemsToDelivery.add(new LineCatalogItem(lineCatalogItem.getCatalogItem(), lineCatalogItem.getAmount(), lineCatalogItem.getPriceAfterDiscount(), lineCatalogItem.getAmountRecieved()));
		}
	}

	public void addItemToCart(bussinessLayer.SupplierPackage.CatalogItem catItem, int amount, double priceAfterDiscount) {
        for (LineCatalogItem line : itemsToDelivery) {
			if(line.getCatalogItemId() == catItem.getCatalogItemId()) {
		        this.totalAmount += amount;
		        totalPrice -= line.getPriceAfterDiscount() * (double) line.getAmount();
				line.setAmountAndPrice(amount,priceAfterDiscount);
				totalPrice += priceAfterDiscount * (double) line.getAmount();
				return;
			}
		}
        itemsToDelivery.add(new LineCatalogItem(catItem, amount, priceAfterDiscount));
        
    }

    public void removeFromCart(int catalogItemId) throws Exception {
        int i = 0;
        for (LineCatalogItem lineCatItem : itemsToDelivery) {
            if (lineCatItem.getCatalogItemId() == catalogItemId) {
                itemsToDelivery.remove(i);
                this.totalAmount -= lineCatItem.getAmount();
                totalPrice -= (double) lineCatItem.getAmount() * lineCatItem.getPriceAfterDiscount();
                return;
            }
            i += 1;
        }
        throw new Exception("Item does not Exist in the cart.");
    }

    /**
     * @return the totalAmount
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        String s = "\nItem name:ID\tPrice\tAmount";
        for (LineCatalogItem item : itemsToDelivery) {
            s += "\n" + item.toString();
        }
        s += "\nTotal:\t" + totalPrice;
        return s;
    }

    public List<LineCatalogItem> getItemsToDelivery() {
        return itemsToDelivery;
    }

	public CartDTO converToDTO() {
        List<LineCatalogItemDTO> list = new ArrayList<LineCatalogItemDTO>();
        for (LineCatalogItem lineCatalogItem : itemsToDelivery) {
            list.add(lineCatalogItem.converToDTO());
        }
		return new CartDTO(list, totalAmount, totalPrice, totalAmountRecieved);
	}

	public double getPriceAfterDiscount(int catalogItemId) throws Exception {
		for (LineCatalogItem line : itemsToDelivery) {
            if(line.getCatalogItemId() == catalogItemId) return line.getPriceAfterDiscount();
        }

        throw new Exception("Item not found in cart");
	}

	public LineCatalogItemDTO getLineCatalogItemDTO(int catalogItemId) throws Exception {
        for (LineCatalogItem lineCatalogItem : itemsToDelivery) {
            if(lineCatalogItem.getCatalogItemId() == catalogItemId) return lineCatalogItem.converToDTO(); 
        }
        
        throw new Exception("Line Item not found!");
	}

	public void updateCartBeforeReturningToUser() {
		totalAmount = 0;
		totalPrice = 0;
		for (LineCatalogItem lineCatalogItem : itemsToDelivery) {
			totalAmount += lineCatalogItem.getAmount();
			totalPrice += lineCatalogItem.getAmount()*lineCatalogItem.getPriceAfterDiscount();
		}
		
	}
	
	public boolean fillCartWithScheduled(Entry<Integer,Integer> entry) {
		for (LineCatalogItem line : itemsToDelivery) {
			if(line.getCatalogItemId() == entry.getKey()) {
				int difference = entry.getValue() - line.getAmount();
				if(difference >= 0) {
					line.setAmount(entry.getValue());
				}
				return true;
			}
		}
		return false;
	}

	public int getTotalAmountRecieved() {
		return totalAmountRecieved;
	}

	public void setTotalAmountRecieved(int totalAmountRecieved) {
		this.totalAmountRecieved = totalAmountRecieved;
	}


}
