package MessageTypes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ItemWarning implements Report{

    public Date dateProduced;
    public Map<Integer, Integer> warningById; //key: itemId, value: current overall quantity


    public ItemWarning(Map<Integer, Integer> warningById) {
        this.dateProduced = new Date();
        this.warningById = new HashMap<>();
    }

    public Map<Integer, Integer> getWarningById() {
        return warningById;
    }

    public void setWarningById(Map<Integer, Integer> warningById) {
        this.warningById = warningById;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        int counter=1;
        String res = "****Warnings On Minimum Quantity Report****\n"
                +"Date produced: " + df.format(this.dateProduced)+"\n"+
                "Note: warnings are shown only for items which have overall quantity<=minimum quantity.\n\n";
        for (Integer itemId:this.warningById.keySet()) {
            res += counter+". item id: "+ itemId + ", overall quantity: "+this.warningById.get(itemId)+"\n";
            counter++;
        }
        res += "****End of report****\n";
        return res;
    }

}
