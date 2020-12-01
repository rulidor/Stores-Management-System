package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.DamagedControllerDTO;

import java.sql.SQLException;
import java.util.List;

public interface IDamagedControllerDAO {

    DamagedControllerDTO findDamageController(int branchId) throws SQLException;
    void insertDamagedItem(int branchID,int itemId, int quantity) throws SQLException;
    List<DamagedControllerDTO> findAll() throws SQLException;
    void updateAnItem(int branchId, int itemId, int newQuantity) throws SQLException;
}
