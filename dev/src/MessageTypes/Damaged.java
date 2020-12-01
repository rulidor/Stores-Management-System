package MessageTypes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Damaged implements Report {

    public Date dateProduced;
    public Map<Integer, Integer> damagedById;

    public Damaged(Map<Integer, Integer> damagedById) {
        this.dateProduced = new Date();
        this.damagedById = new HashMap<>();
    }

    public Map<Integer, Integer> getDamagedById() {
        return damagedById;
    }

    public void setDamagedById(Map<Integer, Integer> damagedById) {
        this.damagedById = damagedById;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        int counter=1;
        String res = "****Damaged Items Report****\n"
                +"Date produced: " + df.format(this.dateProduced)+"\n\n";
        for (Integer itemId:this.damagedById.keySet()) {
            res += counter+". item id: "+ itemId + ", damaged: "+this.damagedById.get(itemId)+"\n";
            counter++;
        }
        res += "****End of report****\n";
        return res;
    }

}
