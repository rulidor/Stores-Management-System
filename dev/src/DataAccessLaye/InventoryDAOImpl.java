package DataAccessLaye;

import bussinessLayer.DTOPackage.InventoryDTO;
import bussinessLayer.DTOPackage.ItemDTO;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import DataAccessLaye.Interfaces.*;

public class InventoryDAOImpl implements IInventoryDAO {
    private Connection conn;
    private IItemDAO itemDAO;

    public InventoryDAOImpl(Connection conn) {
        this.conn = conn;
        itemDAO = new ItemDAOImpl(conn);
    }

    @Override
    public InventoryDTO find() throws SQLException {
        String sql = "SELECT * "
                + "FROM Inventory where inventoryId=1";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        List<ItemDTO> itemDTOS = itemDAO.findAll();
        Map<Integer, ItemDTO> itemDTOMap = new HashMap<>();
        for (ItemDTO itemDTO : itemDTOS) {
            itemDTOMap.put(itemDTO.getId(), itemDTO);
        }

        int idCounter = rs.getInt("idCounter");

        InventoryDTO inventoryDTO = new InventoryDTO(itemDTOMap, idCounter);
        return inventoryDTO;
    }

    @Override
    public void insert(InventoryDTO inventoryDTO) throws SQLException {
        String sql = "INSERT INTO Inventory(inventoryId, idCounter) VALUES(?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, 1);
        pstmt.setInt(2, inventoryDTO.getIdCounter());
        pstmt.executeUpdate();
    }

    @Override
    public void updateIdCounter(int idCounter) throws SQLException {
        String sql = "UPDATE Inventory SET idCounter = ? where inventoryId=1";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idCounter);

        pstmt.executeUpdate();
    }

    @Override
    public boolean isAlreadyExist() throws SQLException {


        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM Inventory");
        r.next();
        int count = r.getInt("rowcount");
        r.close();
        //System.out.println("MyTable has " + count + " row(s).");
        if(count>0)
            return true;
        return false;

    }
}
