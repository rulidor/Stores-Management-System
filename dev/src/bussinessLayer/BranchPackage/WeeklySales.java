package bussinessLayer.BranchPackage;

import java.util.HashMap;
import java.util.Map;

public class WeeklySales {

    private int weekNumber;
    private Map<Integer, Integer> soldById;

    public WeeklySales(int weekNumber, Map<Integer, Integer> soldById) {
        this.weekNumber = weekNumber;
        this.soldById = new HashMap<>();
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Map<Integer, Integer> getSoldById() {
        return soldById;
    }

    public void setSoldById(Map<Integer, Integer> soldById) {
        this.soldById = soldById;
    }
}
