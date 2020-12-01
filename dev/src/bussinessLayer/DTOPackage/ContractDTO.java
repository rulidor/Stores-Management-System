package bussinessLayer.DTOPackage;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;

import javafx.util.Pair;

public class ContractDTO {

	private List<DayOfWeek> constDayDelivery;
	private int supplierId;
	private boolean isDeliver;
	private CatalogDTO catalog;
	private HashMap<Integer, List<Pair<RangeDTO, Double>>> discountByAmountItems;

	public ContractDTO(int supplierId, List<DayOfWeek> constDayDeliviery, boolean isDeliver, CatalogDTO catalog,HashMap<Integer, List<Pair<RangeDTO, Double>>> discountByAmountItems) {
		this.supplierId = supplierId;
		this.constDayDelivery = constDayDeliviery;
		this.isDeliver = isDeliver;
		this.catalog = catalog;
		this.discountByAmountItems = discountByAmountItems;
	}

	public List<DayOfWeek> getConstDayDelivery() {
		return constDayDelivery;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public boolean getIsDeliver(){
		return this.isDeliver;
	}

	@Override
	public String toString() {
		return "" + constDayDelivery;
	}

	/**
	 * @return the catalog
	 */
	public CatalogDTO getCatalog() {
		return catalog;
	}

	/**
	 * @return the discountByAmountItems
	 */
	public HashMap<Integer, List<Pair<RangeDTO, Double>>> getDiscountByAmountItems() {
		return discountByAmountItems;
	}
    
}