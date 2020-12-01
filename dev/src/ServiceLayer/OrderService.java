package ServiceLayer;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import bussinessLayer.DTOPackage.OrderDTO;
import bussinessLayer.OrderPackage.OrderController;

public class OrderService implements IOrderService {

	private static OrderService orderService = null;
	private OrderController oController;

	public static OrderService getInstance() {
		if (orderService == null) {
			orderService = new OrderService();
		}
		return orderService;
	}

	private OrderService() {
		this.oController = new OrderController();
	}

	public ResponseT<OrderDTO> getOrderDetails(String orderId) { // RETURNING SPECIFIC DETAILS TO UI
		try {
			return new ResponseT<OrderDTO>(oController.getOrderDetails(Integer.valueOf(orderId)));
		} catch (Exception e) {
			return new ResponseT<OrderDTO>(e.getMessage());
		}
	}

	public ResponseT<Integer> createAnOrder(int supplierId, int branchId) { // CREATES NEW ORDER AND ADD IT TO @orders
		try {
			return oController.createAnOrder(supplierId, branchId);
		} catch (Exception e) {
			return new ResponseT<Integer>(e.getMessage());
		}

	}

	public Response addItemToCart(String orderId, String catalogItemId, String amount,int branchId) { // ADD ONE ITEM TO THE CART
		try {
			oController.addItemToCart(Integer.valueOf(orderId), Integer.valueOf(catalogItemId), Integer.valueOf(amount),branchId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}

	}

	public Response removeFromCart(String orderId, String catalogItemId,int branchId) { // REMOVES ONE ITEM FROM THE CART
		try {
			oController.removeFromCart(Integer.valueOf(orderId), Integer.valueOf(catalogItemId), branchId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response sendOrder(int orderId) { // CHANGES ORDER'S STATUS TO INPROGRESS
		try {
			oController.sendOrder(orderId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response endOrder(String orderId) { // CHANGES ORDER'S STATUS TO COMPLETE
		try {
			oController.endOrder(Integer.valueOf(orderId));
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public ResponseT<List<OrderDTO>> printOrdersFromSupplier(int supplierId, int branchId) { // PRINTS ALL ORDERS FROM SUPPLIER
		try {
			List<OrderDTO> list = oController.getOrdersOfSupplier(supplierId,branchId);
			return new ResponseT<List<OrderDTO>>(list);
		} catch (Exception e) {
			return new ResponseT<List<OrderDTO>>(e.getMessage());
		}
	}

	@Override
	public Response startScheduledOrder() {
		try {
			oController.startScheduledOrder();
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	@Override
	public Response purgeTimer() {
		try{
			oController.purgeTimer();
			return new Response();
		}catch(Exception e){
			return new Response(e.getMessage());
		}
	}

	@Override
	public Response subscribeScheduleOrder(int branchId, int supplierId, String day,HashMap<String, String> itemsToOrder) {
		try {
			oController.subscribeScheduleOrder(branchId, supplierId, DayOfWeek.valueOf(day).getValue(), changeItemsToOrderToInteger(itemsToOrder));
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	private HashMap<Integer,Integer> changeItemsToOrderToInteger(HashMap<String, String> itemsToOrder) throws Exception {
		HashMap<Integer,Integer> intItemsToOrder = new HashMap<Integer, Integer>();
		
		for (Entry<String, String> entry : itemsToOrder.entrySet()) {
			try{intItemsToOrder.put(Integer.valueOf(entry.getKey()), Integer.valueOf(entry.getValue()));}
			catch(Exception e) {throw new Exception("catalogItemID/Amount invalid");}
		}
			
			return intItemsToOrder;
		
	}

	@Override
	public ResponseT<List<OrderDTO>> getAllOpenOrdersByBranch(int branchId) {
		try {
			List<OrderDTO> orders = oController.getAllOpenOrdersByBranch(branchId);
			return new ResponseT<List<OrderDTO>>(orders);
		}catch (Exception e) {
			return new ResponseT<List<OrderDTO>>(e.getMessage());
		}
	}

	@Override
	public ResponseT<List<OrderDTO>> getAllOrdersByBranch(int branchId) {
		try {
			return new ResponseT<List<OrderDTO>>(oController.getAllOrdersByBranch(branchId));
		}catch (Exception e) {
			return new ResponseT<List<OrderDTO>>(e.getMessage());
		}
	}

	@Override
	public Response cancelOrder(String orderId) {
		try {
			oController.cancelOrder(Integer.valueOf(orderId));
			return new Response();
		}catch (Exception e) {
			return new Response(e.getMessage());
		}
	}
	/**
	 * Updates the amount recieved in a specific order
	 * @param orderId
	 * @param catalogItemId
	 * @param amount
	 * @return
	 */
	public Response updateAmount(int orderId, int catalogItemId, int amount) {
		try {
			oController.updateAmountRecieved(orderId, catalogItemId, amount);
			return new Response();
		}catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response loadFirstOrders() {
		try {
			oController.loadFirstData();
			return new Response();
		}catch(Exception e) {
			return new Response(e.getMessage());
		}
	}

}
