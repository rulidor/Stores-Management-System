package MessageTypes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StockReport implements Report{

    public Date dateProduced;

    //    public Map<String, List<Integer>> idByCategory;
    public List<Integer> itemsIdToBeReported;
    public Map<Integer, String> descById; //key: itemId, value: description
    public Map<Integer, String> positionById;
    public Map<Integer, String> manufacturerById;
    public Map<Integer, Integer> overallQuantityById;
    public Map<Integer, Integer> shelfQuantityById;
    public Map<Integer, Integer> stockQuantityById;


    public StockReport() {
        this.dateProduced = new Date();
        this.descById = new HashMap<>();
        this.positionById = new HashMap<>();
        this.manufacturerById = new HashMap<>();
        this.overallQuantityById = new HashMap<>();
        this.shelfQuantityById = new HashMap<>();
        this.stockQuantityById = new HashMap<>();
        this.itemsIdToBeReported = new ArrayList<>();
    }

    public List<Integer> getItemsIdToBeReported() {
        return itemsIdToBeReported;
    }

    public void setItemsIdToBeReported(List<Integer> itemsIdToBeReported) {
        this.itemsIdToBeReported = itemsIdToBeReported;
    }

    public Map<Integer, String> getDescById() {
        return descById;
    }

    public void setDescById(Map<Integer, String> descById) {
        this.descById = descById;
    }

    public Map<Integer, String> getPositionById() {
        return positionById;
    }

    public void setPositionById(Map<Integer, String> positionById) {
        this.positionById = positionById;
    }

    public Map<Integer, String> getManufacturerById() {
        return manufacturerById;
    }

    public void setManufacturerById(Map<Integer, String> manufacturerById) {
        this.manufacturerById = manufacturerById;
    }

    public Map<Integer, Integer> getOverallQuantityById() {
        return overallQuantityById;
    }

    public void setOverallQuantityById(Map<Integer, Integer> overallQuantityById) {
        this.overallQuantityById = overallQuantityById;
    }

    public Map<Integer, Integer> getShelfQuantityById() {
        return shelfQuantityById;
    }

    public void setShelfQuantityById(Map<Integer, Integer> shelfQuantityById) {
        this.shelfQuantityById = shelfQuantityById;
    }

    public Map<Integer, Integer> getStockQuantityById() {
        return stockQuantityById;
    }

    public void setStockQuantityById(Map<Integer, Integer> stockQuantityById) {
        this.stockQuantityById = stockQuantityById;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        int counter=1;
        String res = "****Stock Report****\n"
                +"Date produced: " + df.format(this.dateProduced)+"\n\n";
        for (Integer itemId:this.itemsIdToBeReported) {
            res += counter+". item id: "+ itemId + "\n"+
                    "\tdescription: "+this.descById.get(itemId)+"\n"+
                    "\tposition: "+this.positionById.get(itemId)+"\n"+
                    "\tmanufacturer: "+this.manufacturerById.get(itemId)+"\n"+
                    "\toverall quantity: "+this.overallQuantityById.get(itemId)+"\n"+
                    "\tshelf quantity: "+this.shelfQuantityById.get(itemId)+"\n"+
                    "\tstock quantity: "+this.stockQuantityById.get(itemId)+"\n";
            counter++;
        }
        res += "****End of report****\n";
        return res;
    }
}