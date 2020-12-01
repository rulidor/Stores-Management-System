package DataAccessLaye;

import bussinessLayer.DTOPackage.ContactDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DataAccessLaye.Interfaces.*;

public class ContactDaoImpl implements  IContactDAO {

    private Connection conn;
    public ContactDaoImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public ContactDTO find(int supplierId, String phoneNumber) throws SQLException {
        String sql = "SELECT * "
                + "FROM Contacts WHERE supplierId = ? AND phoneNumber = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

      //set the value
        pstmt.setInt(1, supplierId);
        pstmt.setString(2,phoneNumber);

        ResultSet rs = pstmt.executeQuery();
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String phoneNumbers = rs.getString("phoneNumber");
        String address = rs.getString("address");


        ContactDTO contactDTO = new ContactDTO(firstName,lastName,phoneNumbers,address);
        return contactDTO;
    }

    @Override
    public void insert(ContactDTO contactDTO, int supplierId) throws SQLException {
        String sql = "INSERT INTO Contacts(supplierId,firstName,lastName,phoneNumber,address) VALUES(?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, supplierId);
        pstmt.setString(2, contactDTO.getFirstName());
        pstmt.setString(3, contactDTO.getLastName());
        pstmt.setString(4, contactDTO.getPhoneNumber());
        pstmt.setString(5, contactDTO.getAddress());
        pstmt.executeUpdate();

    }

    @Override
    public List<ContactDTO> findAllBySupplier(int supplierId) throws SQLException {
        List<ContactDTO> contactDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Contacts WHERE supplierId = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,supplierId);
        //
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String phoneNumbers = rs.getString("phoneNumber");
            String address = rs.getString("address");
            ContactDTO contactDTO = new ContactDTO(firstName,lastName,phoneNumbers,address);
            contactDTOS.add(contactDTO);
        }
        return contactDTOS;
    }
}
