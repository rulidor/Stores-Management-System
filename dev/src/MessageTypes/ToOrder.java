package MessageTypes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ToOrder  implements Report{

    public Date dateProduced;
    public Map<Integer, Integer> orderById;

    public ToOrder() {
        this.dateProduced = new Date();
        this.orderById = new HashMap<>();
    }

    public Map<Integer, Integer> getOrderById() {
        return orderById;
    }

    public void setOrderById(Map<Integer, Integer> orderById) {
        this.orderById = orderById;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        int counter=1;
        String res = "****Warnings On Minimum Quantity Report****\n"
                +"Date produced: " + df.format(this.dateProduced)+"\n"+
                "Note: this report will include only items which has overall quantity < 5 * min. quantity.\n\n";
        for (Integer itemId:this.orderById.keySet()) {
            res += counter+". item id: "+ itemId + ", needs to be ordered: "+this.orderById.get(itemId)+"\n";
            counter++;
        }
        res += "****End of report****\n";
        return res;
    }

}
