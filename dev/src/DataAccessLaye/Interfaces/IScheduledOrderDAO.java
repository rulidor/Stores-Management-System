package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.ScheduledDTO;

import java.sql.SQLException;
import java.util.List;

public interface IScheduledOrderDAO {
    List<ScheduledDTO> findAll() throws SQLException;
    void insert(ScheduledDTO scheduledDTO) throws SQLException;
    void deleteScheduledBySupplier(int supplierId);
}
