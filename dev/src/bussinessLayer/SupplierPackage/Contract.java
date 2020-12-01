package bussinessLayer.SupplierPackage;

import javafx.util.Pair;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bussinessLayer.DTOPackage.ContractDTO;
import bussinessLayer.DTOPackage.RangeDTO;

public class Contract {
    private boolean isDeliver;
    private Catalog catalog;
    private List<DayOfWeek> constDayDelivery;
    private int supplierId;
    private HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems;

    public Contract(boolean isDeliver, int supplierId) {
        this.isDeliver = isDeliver;
        this.catalog = new Catalog();
        this.supplierId = supplierId;
        this.discountByAmountItems = new HashMap<Integer, List<Pair<Range, Double>>>();
        this.constDayDelivery = new ArrayList<>();
    }

    public Contract(boolean isDeliver, Catalog catalog, List<DayOfWeek> constDayDelivery, int supplierId,
            HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems) {
        super();
        this.isDeliver = isDeliver;
        this.catalog = catalog;
        this.constDayDelivery = constDayDelivery;
        this.supplierId = supplierId;
        this.discountByAmountItems = discountByAmountItems;
    }

    public Contract(ContractDTO contractDTO) {
        isDeliver = contractDTO.getIsDeliver();
        catalog = new Catalog(contractDTO.getCatalog());
        constDayDelivery = contractDTO.getConstDayDelivery();
        supplierId = contractDTO.getSupplierId();
        discountByAmountItems = new HashMap<Integer,List<Pair<Range,Double>>>();
        convertToBuisDiscount(contractDTO.getDiscountByAmountItems());
    }

    private void convertToBuisDiscount(HashMap<Integer, List<Pair<RangeDTO, Double>>> discountByAmountItems) {
        for (Integer integer : discountByAmountItems.keySet()) {
            List<Pair<Range, Double>> ansForItem = new ArrayList<>();
            for (Pair<RangeDTO, Double> pairs : discountByAmountItems.get(integer)) {
                ansForItem.add(new Pair<Range,Double>(new Range(pairs.getKey()),pairs.getValue()));
            }
            this.discountByAmountItems.put(integer,ansForItem); }
    }

    public void setConstDayDeliveryByList(List<DayOfWeek> days) {
        this.constDayDelivery = days;
    }

    public void setConstDayDelivery(String day) {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
        if (!this.constDayDelivery.contains(dayOfWeek))
            this.constDayDelivery.add(dayOfWeek);
    }

    public void addToMap(int catalogItemId, int max, int min, double price) throws Exception {
        if (!discountByAmountItems.containsKey(catalogItemId)) {
            discountByAmountItems.put(catalogItemId, new ArrayList<>());
        }
        Range range = new Range(max, min);
        Pair<Range, Double> pair = new Pair<Range, Double>(range, price);
        if (!discountByAmountItems.get(catalogItemId).contains(pair)) {
            discountByAmountItems.get(catalogItemId).add(pair);
        } else {
            throw new Exception("please you cannot edit filed in the agreement just add new range of discount, to edit just add new agreement");
        }

    }

    public void removeItemFromMap(int catalogItem) throws Exception {
        if (discountByAmountItems.containsKey(catalogItem)) {
            discountByAmountItems.remove(catalogItem);
            return;
        }
        throw new Exception("catalog item do not found");
    }

    public List<DayOfWeek> getConstDayDeliviery() {
    	return constDayDelivery;
		/*
		 * String s = ""; if (isDeliver == false) { return s = s +
		 * "this supplier dose not do delivery, only pick-up"; }
		 * 
		 * String days = ""; for (DayOfWeek dayOfWeek : constDayDelivery) { days +=
		 * dayOfWeek.toString() + ","; } days = days.substring(0, days.length() - 1);
		 * return s = s + "Days of delivery: " + days;
		 */
    }

    public boolean isDeliver() {
        return isDeliver;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public HashMap<Integer, List<Pair<Range, Double>>> getDiscountByAmountItems() throws SQLException {
      /*  HashMap<Integer,List<Pair<RangeDTO,Double>>> temp = Repo.getInstance().getAllRangesByContract(this.supplierId);
            HashMap<Integer,List<Pair<Range,Double>>> ans = new HashMap<>();
        for (Integer it : temp.keySet()) {
            List<Pair<RangeDTO,Double>> list = temp.get(it);
            List<Pair<Range,Double>> ansForItemId = new ArrayList<>();
            for (Pair<RangeDTO,Double> pair: list ) {
                Range r = new Range(pair.getKey());
                Pair<Range,Double> Pair = new Pair(r,pair.getValue());
                ansForItemId.add(Pair);
            }
            ans.put(it,ansForItemId);

        }
        return ans;*/
        return this.discountByAmountItems;
    }

    public void setDeliver(boolean deliver) {
        isDeliver = deliver;
        if (!isDeliver) {
            constDayDelivery.clear();
        }
    }

    public void addNewItemToCatalog(int itemId, int catalogId, double price,String description) throws Exception {
        CatalogItem catalogItem = new CatalogItem(itemId, catalogId, price,description);
        if (!catalog.getItems().contains(catalogItem)) {
            addItemToCatalog(catalogItem);
            return;
        }
        throw new Exception("catalog already contain the item");

    }

    public void addItemToCatalog(CatalogItem catalogItem) {
        this.catalog.addItemToCatalog(catalogItem);
    }

    public void removeItemFromCatalog(CatalogItem catalogItem) throws Exception {
        this.catalog.removItemFromList(catalogItem);
        removeItemFromMap(catalogItem.getCatalogItemId());

    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setDiscountByAmountItems(HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems) {
        this.discountByAmountItems = discountByAmountItems;
    }

    public CatalogItem getCatalogItem(int catalogItemId) throws Exception {
        return catalog.getCatalogItem(catalogItemId);
    }

    public LocalDateTime getNextDateOfDelivery() {
        LocalDateTime now = LocalDateTime.now();
        if (!isDeliver()) {
            //TODO NEED TO CHANGE THIS TO CALL TO ARRANGE PICKUP
            return now.plusDays(1);
        }

        if (constDayDelivery.isEmpty()) {
            return now.plusDays(1);
        }

        int minDay = 8;
        for (DayOfWeek dw : constDayDelivery) { //LOOPING OVER CONSTDAY.. AND DECIDE WHICH THE DAY OF DELIVERY IS THE CLOSEST AND RETURN IT
            int diff = now.getDayOfWeek().getValue() - dw.getValue(); // THIS DAY - DAYOFREGULARDELIVERY
            if (diff < 0) {
                diff = (-1) * diff;
            } else if (diff > 0) {
                diff = 7 - diff;
            }
            if (minDay > diff && diff != 0) minDay = diff; //IF THE DIFF!=0 MEANING ITS NOT TODAY
        }

        return now.plusDays(minDay);
    }

    public void cleanRangeListItemFromMap(int catalogItemId) throws Exception {
        if (discountByAmountItems.containsKey(catalogItemId)) {
            discountByAmountItems.get(catalogItemId).clear();
        }
        throw new Exception("catalog item do not found");
    }

	public void isDayValidDelivery(DayOfWeek day)throws Exception {
        if (day == null) throw new Exception("Day is not Valid");
        if(!isDeliver() || constDayDelivery.isEmpty()) return;
		for(DayOfWeek d : constDayDelivery){
            if(d.getValue() == day.getValue()) return;
        }

        throw new Exception("Supplier Doesnt supply products at " + day.name() + "'s'");
	}

    public int getCatalogItemIdByItemId(Integer itemId) {
        return this.catalog.getcatalogItemIdByItemId(itemId);
    }

	public ContractDTO convertToDTO() {
		return new ContractDTO(supplierId, constDayDelivery, isDeliver, catalog.convertToDTO(), convertDiscountToDTO());
	}

	private HashMap<Integer, List<Pair<RangeDTO, Double>>> convertDiscountToDTO() {
		HashMap<Integer, List<Pair<RangeDTO, Double>>> disc = new HashMap<Integer, List<Pair<RangeDTO,Double>>>();
		for (Integer catalogItemId : discountByAmountItems.keySet()) {
			disc.putIfAbsent(catalogItemId, new ArrayList<Pair<RangeDTO,Double>>());
			List<Pair<RangeDTO,Double>> list = disc.get(catalogItemId);
			for (Pair<Range,Double> pair : discountByAmountItems.get(catalogItemId)) {
				list.add(new Pair<RangeDTO,Double>(pair.getKey().convertToDTO(),pair.getValue()));
			}
		}
		
		return disc;
	}
}
