package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.LineCatalogItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ILineCatalogItemInCartDAO {

    LineCatalogItemDTO find(int orderId,int CatalogItemId) throws Exception;
    List<LineCatalogItemDTO>findAllByOrderId(int orderId) throws Exception;
    void insert(LineCatalogItemDTO lineCatalogItemDTO, int orderId) throws SQLException;
    void deleteItemFromOrder(int catalodItemId, int orderId);
	void updateLineCatalogItem(LineCatalogItemDTO line, int orderId) throws Exception;
}
