package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.ItemStatusDTO;

import java.sql.SQLException;
import java.util.List;

public interface IItemStatusDAO {

    ItemStatusDTO find(int branchId, int itemId) throws SQLException;

    void insert(ItemStatusDTO itemStatusDTO) throws SQLException;

    List<ItemStatusDTO> findAllByBranch(int branchId) throws SQLException;

    void updateAStatus(ItemStatusDTO itemStatusDTO) throws SQLException;
}
