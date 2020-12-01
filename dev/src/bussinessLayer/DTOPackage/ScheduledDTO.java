package bussinessLayer.DTOPackage;

import java.time.DayOfWeek;
import java.util.HashMap;


public class ScheduledDTO {

    private DayOfWeek day;
    private int supplierId;
    private int branchId;
    private HashMap<Integer, Integer> itemsToOrder;

    public ScheduledDTO(int day, int supplierId, HashMap<Integer, Integer> itemsToOrder,int branchId) {
        try{this.day = DayOfWeek.of(day);} catch(Exception e){ this.day = null;}
        this.supplierId = supplierId;
        this.itemsToOrder = itemsToOrder;
        this.branchId = branchId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public int getSupplierId() {
        return supplierId;
    }

    /**
     * @return the itemsToOrder
     */
    public HashMap<Integer, Integer> getItemsToOrder() {
        return itemsToOrder;
    }

    public int getBranchId(){
        return this.branchId;
    }


}