package DataAccessLaye;

import bussinessLayer.DTOPackage.DamagedControllerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import DataAccessLaye.Interfaces.*;

public class DamagedControllerDAOImpl implements IDamagedControllerDAO {
    private Connection conn;
    public DamagedControllerDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public DamagedControllerDTO findDamageController(int branchId) throws SQLException {
        String sql = "SELECT * "
                + "FROM DamagedItem WHERE branchId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);


        pstmt.setInt(1, branchId);
        //
        ResultSet rs = pstmt.executeQuery();
        Map<Integer, Integer> quantityById = new HashMap<>();
        while (rs.next()) {
            int itemIdO = rs.getInt("itemId");
            int quantityO = rs.getInt("quantityDamaged");
            quantityById.put(itemIdO, quantityO);
        }
        DamagedControllerDTO damagedControllerDTO = new DamagedControllerDTO(branchId, quantityById);
        return damagedControllerDTO;
    }

    @Override
    public void insertDamagedItem(int branchId,int itemId, int quantity) throws SQLException {
        String sql = "INSERT INTO DamagedItem(branchId,itemId, quantityDamaged) VALUES(?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, branchId);
        pstmt.setInt(2, itemId);
        pstmt.setInt(3, quantity);
        pstmt.executeUpdate();
    }

    @Override
    public List<DamagedControllerDTO> findAll() throws SQLException {
//        List<DamagedControllerDTO> damagedControllerDTOS = new ArrayList<>();
//
//        String sql = "SELECT * "
//                + "FROM DamagedItem WHERE branchId = ?" +
//                "order By itemId";
//
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        pstmt.setInt(1, branchId);
//        ResultSet rs = pstmt.executeQuery();
//
//        while (rs.next()) {
//            int itemIdO = rs.getInt("itemId");
//            int quantityO = rs.getInt("quantity");
//            DamagedControllerDTO damagedControllerDTO = new DamagedControllerDTO(branchId, itemIdO, quantityO);
//            damagedControllerDTOS.add(damagedControllerDTO);
//        }
//        return damagedControllerDTOS;
        return null;
    }

    @Override
    public void updateAnItem(int branchId, int itemId, int newQuantity) throws SQLException {
        String sql = "UPDATE DamagedItem SET quantityDamaged = ?" +
                "where branchId = ? AND itemId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, newQuantity);
        pstmt.setInt(2, branchId);
        pstmt.setInt(3, itemId);
        pstmt.executeUpdate();


    }
}
