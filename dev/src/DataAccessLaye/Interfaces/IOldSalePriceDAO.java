package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.OldSalePriceDTO;

import java.sql.SQLException;
import java.util.List;

public interface IOldSalePriceDAO {
    OldSalePriceDTO find(int itemId, int counter) throws SQLException;

    void insert(OldSalePriceDTO oldSalePriceDTO) throws SQLException;

    List<OldSalePriceDTO> findAllByItemId(int itemId) throws SQLException;
}
