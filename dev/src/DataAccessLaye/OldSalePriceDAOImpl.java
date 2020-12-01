package DataAccessLaye;

import bussinessLayer.DTOPackage.OldSalePriceDTO;
import DataAccessLaye.Interfaces.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OldSalePriceDAOImpl implements IOldSalePriceDAO {
    private Connection conn;
    public OldSalePriceDAOImpl(Connection conn)
    {
        this.conn = conn;
    }
    @Override
    public OldSalePriceDTO find(int itemId, int counter) throws SQLException {
        String sql = "SELECT * "
                + "FROM OldSalePrice WHERE itemId = ? AND counter = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);


        pstmt.setInt(1, itemId);
        pstmt.setInt(2, counter);
        //
        ResultSet rs = pstmt.executeQuery();

        double priceO = rs.getDouble("price");

        OldSalePriceDTO oldSalePriceDTO = new OldSalePriceDTO(itemId, counter, priceO);
        return oldSalePriceDTO;    }

    @Override
    public void insert(OldSalePriceDTO oldSalePriceDTO) throws SQLException {
        String sql = "INSERT INTO OldSalePrice(itemId,counter, price) VALUES(?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, oldSalePriceDTO.getItemId());
        pstmt.setInt(2, oldSalePriceDTO.getCounter());
        pstmt.setDouble(3, oldSalePriceDTO.getPrice());
        pstmt.executeUpdate();
    }

    @Override
    public List<OldSalePriceDTO> findAllByItemId(int itemId) throws SQLException {
        List<OldSalePriceDTO> oldSalePriceDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM OldSalePrice WHERE itemId = ?" +
                "order By itemId, counter";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int counter = rs.getInt("counter");
            double price = rs.getDouble("price");
            OldSalePriceDTO oldSalePriceDTO = new OldSalePriceDTO(itemId,counter,price);
            oldSalePriceDTOS.add(oldSalePriceDTO);
        }
        return oldSalePriceDTOS;     }
}
