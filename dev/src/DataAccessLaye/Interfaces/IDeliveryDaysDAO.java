package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.ContractDTO;
import bussinessLayer.DTOPackage.DeliveryDaysDTO;

import java.sql.SQLException;

public interface IDeliveryDaysDAO {
   DeliveryDaysDTO findAllByContract(int contractId) throws SQLException;
    void insert(DeliveryDaysDTO deliveryDaysDTO, int contractId) throws SQLException;

    void deleteEveryThingByContract(int supplierId) throws SQLException;

    void insertEveryTingByContract(ContractDTO contractDTO) throws SQLException;
}
