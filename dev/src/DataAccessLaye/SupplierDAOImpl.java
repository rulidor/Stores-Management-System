package DataAccessLaye;

import bussinessLayer.DTOPackage.*;
import bussinessLayer.SupplierPackage.Supplier.BillingOptions;
import DataAccessLaye.Interfaces.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements ISupplierDAO {
    private Connection conn;
    private ContractDAOImpl contractDAO;
    private ContactDaoImpl contactDao;

    public SupplierDAOImpl(Connection conn) {
        this.conn = conn;
        contractDAO = new ContractDAOImpl(conn);
        contactDao = new ContactDaoImpl(conn);
    }


    @Override
    public SupplierDTO find(int supplierId) throws SQLException {
        String sql = "SELECT * "
                + "FROM Suppliers WHERE supplierId = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, supplierId);
      /*  // set the value
        pstmt.set(1, catalogItemId,contractId);*/
        //
        ResultSet rs = pstmt.executeQuery();
        if(!rs.next()) throw new SQLException("Not Found!");
        String supplierName = rs.getString("supplierName");
        int bankAccountNumber = rs.getInt("bankAccountNumber");
        String bilingOptions = rs.getString("bilingOptions");
        ContractDTO contractDTO = contractDAO.find(supplierId);
        List<ContactDTO> contactDTOS = contactDao.findAllBySupplier(supplierId);
        SupplierDTO res = new SupplierDTO(supplierId, supplierName, BillingOptions.valueOf(bilingOptions), bankAccountNumber, contractDTO, contactDTOS);
        return res;
    }

    @Override
    public List<SupplierDTO> findAll() throws SQLException {
        List<SupplierDTO> supplierDTOList = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Suppliers";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String supplierName = rs.getString("supplierName");
            int supplierId = rs.getInt("supplierId");
            int bankAccountNumber = rs.getInt("bankAccountNumber");
            String bilingOptions = rs.getString("bilingOptions");
            ContractDTO contractDTO = contractDAO.find(supplierId);
            List<ContactDTO> contactDTOS = contactDao.findAllBySupplier(supplierId);
            SupplierDTO res = new SupplierDTO(supplierId, supplierName, BillingOptions.valueOf(bilingOptions), bankAccountNumber, contractDTO, contactDTOS);
            supplierDTOList.add(res);
        }
        return supplierDTOList;

    }

    @Override
    public void insertSupplier(bussinessLayer.SupplierPackage.Supplier supplier) throws SQLException {
    	SupplierDTO sup = supplier.convertToDTO();
    	String sql = "INSERT INTO Suppliers(supplierName,supplierId,bankAccountNumber,bilingOptions) VALUES(?,?,?,?)";


        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, supplier.getName());
        pstmt.setInt(2, supplier.getSupplierId());
        pstmt.setInt(3, supplier.getBankAccountNumber());
        pstmt.setString(4, supplier.getBilingOption().name());
        pstmt.executeUpdate();
        
        contractDAO.insert(sup.getContractDTO());
        for(ContactDTO c : sup.getContactDTOS()) {
        	contactDao.insert(c, sup.getSupplierId());
        }
        
    }
    
    public void deleteSupplier(int supplierId) throws SQLException {
    	String sql = "DELETE FROM Suppliers where supplierId = ?";
    	
    	PreparedStatement ps = conn.prepareStatement(sql);
    	
    	ps.setInt(1, supplierId);
    	ps.execute();
    }
}
