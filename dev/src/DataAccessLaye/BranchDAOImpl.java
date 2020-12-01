package DataAccessLaye;

import bussinessLayer.DTOPackage.BranchDTO;
import bussinessLayer.DTOPackage.DamagedControllerDTO;
import bussinessLayer.DTOPackage.InventoryDTO;
import bussinessLayer.DTOPackage.ItemStatusDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import DataAccessLaye.Interfaces.*;

public class BranchDAOImpl implements IBranchDAO {

    private Connection conn;
    private IDamagedControllerDAO damagedControllerDAO;
    private IInventoryDAO inventoryDAO;
    private IItemStatusDAO itemStatusDAO;

/*
    public SupplierDAOImpl(Connection conn) {

    }
 */
    public BranchDAOImpl(Connection conn)
    {
        this.conn = conn;
        damagedControllerDAO = new DamagedControllerDAOImpl(conn);
        inventoryDAO = new InventoryDAOImpl(conn);
        itemStatusDAO = new ItemStatusDAOImpl(conn);
    }

    @Override
    public BranchDTO find(int branchId) throws SQLException {
        String sql = "SELECT * "
                + "FROM Branch WHERE branchId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);


        pstmt.setInt(1, branchId);

        //
        ResultSet rs = pstmt.executeQuery();
        String description = rs.getString("description");
        InventoryDTO inventoryDTO = inventoryDAO.find();
        DamagedControllerDTO damagedControllerDTO = damagedControllerDAO.findDamageController(branchId);
        List<ItemStatusDTO> itemStatuses = itemStatusDAO.findAllByBranch(branchId);
        Map<Integer, ItemStatusDTO> statuses = new HashMap<>();
        for (ItemStatusDTO s: itemStatuses) {
            statuses.put(s.getItemId(), s);
        }
        BranchDTO branchDTO = new BranchDTO(branchId,description, damagedControllerDTO, inventoryDTO, statuses);
        return branchDTO;
    }

    @Override
    public void insert(BranchDTO branchDTO) throws SQLException {
        String sql = "INSERT INTO Branch(branchId,description) VALUES(?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, branchDTO.getId());
        pstmt.setString(2,branchDTO.getDescription());
        pstmt.executeUpdate();
    }

    @Override
    public List<BranchDTO> findAll() throws SQLException {
        List<BranchDTO> branchDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Branch order By branchId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        
        InventoryDTO inventoryDTO = null;
        try{inventoryDTO = inventoryDAO.find();}catch(Exception e) {System.out.println(e.getMessage());}
        while (rs.next()) {
            int branchIdO = rs.getInt("branchId");
            String descriptionO = rs.getString("description");
            DamagedControllerDTO damagedControllerDTO = damagedControllerDAO.findDamageController(branchIdO);
            List<ItemStatusDTO> itemStatuses = itemStatusDAO.findAllByBranch(branchIdO);
            Map<Integer, ItemStatusDTO> statuses = new HashMap<>();
            for (ItemStatusDTO s: itemStatuses) {
                statuses.put(s.getItemId(), s);
            }
            BranchDTO branchDTO = new BranchDTO(branchIdO,descriptionO, damagedControllerDTO, inventoryDTO, statuses);
            branchDTOS.add(branchDTO);
        }
        return branchDTOS;
    }

    @Override
    public void updateDescription(int branchId, String description) throws SQLException {
        String sql = "UPDATE Branch SET description = ?" +
                "where branchId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, description);
        pstmt.setInt(2, branchId);

        pstmt.executeUpdate();
    }
}
