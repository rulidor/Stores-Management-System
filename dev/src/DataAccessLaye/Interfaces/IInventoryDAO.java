package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.InventoryDTO;

import java.sql.SQLException;

public interface IInventoryDAO {
    InventoryDTO find() throws SQLException;

    void insert(InventoryDTO inventoryDTO) throws SQLException;

    void updateIdCounter(int idCounter) throws SQLException;

    boolean isAlreadyExist() throws SQLException;
}
