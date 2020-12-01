package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.ContactDTO;

import java.sql.SQLException;
import java.util.List;

public interface IContactDAO {

    ContactDTO find(int supplierId, String phoneNumber) throws SQLException;

    void insert(ContactDTO contactDTO, int supplierId) throws SQLException;

    List<ContactDTO> findAllBySupplier(int supplierId) throws SQLException;


}
