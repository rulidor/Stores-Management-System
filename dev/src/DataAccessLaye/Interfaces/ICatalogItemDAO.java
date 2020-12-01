package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.CatalogItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ICatalogItemDAO {

    CatalogItemDTO find (int catalogItemId,int contractId) throws SQLException;

    void insert(CatalogItemDTO catalogItemDTO, int contractId) throws SQLException;

    void deleteCatalogItem(int contractId, int catalogItemId);

    List<CatalogItemDTO> findAll(int contractId) throws SQLException;
}
