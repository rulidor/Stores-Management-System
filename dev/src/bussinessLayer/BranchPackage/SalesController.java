package bussinessLayer.BranchPackage;

import java.util.Map;

public class SalesController {

    private Map<Integer, WeeklySales> salesByWeek;

    public SalesController(Map<Integer, WeeklySales> salesByWeek) {
        this.salesByWeek = salesByWeek;
    }

    public Map<Integer, WeeklySales> getSalesByWeek() {
        return salesByWeek;
    }

    public void setSalesByWeek(Map<Integer, WeeklySales> salesByWeek) {
        this.salesByWeek = salesByWeek;
    }
}
