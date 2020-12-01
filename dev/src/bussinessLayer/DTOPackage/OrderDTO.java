package bussinessLayer.DTOPackage;

import java.time.LocalDateTime;


public class OrderDTO {

	private int orderId;
	private int supplierId;
	private String orderStatus;
	private LocalDateTime creationDate;
	private LocalDateTime deliveryDate;
	private CartDTO cart;
	private int branchId;

	public OrderDTO(int orderId, int supplierId, String orderStatus, LocalDateTime dateTimeAtCreation,
	LocalDateTime deliveryDate, CartDTO cart, int branchId) {
		this.orderId = orderId;
		this.supplierId = supplierId;
		this.orderStatus = orderStatus;
		this.creationDate = dateTimeAtCreation;
		this.deliveryDate = deliveryDate;
		this.cart = cart;
		this.branchId = branchId;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public CartDTO getCart() {
		return cart;
	}

	public int getBranchId() {
		return branchId;
	}

    public void setOrderId(int orderId) {
		this.orderId = orderId;
    }
    
    @Override
    public String toString() {
    	String s = "Order Num: " + orderId + "\nBranch ID: " + branchId+ "\nSupplier ID: " + supplierId 
    			+"\nOrder Status: "+ orderStatus + "\nCreation Date: " + creationDate;
    	if(orderStatus.equals("INPROGRESS") || orderStatus.equals("OPEN")) s += "\nEstimated Delivery Date: " + deliveryDate.toString();
    	if(orderStatus.equals("COMPLETE")) s+= "\nDelivered on: " + deliveryDate.toString();
    	s += "\nCatalog item ID\t\tDescription\t\tAmoumt\t\tprice\t\tDiscount(%)\t\tPrice After Discount\n";
    	return s +cart.toString();
    	
    }
}