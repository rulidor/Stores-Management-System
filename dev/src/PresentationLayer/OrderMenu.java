package PresentationLayer;

import java.util.HashMap;
import java.util.List;

import PresentationLayer.MainUserInterface.Job;
import ServiceLayer.*;
import bussinessLayer.DTOPackage.*;

public class OrderMenu {

	private BranchService branchService = new BranchService();
	private ISupplierService supplierService = SupplierService.getInstance();
	private IOrderService orderService = OrderService.getInstance();
	private int branchId=-1;

	public void manageOrders() {
		String input="";

		MainUserInterface.printAllBranches();
		System.out.println("Enter branchId");
		ResponseT<BranchDTO> response = branchService.getBranchDTOById(MainUserInterface.getUserInput());
		if(response.isErrorOccured()) {
			System.out.println(response.getMessage());
			return;
		}

		branchId = response.getObj().getId();

		do {
			printManageOrdersMenu();

			input = MainUserInterface.getUserInput();
			switch (input) {
				case "1":
					makeAnOrder();
					break;
				case "2":
					printOrdersFromSupplier(); // PRINTS ALL ORDERS FROM SUPPLIER
					break;
				case "3":
					endOrder(); // CHANGE ORDER'S STATUS TO INPROGRESS
					break;
				case "4":
					getOrderDetails();// GET ORDER DETAILS OF A specific order
					break;
				case "5":
					subscribeToScheduled();
					break;
				case "6":
					addItemToOpenOrders();
					break;
				case "7":
					removeItemFromOpenOrders();
					break;
				case "8":
					printAllOrders();
					break;
				case "9":
					printOpenOrders();
					break;
				case "10":
					removeOrder();
					break;
				default:
					System.out.println("Invalid Input");
			}

		} while (!input.equals("11"));
	}

	private void removeOrder() {



		printAllOrders();

		System.out.println("Enter orderID you would like to cancel:");

		String orderId = MainUserInterface.getUserInput();

		if(!MainUserInterface.job.toString().equals(Job.HR.toString())){
			System.out.println("HR Confirmation y/n");
			String conf = MainUserInterface.getUserInput();
			if(conf.equals("y"));
			else{System.out.println("not confirmed"); return;}
		}


		if(!MainUserInterface.job.toString().equals(Job.LOGISTICMANAGER.toString())){
			System.out.println("LogisticManager Confirmation y/n");
			String conf = MainUserInterface.getUserInput();
			if(conf.equals("y"));
			else{System.out.println("not confirmed"); return;}
		}

		if(!MainUserInterface.job.toString().equals(Job.STOCKMANAGER.toString())){
			System.out.println("LogisticManager Confirmation y/n");
			String conf = MainUserInterface.getUserInput();
			if(conf.equals("y")) ;
			else{System.out.println("not confirmed"); return;}
		}

		orderService.cancelOrder(orderId);

	}

	/**
	 * Prints the Manage orders menu options
	 */
	private void printManageOrdersMenu() {
		System.out.println("1) Make an order\n2) Print all orders from supplier\n3) End order\n4) Get order details\n"
				+"5) Subscribe to schedule order\n6) Add Items To Open Orders\n7) Remove Item from Open Orders\n"
				+"8) Print All Branch Orders\n9) Print Open Orders\n10) Cancel order\n11) Return to previous Menu");

	}

	private void makeAnOrder() {
		String input = "";
		System.out.println("This will create new order if there is none already exist in the next delivery date\n"
				+ "or, it will add/remove to the nearest order");
		MainUserInterface.printSuppliers();
		System.out.println("Enter supplier ID:");
		ResponseT<SupplierDTO> supplierResponse = supplierService.getSupplierInfo(MainUserInterface.getUserInput());
		if(supplierResponse.isErrorOccured()) {
			System.out.println(supplierResponse.getMessage());
			return;
		}

		int supplierId = supplierResponse.getObj().getSupplierId();

		ResponseT<Integer> response = orderService.createAnOrder(supplierResponse.getObj().getSupplierId(), branchId);

		if(response.isErrorOccured()) {
			System.out.println(response.getMessage());
			return;
		}

		int orderId = response.getObj();

		do {
			System.out.println(orderService.getOrderDetails(String.valueOf(orderId)).getObj());
			System.out.println("1) Add item\n2) Remove item\n3) Return");

			input = MainUserInterface.getUserInput();

			switch (input) {

				case "1":
					addItemToCart(orderId, supplierId); // ADD ITEM TO CART
					break;
				case "2":
					removeItemFromCart(orderId, supplierId); // REMOVES AN ITEM FROM CART
					break;
				case "3": //RETURN
					break;
				default:
					System.out.println("Invalid Input try again");
					break;

			}
		} while (!input.equals("3"));
		System.out.println("Remember!Your order will be closed to changes automatically 24hours before its arrival!");
	}

	/**
	 * Adding Item to order
	 *
	 * @param orderId The order ID which we want to add to
	 */
	private void addItemToCart(int orderId, int supplierId) {

		if(MainUserInterface.printCatalogItemsOfSupplier(String.valueOf(supplierId))) return;

		System.out.println("Enter as Follow: CatalogItemId:Amount");
		String input = MainUserInterface.getUserInput();
		if (input.equals("b"))
			return;
		String split[] = input.split(":");
		System.out.println(orderService.addItemToCart(String.valueOf(orderId), split[0], split[1],branchId).getMessage());
	}

	/**
	 * Removing Item to order
	 *
	 * @param orderId The order ID which we want to remove the item from
	 * @param branchId
	 */
	private void removeItemFromCart(int orderId,int supplierId) { // REMOVES AN ITEM FROM CART
		if(MainUserInterface.printCatalogItemsOfSupplier(String.valueOf(supplierId))) return;
		System.out.println("Enter catalog item ID:");
		String catalogItemId = MainUserInterface.getUserInput();
		if (catalogItemId.equals("b"))
			return;
		System.out.println(orderService.removeFromCart(String.valueOf(orderId), catalogItemId,branchId).getMessage());
	}

	/**
	 * Prints all orders from a certain supplier
	 *
	 * @param supplierId The supplier ID
	 */
	private void printOrdersFromSupplier() {
		MainUserInterface.printSuppliers();
		System.out.println("Enter supplier ID:");
		ResponseT<SupplierDTO> supplierResponse = supplierService.getSupplierInfo(MainUserInterface.getUserInput());
		if(supplierResponse.isErrorOccured()) {
			System.out.println(supplierResponse.getMessage());
			return;
		}

		int supplierId = supplierResponse.getObj().getSupplierId();

		ResponseT<List<OrderDTO>> r = orderService.printOrdersFromSupplier(supplierId,branchId);

		for (OrderDTO order : r.getObj()) {
			System.out.println(order + "\n");

		}
	}

	/**
	 * End order means the order arrived
	 */
	private void endOrder() { // CHANGES ORDER STATUS TO COMPLETE
		printAllOrders();
		System.out.println("Enter order ID or \"b\" to return:");
		String orderId = MainUserInterface.getUserInput();
		if (orderId.equals("b"))
			return;
		System.out.println(orderService.endOrder(orderId).getMessage());
	}


	private void printAllOrders() {
		ResponseT<List<OrderDTO>> orders = orderService.getAllOrdersByBranch(branchId);

		if(orders.isErrorOccured()) {
			System.out.println(orders.getMessage());
			return;
		}
		if(orders.getObj().size() == 0) {
			System.out.println("There are no orders at all!");
			return;
		}

		for (OrderDTO it : orders.getObj()) {
			System.out.println(it + "\n");
		}

	}

	/**
	 * Print specific Order
	 */
	private void getOrderDetails() { // GET ORDER DETAILS OF ORDER NO.@orderId
		System.out.println("Enter order ID:");
		String orderId = MainUserInterface.getUserInput();
		if (orderId.equals("b"))
			return;
		System.out.println(orderService.getOrderDetails(orderId).getObj());
	}

	private void subscribeToScheduled() {
		MainUserInterface.printSuppliers();
		System.out.println("Enter supplier ID:");
		ResponseT<SupplierDTO> supplierResponse = supplierService.getSupplierInfo(MainUserInterface.getUserInput());
		if(supplierResponse.isErrorOccured()) {
			System.out.println(supplierResponse.getMessage());
			return;
		}

		int supplierId = supplierResponse.getObj().getSupplierId();
		System.out.println(supplierService.getContractDetails(supplierId).getObj());

		System.out.println("Please enter the following data:");
		System.out.println("The scheduled day name in CAPITAL LETTERS: ");
		String day = MainUserInterface.getUserInput();

		HashMap<String,String> itemsToOrder = new HashMap<String, String>();
		String catalogItemId ="";
		String amount = "";
		while(true) {
			System.out.println(supplierService.getCatalog(supplierId).getObj());
			System.out.println("Enter Catalog Item ID if u done press b:");

			catalogItemId = MainUserInterface.getUserInput();
			if(itemsToOrder.containsKey(catalogItemId)) {
				System.out.println("Already exist in scheduled this will run over the previous amount");
			}
			if(catalogItemId.equals(("b")))break;
			System.out.println("Enter amount:");
			amount = MainUserInterface.getUserInput();
			itemsToOrder.put(catalogItemId, amount);

		}
		System.out.println(orderService.subscribeScheduleOrder(branchId, supplierId, day, itemsToOrder).getMessage());
	}


	private void addItemToOpenOrders() {
		if(printOpenOrders()) {
			return;
		}
		System.out.println("Please Enter OrderId: ");
		String orderId = MainUserInterface.getUserInput();
		ResponseT<OrderDTO> response = orderService.getOrderDetails(orderId);
		if(response.isErrorOccured()) {
			System.out.println(response.getMessage());
			return;
		}
		System.out.println(MainUserInterface.printCatalogItemsOfSupplier(String.valueOf(response.getObj().getSupplierId())));
		System.out.println("Please Enter catalog Item ID:");
		String catalogItemId = MainUserInterface.getUserInput();
		System.out.println("Please Enter amount:");
		String amount = MainUserInterface.getUserInput();
		System.out.println(orderService.addItemToCart(orderId, catalogItemId, amount,branchId).getMessage());

	}

	private boolean printOpenOrders() {
		ResponseT<List<OrderDTO>> order = orderService.getAllOpenOrdersByBranch(branchId);
		if(order.isErrorOccured()) {
			System.out.println(order.getMessage());
			return true;
		}
		for (OrderDTO it : order.getObj()) {
			System.out.println(it +"\n");
		}
		return false;

	}

	private void removeItemFromOpenOrders() {
		if(printOpenOrders()) {
			return;
		}
		System.out.println("Please Enter OrderId: ");
		String orderId = MainUserInterface.getUserInput();
		ResponseT<OrderDTO> response = orderService.getOrderDetails(orderId);
		if(response.isErrorOccured()) {
			System.out.println(response.getMessage());
			return;
		}
		MainUserInterface.printCatalogItemsOfSupplier(String.valueOf(response.getObj().getSupplierId()));
		System.out.println("Please Enter catalog Item ID:");
		String catalogItemId = MainUserInterface.getUserInput();
		System.out.println(orderService.removeFromCart(orderId, catalogItemId, branchId).getMessage());

	}


}