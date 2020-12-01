package DataAccessLaye;

import bussinessLayer.DTOPackage.ItemStatusDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DataAccessLaye.Interfaces.*;

public class ItemStatusDAOImpl implements IItemStatusDAO{
    private Connection conn;
public ItemStatusDAOImpl(Connection conn)
{
    this.conn = conn;
}

    @Override
    public ItemStatusDTO find(int branchId, int itemId) throws SQLException {
        String sql = "SELECT * "
                + "FROM ItemStatus WHERE branchId = ? AND itemId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);


        pstmt.setInt(1, branchId);
        pstmt.setInt(2,itemId);
        //
        ResultSet rs = pstmt.executeQuery();

        int branchIdO = rs.getInt("branchId");
        int itemIdO = rs.getInt("itemId");
        int quantityShelfO = rs.getInt("quantityShelf");
        int quantityStockO = rs.getInt("quantityStock");

        ItemStatusDTO itemStatusDTO = new ItemStatusDTO(branchIdO, itemIdO, quantityShelfO, quantityStockO);
        return itemStatusDTO;
    }

    @Override
    public void insert(ItemStatusDTO itemStatusDTO) throws SQLException {
        String sql = "INSERT INTO ItemStatus(branchId,itemId, quantityShelf, quantityStock) VALUES(?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemStatusDTO.getBranchId());
        pstmt.setInt(2, itemStatusDTO.getItemId());
        pstmt.setInt(3, itemStatusDTO.getQuantityShelf());
        pstmt.setInt(4, itemStatusDTO.getQuantityStock());
        pstmt.executeUpdate();
    }

    @Override
    public List<ItemStatusDTO> findAllByBranch(int branchId) throws SQLException {
        List<ItemStatusDTO> itemStatusDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM ItemStatus WHERE branchId = ?" +
                "order By itemId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, branchId);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int itemIdO = rs.getInt("itemId");
            int quantityShelfO = rs.getInt("quantityShelf");
            int quantityStockO = rs.getInt("quantityStock");
            ItemStatusDTO itemStatusDTO = new ItemStatusDTO(branchId, itemIdO, quantityShelfO, quantityStockO);
            itemStatusDTOS.add(itemStatusDTO);
        }
        return itemStatusDTOS;
    }

    @Override
    public void updateAStatus(ItemStatusDTO itemStatusDTO) throws SQLException {

        String sql = "UPDATE ItemStatus SET quantityShelf = ?, quantityStock = ?" +
                "where branchId = ? AND itemId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemStatusDTO.getQuantityShelf());
        pstmt.setInt(2, itemStatusDTO.getQuantityStock());
        pstmt.setInt(3, itemStatusDTO.getBranchId());
        pstmt.setInt(4, itemStatusDTO.getItemId());
        pstmt.executeUpdate();


    }
}
