package ServiceLayer;

import java.util.HashMap;
import java.util.List;

import bussinessLayer.DTOPackage.*;


public interface IOrderService {

    /**
     * Get order details for a specific order by id
     * @param orderId
     * @return if success returns OrderDTO with all the details about the order else
     * the respose holds error message
     */
    public ResponseT<OrderDTO> getOrderDetails(String orderId);

    /**
     * Creates a new empty order
     * @param supplierId supplier id of the supplier we want to order from
     * @param branchId branch id of the ordering branch
     * @return the orderId which created
     */
    public ResponseT<Integer> createAnOrder(int supplierId, int branchId);

    /**
     * Add item to Order
     * @param orderId The order Id
     * @param catalogItemId The catalog item Id which we want to add to the order
     * @param amount The amount of the catalog item we want to buy
     * @return if success "Done", else error message
     */
    public Response addItemToCart(String orderId, String catalogItemId, String amount,int branchId);

    /**
     * Remove item from order
     * @param orderId The order Id
     * @param catalogItemId The catalog item which we want to remove from the order
     * @param branchId 
     * @return if success "Done", else error message
     */
    public Response removeFromCart(String orderId, String catalogItemId, int branchId);

    /**
     * Change order status to IN PROGGRESS.
     * Meaning that the order will not be allowed to change
     * from now on.
     * @param orderId The order ID
     * @return if success "Done", else error message
     */
    public Response sendOrder(int orderId);

    /**
     * Confirms that the order arrived to branch and
     * changes its status to "COMPLETED"
     * @param orderId The order ID
     * @return if success "Done", else error message
     */
    public Response endOrder(String orderId);

    /**
     * Get the supplier's orders
     * @param supplierId The supplier ID
     * @return List of OrderDTO which contains all the orders details of
     * the supplier
     */
    public ResponseT<List<OrderDTO>> printOrdersFromSupplier(int supplierId, int branchId);

    /**
     * Invokes a thread which will manage all scheduled orders
     * @return if success "Done", else error message
     */
    public Response startScheduledOrder();


    /**
     * Kill the Timer which handles regular deliveries
     * @return if success "Done", else error message
     */
	public Response purgeTimer();
	
	/**
	 * subscribe to regular deliveries from a certain supplier
	 * 
	 * @param branchId
	 * @param supplierId
	 * @param day
	 * @param itemsToOrder
	 * @return if success "Done" else error message
	 */
	public Response subscribeScheduleOrder(int branchId, int supplierId, String day,
			HashMap<String, String> itemsToOrder);

	public ResponseT<List<OrderDTO>> getAllOpenOrdersByBranch(int branchId);

	public ResponseT<List<OrderDTO>> getAllOrdersByBranch(int branchId);
	
	public Response cancelOrder(String orderId);

    public Response loadFirstOrders();
}