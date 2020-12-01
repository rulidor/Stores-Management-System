package bussinessLayer.OrderPackage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import DataAccessLaye.Repo;
import ServiceLayer.ResponseT;
import bussinessLayer.DTOPackage.OrderDTO;
import bussinessLayer.DTOPackage.ScheduledDTO;
import bussinessLayer.SupplierPackage.Supplier;

public class OrderController {

	public OrderController() {
	}

	private bussinessLayer.OrderPackage.Order getOrder(int orderId) throws Exception {
		return new Order(Repo.getInstance().getOrderByID(orderId));
	}

	private List<Order> getOrders(int branchId) throws Exception {
		List<OrderDTO> ordersDTO = Repo.getInstance().getAllOrderByBranchId(branchId);
		return changeDTOListToBuissness(ordersDTO);

	}

	public bussinessLayer.DTOPackage.OrderDTO getOrderDetails(int orderId) throws Exception {
		Order o = getOrder(orderId);
		return o.converToDTO();
	}

	public ResponseT<Integer> createAnOrder(int supplierId, int branchId) throws Exception {
		Order o = new Order(new Supplier(Repo.getInstance().getSupplierById(supplierId)), branchId);
		if (Repo.getInstance().isThereScheduledOrderForNextDay(supplierId, branchId, o.getDeliveryDate().getDayOfWeek().getValue()) || Repo.getInstance().getOrderByDateSupplier(supplierId, branchId, o.getDeliveryDate())) { //maybe problem
			return new ResponseT<Integer>(Repo.getInstance().getOrderIdBy(supplierId, branchId, o.getDeliveryDate().getDayOfYear(), o.getDeliveryDate().getYear()));
		}
		int orderId = -1;
		try {
			orderId = Repo.getInstance().insertOrder(o.converToDTO());
			ScheduledHandler.getInstance().addChangeToProgress(orderId, o.getDeliveryDate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (orderId > 0) {
			return new ResponseT<Integer>(orderId);
		} else return new ResponseT<Integer>("Error occured");
	}

	public void addItemToCart(int orderId, int catalogItemId, int amount, int branchId) throws Exception {
		Order order = getOrder(orderId);
		if (order.getBranchId() != branchId) throw new Exception("This order belongs to other branch");
		order.addItemToCart(catalogItemId, amount);
		try {
			Repo.getInstance().insertLineCatalogItem(order.getLineCatalogItemDTO(catalogItemId), orderId);
			return;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		try {
			Repo.getInstance().updateOrder(order.converToDTO());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void removeFromCart(int orderId, int catalogItemId, int branchId) throws Exception {
		Order o = getOrder(orderId);
		if (o.getBranchId() != branchId) throw new Exception("This order belongs to other branch");
		o.removeFromCart(catalogItemId);
		Repo.getInstance().deleteItemFromOrder(catalogItemId, orderId);
	}

	public void sendOrder(int orderId) throws Exception {
		Order order = getOrder(orderId);
		order.sendOrder();
		Repo.getInstance().updateOrderStatus(order.getSupplierId(), order.getBranchId(),
				order.getDeliveryDate().getDayOfYear(), order.getDeliveryDate().getYear(), order.getOrderStatus().toString());

	}

	public void endOrder(int orderId) throws Exception {
		Order order = getOrder(orderId);
		order.endOrder();
		//Repo.getInstance().updateOrderStatus(order.getSupplierId(), order.getBranchId(),
			//	order.getDeliveryDate().getDayOfYear(), order.getDeliveryDate().getYear(), order.getOrderStatus().toString());
		Repo.getInstance().updateAnOrderStatusById(order.getOrderId(), "COMPLETE");
	}

	public List<bussinessLayer.DTOPackage.OrderDTO> getOrdersOfSupplier(int supplierId, int branchId) throws Exception {
		List<Order> orders = getOrders(branchId);
		List<bussinessLayer.DTOPackage.OrderDTO> DTOlist = new ArrayList<bussinessLayer.DTOPackage.OrderDTO>();
		for (Order order : orders) {
			if (order.getSupplierId() == supplierId) {
				DTOlist.add(order.converToDTO());
			}
		}

		return DTOlist;
	}

	public void startScheduledOrder() throws SQLException {
		ScheduledHandler.getInstance().start();
	}

	public void subscribeScheduleOrder(int branchId, int supplierId, int day, HashMap<Integer, Integer> itemsToOrder) throws Exception {
		ScheduledDTO schedule = new ScheduledDTO(day, supplierId, itemsToOrder, branchId);
		isScheduleValid(schedule);
		Repo.getInstance().insertScheduled(schedule);
		ScheduledHandler.getInstance().addSchedule(schedule);
	}

	private void isScheduleValid(ScheduledDTO schedule) throws Exception {
		Repo.getInstance().getBranchById(schedule.getBranchId());
		Supplier supplier = new Supplier(Repo.getInstance().getSupplierById(schedule.getSupplierId()));
		supplier.isDayValidDelivery(schedule.getDay());
		isItemsValid(schedule.getItemsToOrder(), supplier);
	}

	private void isItemsValid(HashMap<Integer, Integer> hashMap,
							  bussinessLayer.SupplierPackage.Supplier supplier) throws Exception {
		for (Entry<Integer, Integer> entry : hashMap.entrySet()) {
			if (entry.getValue() <= 0) throw new Exception("Amount is not valid");
			supplier.getCatalogItem(entry.getKey());
		}
	}

	public void purgeTimer() {
		ScheduledHandler.getInstance().getTimer().cancel();
	}

	public List<OrderDTO> getAllOpenOrdersByBranch(int branchId) throws Exception {
		return changeListToDTO(changeDTOListToBuissness(Repo.getInstance().getAllOpenOrdersByBranch(branchId)));
	}

	public List<OrderDTO> getAllOrdersByBranch(int branchId) throws Exception {
		return changeListToDTO(getOrders(branchId));
	}

	private List<OrderDTO> changeListToDTO(List<Order> orders) {
		List<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();
		for (Order order : orders) {
			ordersDTO.add(order.converToDTO());
		}

		return ordersDTO;
	}

	private List<Order> changeDTOListToBuissness(List<OrderDTO> ordersDTO) throws Exception {
		List<Order> orders = new ArrayList<Order>();
		for (OrderDTO orderDTO : ordersDTO) {
			orders.add(new Order(orderDTO));
		}

		return orders;
	}

	public void cancelOrder(int orderId) throws Exception {
		Order order = getOrder(orderId);
		order.cancelOrder();
		Repo.getInstance().updateAnOrderStatusById(orderId, order.getOrderStatus().toString());
	}

	public void updateAmountRecieved(int orderId, int catalogItemId, int amount) throws Exception {
		Repo.getInstance().updateAmountRecieved(orderId, catalogItemId, amount);
	}

	public void loadFirstData() throws Exception {
//		createAnOrder(1, 1);
//		createAnOrder(2, 1);
//		createAnOrder(2, 2);
//		addItemToCart(1, 10, 20, 1);
//		addItemToCart(1, 11, 50, 1);
//		addItemToCart(2, 20, 25, 1);
//		addItemToCart(2, 21, 30, 1);
//		addItemToCart(3,22,10,2);
//		addItemToCart(3,23,15,2);
	}
}
