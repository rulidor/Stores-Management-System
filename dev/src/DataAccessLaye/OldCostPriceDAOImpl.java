package DataAccessLaye;

import bussinessLayer.DTOPackage.OldCostPriceDTO;
import DataAccessLaye.Interfaces.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OldCostPriceDAOImpl implements IOldCostPriceDAO {
    private Connection conn;
    public OldCostPriceDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public OldCostPriceDTO find(int itemId, int counter) throws SQLException {
        String sql = "SELECT * "
                + "FROM OldCostPrice WHERE itemId = ? AND counter = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);


        pstmt.setInt(1, itemId);
        pstmt.setInt(2, counter);
        //
        ResultSet rs = pstmt.executeQuery();

        double priceO = rs.getDouble("price");

        OldCostPriceDTO oldCostPriceDTO = new OldCostPriceDTO(itemId, counter, priceO);
        return oldCostPriceDTO;    }

    @Override
    public void insert(OldCostPriceDTO oldCostPriceDTO) throws SQLException {
        String sql = "INSERT INTO OldCostPrice(itemId,counter, price) VALUES(?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, oldCostPriceDTO.getItemId());
        pstmt.setInt(2, oldCostPriceDTO.getCounter());
        pstmt.setDouble(3, oldCostPriceDTO.getPrice());
        pstmt.executeUpdate();
    }

    @Override
    public List<OldCostPriceDTO> findAllByItemId(int itemId) throws SQLException {
        List<OldCostPriceDTO> oldCostPriceDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM OldCostPrice WHERE itemId = ?" +
                "order By itemId, counter";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int counter = rs.getInt("counter");
            double price = rs.getDouble("price");
            OldCostPriceDTO oldCostPriceDTO = new OldCostPriceDTO(itemId,counter,price);
            oldCostPriceDTOS.add(oldCostPriceDTO);
        }
        return oldCostPriceDTOS;    }
}
