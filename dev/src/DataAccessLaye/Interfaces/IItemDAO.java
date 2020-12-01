package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface IItemDAO {
    ItemDTO find(int itemId) throws SQLException;

    void insert(ItemDTO itemDTO) throws SQLException;

    List<ItemDTO> findAll() throws SQLException;

    void updateWithoutOldPrices(ItemDTO itemDTO) throws SQLException;

    void updateCostPrice(int itemId, double newPrice,double oldPrice) throws SQLException;

    void updateSalePrice(int itemId, double newPrice,double oldPrice) throws SQLException;

}
