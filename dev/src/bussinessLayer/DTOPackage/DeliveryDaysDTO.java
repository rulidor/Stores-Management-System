package bussinessLayer.DTOPackage;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDaysDTO {

    private List<DayOfWeek> dayOfWeeks;

    public DeliveryDaysDTO(List<DayOfWeek> dayOfWeek)
    {

        dayOfWeeks = new ArrayList<DayOfWeek>();
        for (DayOfWeek day : dayOfWeek)
        {
            dayOfWeeks.add(day);
        }
    }

    public List<DayOfWeek> dayOfWeeks()
    {
        return this.dayOfWeeks;
    }
}
