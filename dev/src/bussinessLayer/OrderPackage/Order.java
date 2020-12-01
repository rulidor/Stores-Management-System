package bussinessLayer.OrderPackage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map.Entry;

import DataAccessLaye.Repo;
import ServiceLayer.Service;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;
import bussinessLayer.DTOPackage.OrderDTO;
import bussinessLayer.DTOPackage.ScheduledDTO;
import bussinessLayer.SupplierPackage.Supplier;

public class Order {

    enum Status {
        OPEN, INPROGRESS, COMPLETE,CANCELED
    }

    private Status status = Status.OPEN;
    private Cart cart = new Cart();
    private bussinessLayer.SupplierPackage.Supplier supplier;
    private int orderId = -1;
    private LocalDateTime dateTimeAtCreation = LocalDateTime.now();
    private LocalDateTime deliveryDate;
    private int branchId;


    public Order(Supplier supplier, int branchId, int orderId) throws Exception {
        this.orderId = orderId;
        this.supplier = supplier;
        this.branchId = branchId;
        deliveryDate = null;
    }

    public Order(Supplier supplier, int branchId) throws Exception {
        this.supplier = supplier;
        this.branchId = branchId;
        deliveryDate = supplier.getNextDateOfDelivery();
        orderId++;
    }

    public Order(ScheduledDTO scheduled, Date date,Supplier supplier) throws Exception {
        this(supplier, scheduled.getBranchId(),-1);
        deliveryDate = date.toInstant()
        	      .atZone(ZoneId.systemDefault())
        	      .toLocalDateTime();
        fillCart(scheduled);
    }

    public Order(OrderDTO orderDTO) throws Exception {
        status = Status.valueOf(orderDTO.getOrderStatus());
        this.branchId = orderDTO.getBranchId();
        this.supplier = new Supplier(Repo.getInstance().getSupplierById(orderDTO.getSupplierId()));
        this.cart = new Cart(orderDTO.getCart());
        this.orderId = orderDTO.getOrderId();
        this.deliveryDate = orderDTO.getDeliveryDate();
        this.dateTimeAtCreation = orderDTO.getCreationDate();
	}

	private void fillCart(ScheduledDTO scheduled)throws Exception {
		if(scheduled.getItemsToOrder().size() == 0) throw new  Exception ("Cart Is Empty");
        for (Entry<Integer, Integer> it : scheduled.getItemsToOrder().entrySet()) {
            try{addItemToCart(it.getKey(), it.getValue());}catch(Exception e) {System.out.println(e.getMessage());}
        }
    }

    /**
     * @return the branchId
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    public void addItemToCart(int catalogItemId, int amount) throws Exception {
    	if(status.toString().equals("CANCELED") || !status.toString().equals("OPEN")) throw new Exception("You can no longer add Items to this order!");
        if (amount <= 0) throw new Exception("Amount must be larger than zero " + catalogItemId);
        cart.addItemToCart(supplier.getCatalogItem(catalogItemId), amount, supplier.getPriceAfterDiscountByItem(catalogItemId, amount));
    }

    public void removeFromCart(int catalogItemId) throws Exception {
    	if(status.toString().equals("CANCELED") || !status.toString().equals("OPEN")) throw new Exception("You can no longer remove Items from this order!");
        cart.removeFromCart(catalogItemId);
    }

    public void sendOrder() throws Exception {
        if (!status.toString().equals("OPEN")) throw new Exception("Order is not OPEN");
        deliveryDate = supplier.getNextDateOfDelivery();
        status = Status.INPROGRESS;
    }

    /**
     * @return the status
     */
    public Status getOrderStatus() {
        return status;
    }

    public void endOrder() throws Exception {
        if (status.toString().equals("COMPLETE")) throw new Exception("Already completed");
        if (status.toString().equals("OPEN")) throw new Exception("The order is still OPEN confirm it first");
        if(status.toString().equals("CANCELED")) throw new Exception("The order has already been canceled");
        status = Status.COMPLETE;
        this.deliveryDate = LocalDateTime.now();
    }

    public int getSupplierId() {
        return supplier.getSupplierId();
    }


	/**
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }


	/**
     * @return the creation date
     */
    public LocalDateTime getCreationDate() {
        return dateTimeAtCreation;
    }


	/**
     * @return the deliveryDate
     */
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Convert BL Order to OrderDTO
     * @return
     */
	public OrderDTO converToDTO() {
		updateOrderBeforeReturningToUser();
        return new bussinessLayer.DTOPackage.OrderDTO(orderId, getSupplierId(),getOrderStatus().name(), dateTimeAtCreation, deliveryDate, cart.converToDTO(), branchId);
	}

	private void updateOrderBeforeReturningToUser() {
		for (LineCatalogItem line : cart.getItemsToDelivery()) {
			try {
				line.setPriceAfterDiscount(supplier.getPriceForItemWithAmountAfterDiscount(line.getCatalogItemId(), line.getAmount()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		cart.updateCartBeforeReturningToUser();
		
	}

	public double getPriceAfterDiscount(int catalogItemId) throws Exception {
		return cart.getPriceAfterDiscount(catalogItemId);
	}

	public LineCatalogItemDTO getLineCatalogItemDTO(int catalogItemId) throws Exception {
		return cart.getLineCatalogItemDTO(catalogItemId);
	}

	public void fillCartWithScheduled(ScheduledDTO scheduled) throws Exception {
		boolean found = false;
		for (Entry<Integer, Integer> entry : scheduled.getItemsToOrder().entrySet()) {
			found = cart.fillCartWithScheduled(entry);
			if(!found) cart.getItemsToDelivery().add(new LineCatalogItem(supplier.getCatalogItem(entry.getKey()),entry.getValue(), 0));
		}
		
	}

	public void cancelOrder() throws Exception {
		if (status.toString().equals("COMPLETE")) throw new Exception("Already completed");
		if (status.toString().equals("INPROGRESS")) {
			new Service().removeDelivery(String.valueOf(orderId));
		}
		status = Status.CANCELED;
	}
}
