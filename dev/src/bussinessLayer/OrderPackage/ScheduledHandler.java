package bussinessLayer.OrderPackage;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import DataAccessLaye.Repo;
import ServiceLayer.DeliveryService;
import ServiceLayer.Service;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;
import bussinessLayer.DTOPackage.OrderDTO;
import bussinessLayer.DTOPackage.ScheduledDTO;
import bussinessLayer.InventoryPackage.Inventory;

public class ScheduledHandler {
    private static ScheduledHandler scHandler = null;
    private Timer timer;

    private ScheduledHandler() {
        timer = new Timer("My Timer");
    }

    public static ScheduledHandler getInstance() {
        if (scHandler != null)
            return scHandler;
        scHandler = new ScheduledHandler();
        return scHandler;
    }

    public void start() throws SQLException {
        List<ScheduledDTO> list = Repo.getInstance().getAllScheduled();
        for (ScheduledDTO scheduledDTO : list) {
            Date nextDate = getNextDateToCreateOrder(scheduledDTO.getDay());
            timer.schedule(new TimerTaskImpl(scHandler.getTimer(),nextDate, scheduledDTO), java.sql.Timestamp.valueOf(LocalDateTime.now().plusSeconds(10)));
        }
    }
    
    public void addSchedule(ScheduledDTO scheduled) {
    	Date nextDate = getNextDateToCreateOrder(scheduled.getDay());
    	timer.schedule(new TimerTaskImpl(scHandler.getTimer(),nextDate,scheduled), java.sql.Timestamp.valueOf(LocalDateTime.now().plusSeconds(10)));
    }

    private Date getNextDateToCreateOrder(DayOfWeek day) {
        int now = LocalDateTime.now().getDayOfWeek().getValue(); // value of today
        int dayToOrder = day.getValue();
        int difference = now - dayToOrder;
        if(dayToOrder < now) {
        	difference = 7-difference;
        } else if(now < dayToOrder) {
        	difference = (-1)* difference;
        }
        else{difference =7;}
        
        return java.util.Date.from(LocalDateTime.now().plusDays(difference).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

	public void addChangeToProgress(int orderId, LocalDateTime time) {
		time = time.minusDays(1).plusSeconds(120);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					Repo.getInstance().updateAnOrderStatusById(orderId,"INPROGRESS");
					DeliveryService ds = DeliveryService.getInstance();
                    OrderDTO o = Repo.getInstance().getOrderByID(orderId);
                    ds.createDelivery(java.sql.Timestamp.valueOf(o.getDeliveryDate()), o.getSupplierId(), o.getBranchId(), o);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},java.sql.Timestamp.valueOf(time));
		
	}

}