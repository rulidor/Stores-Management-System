package bussinessLayer.OrderPackage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


import DataAccessLaye.Repo;
import ServiceLayer.DeliveryService;
import ServiceLayer.Response;
import bussinessLayer.DTOPackage.ScheduledDTO;
import bussinessLayer.SupplierPackage.Supplier;

public class TimerTaskImpl extends TimerTask {
	private final Timer timer;
	private Date nextDate;
	private ScheduledDTO scheduled;
	private bussinessLayer.DTOPackage.OrderDTO order;

	public TimerTaskImpl(Timer timer, Date nextDate, ScheduledDTO scheduled){
		this.timer = timer;
		this.nextDate = nextDate;
		this.scheduled = scheduled;
	}

	@Override
	public void run() {
		if(!getspecificSchedule(scheduled.getBranchId(), scheduled.getDay().getValue(),scheduled.getSupplierId())){
			this.cancel();
			return;
		}
		if(orderExist(scheduled.getSupplierId(),scheduled.getBranchId(),nextDate)){
			try{
				LocalDateTime next = nextDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				int orderId = Repo.getInstance().getOrderIdBy(scheduled.getSupplierId(), scheduled.getBranchId(),
						next.getDayOfYear(),next.getYear());
				Order order = new Order(Repo.getInstance().getOrderByID(orderId));
				order.fillCartWithScheduled(scheduled);
				Repo.getInstance().updateOrder(order.converToDTO());
				return;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		Response r =createScheduledOrder();
		System.out.println(r.getMessage());
		/*
		 * if (r.isErrorOccured()) { tryToUpdateOrder(); }
		 */
		 Date nexxtnxxt = new Date(nextDate.getTime()); //the more closer date to today
		 Date nexxtnext = java.sql.Timestamp.valueOf(nextDate.toInstant()
				 .atZone(ZoneId.systemDefault())
				 .toLocalDateTime().plusDays(7));
		 timer.schedule(new TimerTaskImpl(timer, nexxtnext, scheduled), nexxtnxxt);

		 Date changeToProgress = java.sql.Timestamp.valueOf(nexxtnxxt.toInstant()
				 .atZone(ZoneId.systemDefault())
				 .toLocalDateTime().minusDays(1).plusMinutes(1));
		 timer.schedule(new TimerTask() {

			 @Override
			 public void run() {
				 try {
					 int dayOfYear = nextDate.toInstant()
							 .atZone(ZoneId.systemDefault())
							 .toLocalDateTime().getDayOfYear();
					 int year = nextDate.toInstant()
							 .atZone(ZoneId.systemDefault())
							 .toLocalDateTime().getYear();
					 try {
						DeliveryService.getInstance().createDelivery(java.sql.Timestamp.valueOf(order.getDeliveryDate()), order.getSupplierId(), order.getBranchId(), order);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 Repo.getInstance().updateOrderStatus(scheduled.getSupplierId(),scheduled.getBranchId(),dayOfYear, year,"INPROGRESS");
				 } catch (SQLException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }

			 }
		 }, changeToProgress);

	}

	/*
	 * private void tryToUpdateOrder() { try {
	 * Repo.getInstance().updateOrderStatus(scheduled.getSupplierId(),
	 * scheduled.getBranchId(), day, year, status); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

	private Response createScheduledOrder() {
		try {
			order = new Order(scheduled, nextDate, new Supplier(Repo.getInstance().getSupplierById(scheduled.getSupplierId()))).converToDTO();
			Repo.getInstance().insertOrder(order);
			return new Response(); 
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	private boolean getspecificSchedule(int branchId, int day, int supplierId) {
		try{Repo.getInstance().getSpecificScheduled(branchId, day, supplierId);return true;}catch(Exception e){return false;}
	}

	private boolean orderExist(int supplierId, int branchId, Date nextDate) {
		try{
			return Repo.getInstance().getOrderByDateSupplier(supplierId, branchId,nextDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());	
		}
		catch(Exception e){return false;}
	}

}