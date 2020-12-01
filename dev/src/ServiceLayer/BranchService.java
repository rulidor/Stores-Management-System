package ServiceLayer;

import DataAccessLaye.*;

import MessageTypes.Damaged;
import MessageTypes.ItemWarning;
import MessageTypes.StockReport;
import MessageTypes.ToOrder;
import bussinessLayer.DTOPackage.BranchDTO;
import bussinessLayer.DTOPackage.OrderDTO;
import bussinessLayer.BranchPackage.Branch;
import bussinessLayer.BranchPackage.BranchController;
import bussinessLayer.OrderPackage.Order;
import bussinessLayer.OrderPackage.ScheduledHandler;
import bussinessLayer.SupplierPackage.Supplier;

import java.sql.SQLException;
import java.util.*;

public class BranchService {
    private BranchController branchController;
    private DeliveryService deliveryService;

    public BranchService() {
        this.branchController = BranchController.getInstance();
        this.deliveryService = DeliveryService.getInstance();
    }

    public Response receiveDelivery(int deliveryId){
        try{
            boolean isToBeClosed = branchController.receiveDelivery(deliveryId);
            if (isToBeClosed)
                return new Response("Order received completely and has been closed");
            return new Response("Order partially received and has been updated");
        }catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response updateItemShelfQuantity(int branchId, int itemId, int delta) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            this.branchController.getBranches().get(branchId).editShelfQuantity(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Shelf quantity was edited, for branch id: " + branchId);
        return response;
    }

    public void updateBranchController() throws SQLException {
        List<BranchDTO> list = Repo.getInstance().getAllBranches();
        branchController.setIdCounter(list.size());
        Map<Integer, Branch> map = new HashMap<>();
        for (BranchDTO branchDTO: list)
        {
            map.put(branchDTO.getId(), branchDTO.convertFromDTO());
        }
    }

    public Response createBranch(String description) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        int id = 0;
        try {
            id = this.branchController.createBranch(description);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Branch was created successfully, with id: " + id);
        return response;
    }
    public Response createBranch(int branchId, String description) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        int id = branchId;
        try {
            this.branchController.createBranch(description);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Branch was created successfully, with id: " + branchId);
        return response;
    }

    public Response updateItemStockQuantity(int branchId, int itemId, int delta) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            this.branchController.getBranches().get(branchId).editStockQuantity(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Stock quantity was edited, for branch id: " + branchId);
        return response;
    }

    public Response cancelCard(int branchId, int itemId, int quantityToCancel) {
        if(quantityToCancel<0)
            return new Response("Error - cannot receive a negative number to cancel card");
        return updateItemShelfQuantity(branchId, itemId, quantityToCancel*(-1));
//        try {
//            updateBranchController();
//        }
//        catch (Exception e) {
//            return new Response(e.getMessage());
//        }
//        try {
//            this.branchController.getBranches().get(branchId).cancelCard(itemId, quantityToCancel);
//        } catch (Exception e) {
//            return new Response(e.getMessage());
//        }
//        Response response = new Response();
//        response.setMessage("Quantity was updated according to cancel card");
//        return response;
    }

    public Response updateBranchDescription(int branchId, String description) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            this.branchController.getBranches().get(branchId).setDescription(description);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Description was edited, for branch id: " + branchId);
        return response;
    }

    public Response updateDamagedItem(int branchId, int itemId, int delta) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
        try {
            this.branchController.getBranches().get(branchId).updateDamagedItem(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Damaged quantity for item " + itemId + "was updated, at branchId " + branchId);
        return response;
    }

    /*
     * arguments: string of categories: category, subCategory, sub2Category. to
     * generate report for all of the items, input empty array.
     */
    public ResponseT<StockReport> generateStockReport(int branchId, String[] categories) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new ResponseT<StockReport>(e.getMessage());
        }

        StockReport report = null;
        try {
            report = this.branchController.getBranches().get(branchId).generateStockReport(categories);
        } catch (SQLException throwables) {
            return new ResponseT<StockReport>(throwables.getSQLState());
        }
        return new ResponseT<StockReport>(report);
    }

    public ResponseT<Damaged> generateDamagedReport(int branchId) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new ResponseT<Damaged>(e.getMessage());
        }
        Damaged report = new Damaged(new HashMap<>());
        try {
            report.setDamagedById(this.branchController.getBranches().get(branchId).generateDamagedReport());
        } catch (SQLException throwables) {
            return new ResponseT<Damaged>(throwables.getSQLState());
        }

        return new ResponseT<Damaged>(report);
    }

    public ResponseT<ItemWarning> generateWarningReport(int branchId) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new ResponseT<ItemWarning>(e.getMessage());
        }
        ItemWarning report = new ItemWarning(new HashMap<>());
        try {
            report.setWarningById(this.branchController.getBranches().get(branchId).generateWarningReport());
        } catch (SQLException throwables) {
            return new ResponseT<ItemWarning>(throwables.getSQLState());
        }

        return new ResponseT<ItemWarning>(report);
    }

    public ResponseT<ToOrder> generateToOrderReport(int branchId) {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new ResponseT<ToOrder>(e.getMessage());
        }
        ToOrder report = new ToOrder();
        try {
            report.setOrderById(this.branchController.getBranches().get(branchId).generateToOrderReport());
        } catch (SQLException throwables) {
            return new ResponseT<ToOrder>(throwables.getSQLState());
        }
        // DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        // System.out.println(df.format(report.dateProduced));

        return new ResponseT<ToOrder>(report);
    }

    public ResponseT<ToOrder> generateAndSendOrder(int branchId, List<Supplier> suppliersForBranchId) throws Exception {
        try {
            updateBranchController();
        }
        catch (Exception e) {
            return new ResponseT<ToOrder>(e.getMessage());
        }
        int ctalogItemId = -1;
        int chosenForAnItem = -1; //represents supplier id that is cheapest
        double priceAfterDiscount = -1;
        boolean foundExistOrderBySup = false;
        ResponseT<ToOrder> report = this.generateToOrderReport(branchId);
        double cheapestPriceForItem = -1;
        List<Order> orderList = new ArrayList<>();
        Supplier chosenSup = suppliersForBranchId.get(0);
        for (Integer itemId : report.getObj().getOrderById().keySet()) {
            for (Supplier sup : suppliersForBranchId) {
                ctalogItemId = -1 ;
                try {
                    ctalogItemId = sup.getCatalogItemIdByItem(itemId);
                }
                catch (Exception e)
                {
                    continue;
                }
                try {
                    priceAfterDiscount = sup.getPriceForItemWithAmountAfterDiscount(ctalogItemId, report.getObj().getOrderById().get(itemId)); //arg. 2 returns amount to order
                }
                catch (Exception e) {
                    continue;
                }

                if (cheapestPriceForItem == -1 || priceAfterDiscount < cheapestPriceForItem) {
                    cheapestPriceForItem = priceAfterDiscount;
                    chosenForAnItem = sup.getSupplierId();
                    chosenSup = sup;
                }
            }

               if (ctalogItemId == -1 && chosenSup == null) {
                   System.out.println("Item id " + itemId + " is not exists in suppliers catalogs");
                    continue;
               }

                if (orderList.size() > 0) {
                    for (Order order : orderList) {
                        if (order.getSupplierId() == chosenForAnItem) {
                            order.addItemToCart(chosenSup.getCatalogItemIdByItem(itemId), report.getObj().getOrderById().get(itemId));
                            foundExistOrderBySup = true;
                            break;
                        }
                        else continue;
                   /*     if (foundExistOrderBySup){
                            cheapestPriceForItem = -1 ;
                            chosenForAnItem = -1;
                            chosenSup = null;
                            break;
                        }*/
                    }
                    if (!foundExistOrderBySup)
                    {
                        Order o = new Order(chosenSup,branchId);
                        o.addItemToCart(chosenSup.getCatalogItemIdByItem(itemId),report.getObj().getOrderById().get(itemId));
                        orderList.add(o);
                    }
                }
                else{
                    orderList.add(new Order(chosenSup, branchId));
                    orderList.get(0).addItemToCart(chosenSup.getCatalogItemIdByItem(itemId), report.getObj().getOrderById().get(itemId));
                    foundExistOrderBySup = false;
                }
            cheapestPriceForItem = -1 ;
            chosenForAnItem = -1;
            chosenSup = null;
            }

        for (Order order : orderList) {
            OrderDTO orderDTO = order.converToDTO();

            int orderId = Repo.getInstance().insertOrder(orderDTO);
            ScheduledHandler.getInstance().addChangeToProgress(orderId, orderDTO.getDeliveryDate());

            //Repo.getInstance().insertOrder(orderDTO);
        }
        return report;
    }

	public ResponseT<BranchDTO> getBranchDTOById(String branchId) {
		try {
			return new ResponseT<BranchDTO>(branchController.getBranchDTOById(branchId));
		}catch (Exception e) {
			return new ResponseT<BranchDTO>(e.getMessage());
		}
	}

	public ResponseT<List<BranchDTO>> getAllDTOBranches() {
		try {
			return new ResponseT<List<BranchDTO>>(branchController.getAllDTOBranches());
		}catch (Exception e) {
			return new ResponseT<List<BranchDTO>>(e.getMessage());
		}
		
	}

}
