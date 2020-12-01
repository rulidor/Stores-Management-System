package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.OldCostPriceDTO;

import java.sql.SQLException;
import java.util.List;

public interface IOldCostPriceDAO {
    OldCostPriceDTO find(int itemId, int counter) throws SQLException;

    void insert(OldCostPriceDTO oldCostPriceDTO) throws SQLException;

    List<OldCostPriceDTO> findAllByItemId(int itemId) throws SQLException;
}
