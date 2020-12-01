package bussinessLayer.BranchPackage;

import bussinessLayer.Transports.DeliveryPackage.Delivery;
import DataAccessLaye.Repo;
import ServiceLayer.DeliveryService;
import ServiceLayer.OrderService;
import bussinessLayer.DTOPackage.BranchDTO;
import bussinessLayer.DTOPackage.CartDTO;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;
import bussinessLayer.Transports.DeliveryPackage.Location;
import bussinessLayer.Transports.DeliveryPackage.LocationController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BranchController {
    // static variable single_instance of type Singleton
    private static BranchController single_instance = null;
    private DeliveryService deliveryService;
    private OrderService orderService = OrderService.getInstance();

    // variable of type String
    private Map<Integer, Branch> branches;
    private int idCounter;

    // private constructor restricted to this class itself
    private BranchController() {
        this.branches = new HashMap<>();
        this.idCounter = 0;
        this.deliveryService = DeliveryService.getInstance();
    }

    // static method to create instance of Singleton class
    public static BranchController getInstance() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new BranchController();
        }
        return single_instance;
    }

    //    change order status to 'DONE': func. endOrder(String orderId) in OrderService
    //  update order amounts: func. updateAmount(int catalogItemId, int amountRcvd) in OrderService
    //return true if order needs to be closed, else false.
    public boolean receiveDelivery(int deliveryId) throws Exception {
        int totalItemsRcvd = 0;
        this.branches = getBranches();

        Delivery delivery = deliveryService.getDelivery(String.valueOf(deliveryId));
        System.out.println("Receiving delivery "+deliveryId);

        CartDTO cartDTO = delivery.getOrders().getCart();
        int itemId = -1;

        if(deliveryService.getDelivery(String.valueOf(deliveryId)).getStatus().compareTo(Delivery.Status.Created) == 0)
            throw new Exception("you can only receive a InTransit or Delivered delivery");

        for (LineCatalogItemDTO lineItem : cartDTO.getLineItems()) {
            itemId = lineItem.getCatalogItem().getItemId();
            if (!delivery.getAmountById().keySet().contains(itemId))
                continue;
            System.out.println("current item: id: "+itemId +". amount ordered: "+
                            ( lineItem.getAmount() - lineItem.getAmountRecieved() ) +
                    ", amount received: "+delivery.getAmountById().get(itemId));
            System.out.println("Please enter number of damaged items: ");
            Scanner scanner = new Scanner(System.in);
            int amountDamaged = 0;
            amountDamaged = Integer.parseInt(scanner.nextLine());
            if (amountDamaged < 0) {
                System.out.println("amount cannot be a negative number. damaged amount has been set to 0");
                amountDamaged = 0;
            }
            if (amountDamaged>0)
                branches.get(delivery.getOrders().getBranchId()).updateDamagedItem(itemId, amountDamaged);

            System.out.println("Please enter received items amount - WITHOUT damaged amount:");
            int amountRcvd = 0;
            amountRcvd = Integer.parseInt(scanner.nextLine());
            if (amountRcvd < 0) {
                System.out.println("amount cannot be a negative number. received amount has been set to 0");
                amountRcvd = 0;
            }
            if (amountRcvd > 0){
                branches.get(delivery.getOrders().getBranchId()).editStockQuantity(itemId, amountRcvd);
                //TODO: update order amounts: invoke func. updateAmount(int catalogItemId, int amountRcvd) in OrderService
                orderService.updateAmount(delivery.getOrders().getOrderId(), lineItem.getCatalogItemId(), amountRcvd);
            }
            totalItemsRcvd += amountRcvd;

        }

        orderService.endOrder(String.valueOf(delivery.getOrders().getOrderId()));

        if ( totalItemsRcvd >= cartDTO.getTotalAmount())
            return true;
        return false;

    }


    public int getIdCounter() throws SQLException {
        //return idCounter;
        return Repo.getInstance().getAllBranches().size();
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
        //Repo.getInstance().
    }

    public int createBranch(String description) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Cannot create branch without a description");
        }
        this.idCounter++;
        Branch branch = new Branch(idCounter, description);
        Repo.getInstance().createBranch(branch.convertToDTO());
        return this.idCounter;
    }
    public int createBranch(int branchId, String description) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Cannot create branch without a description");
        }
        this.idCounter++;
        Branch branch = new Branch(branchId, description);
        Repo.getInstance().createBranch(branch.convertToDTO());
        return branchId;
    }

    public void editBranchDescription(int branchId, String description) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Cannot change to empty description");
        }
        //this.branches.get(branchId).setDescription(description);
        Repo.getInstance().updateBranchDescription(branchId, description);
    }


    //**NOT YET IMPLEMENTED WITH REPO
    public void deleteBranch(int branchId) throws Exception {
        if (!this.branches.keySet().contains(branchId)) {
            throw new Exception("Branch not found");
        }
        this.branches.remove(branchId);
    }

    public Map<Integer, Branch> getBranches() throws SQLException {
        List<BranchDTO> list = Repo.getInstance().getAllBranches();
        Map<Integer, Branch> map = new HashMap<>();
        for (BranchDTO branchDTO:list) {
            map.put(branchDTO.getId(), branchDTO.convertFromDTO());
        }
        return map;
        //return branches;

    }

    public void setBranches(Map<Integer, Branch> branches) {
        this.branches = branches;
    }

	public BranchDTO getBranchDTOById(String branchId) throws Exception{
		return Repo.getInstance().getBranchById(Integer.valueOf(branchId));
	}

	public List<BranchDTO> getAllDTOBranches() throws Exception {
		return Repo.getInstance().getAllBranches();
	}
}
