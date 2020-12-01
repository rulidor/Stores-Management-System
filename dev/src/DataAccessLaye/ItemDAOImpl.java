package DataAccessLaye;

import bussinessLayer.DTOPackage.ItemDTO;
import bussinessLayer.DTOPackage.ItemFeaturesDTO;
import DataAccessLaye.Interfaces.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements IItemDAO {
    private Connection conn;

    public ItemDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public ItemDTO find(int itemId) throws SQLException {
        String sql = "SELECT * "
                + "FROM Item where itemId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemId);


        ResultSet rs = pstmt.executeQuery();
        if(!rs.next()) throw new SQLException("Not Found!");
        ItemFeaturesDTO itemFeaturesDTO = new ItemFeaturesDTO(rs.getInt("itemId"),rs.getDouble("weight"),
                rs.getString("category"),rs.getString("subCategory"),
                rs.getString("sub2Category"), rs.getString("manufacturer"));
        List<Double> oldCostPrices = getOldCostPrices(itemId);
        List<Double> oldSalePrices = getOldSalePrices(itemId);

        String descriptionO = rs.getString("description");
        double costPriceO = rs.getDouble("costPrice");
        double salePriceO = rs.getDouble("salePrice");
        int minQuantity = rs.getInt("minimumQuantity");

        ItemDTO itemDTO = new ItemDTO(itemId, descriptionO, costPriceO, salePriceO, oldCostPrices, oldSalePrices, minQuantity, itemFeaturesDTO);
        return itemDTO;
    }

    public List<Double> getOldCostPrices(int itemId) throws SQLException {
        List<Double> oldPrices = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM OldCostPrice where itemId = ?" +
                " order by counter";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemId);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            oldPrices.add(rs.getDouble("price"));
        } //older price is the last one
        return oldPrices;
    }
    public List<Double> getOldSalePrices(int itemId) throws SQLException {
        List<Double> oldPrices = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM OldSalePrice where itemId = ?" +
                " order by counter";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemId);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            oldPrices.add(rs.getDouble("price"));
        } //older price is the last one
        return oldPrices;
    }

    @Override
    public void insert(ItemDTO itemDTO) throws SQLException {
        String sql = "INSERT INTO Item(itemId, description, costPrice, salePrice, minimumQuantity ,weight, category, subCategory, sub2Category, manufacturer, costCounter, saleCounter) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemDTO.getId());
        pstmt.setString(2, itemDTO.getDescription());
        pstmt.setDouble(3,itemDTO.getCostPrice());
        pstmt.setDouble(4,itemDTO.getSalePrice());
        pstmt.setInt(5,itemDTO.getMinimumQuantity());
        pstmt.setDouble(6,itemDTO.getFeaturesDTO().getWeight());
        pstmt.setString(7,itemDTO.getFeaturesDTO().getCategory());
        pstmt.setString(8,itemDTO.getFeaturesDTO().getSubCategory());
        pstmt.setString(9,itemDTO.getFeaturesDTO().getSub2Category());
        pstmt.setString(10,itemDTO.getFeaturesDTO().getManufacturer());
        pstmt.setInt(11,itemDTO.getCostCounter());
        pstmt.setInt(12,itemDTO.getSaleCounter());
        pstmt.executeUpdate();
    }

    @Override
    public List<ItemDTO> findAll() throws SQLException {
        List<ItemDTO> itemDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Item order by itemId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int idO = rs.getInt("itemId");
            ItemFeaturesDTO itemFeaturesDTO = new ItemFeaturesDTO(idO,rs.getDouble("weight"),
                    rs.getString("category"),rs.getString("subCategory"),
                    rs.getString("sub2Category"), rs.getString("manufacturer"));

            List<Double> oldCostPrices = getOldCostPrices(idO);
            List<Double> oldSalePrices = getOldSalePrices(idO);

            String descriptionO = rs.getString("description");
            double costPriceO = rs.getDouble("costPrice");
            double salePriceO = rs.getDouble("salePrice");
            int minQuantity = rs.getInt("minimumQuantity");

            ItemDTO itemDTO = new ItemDTO(idO, descriptionO, costPriceO, salePriceO, oldCostPrices, oldSalePrices, minQuantity, itemFeaturesDTO);

            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    @Override
    public void updateWithoutOldPrices(ItemDTO itemDTO) throws SQLException {
        String sql = "UPDATE Item SET description = ?, costPrice=?, salePrice=?" +
                ",minimumQuantity=?, weight=?, category=?, subCategory=?, sub2Category=?, manufacturer=?" +
                "where itemId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, itemDTO.getDescription());
        pstmt.setDouble(2, itemDTO.getCostPrice());
        pstmt.setDouble(3, itemDTO.getSalePrice());
        pstmt.setInt(4,itemDTO.getMinimumQuantity());
        pstmt.setDouble(5, itemDTO.getFeaturesDTO().getWeight());
        pstmt.setString(6, itemDTO.getFeaturesDTO().getCategory());
        pstmt.setString(7, itemDTO.getFeaturesDTO().getSubCategory());
        pstmt.setString(8, itemDTO.getFeaturesDTO().getSub2Category());
        pstmt.setString(9, itemDTO.getFeaturesDTO().getManufacturer());
        pstmt.setInt(10, itemDTO.getId());
        pstmt.executeUpdate();
    }

    @Override
    public void updateCostPrice(int itemId, double newPrice, double oldPrice) throws SQLException {
        String sqlPrep = "SELECT COUNT(*) AS rowcount FROM OldCostPrice where itemId = ?";
        PreparedStatement s = conn.prepareStatement(sqlPrep);
        s.setInt(1, itemId);
        ResultSet r = s.executeQuery();
        r.next();
        int count = r.getInt("rowcount");
        r.close();

        String sql = "UPDATE Item SET costPrice = ?, costCounter = ?" +
                " where itemId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDouble(1, newPrice);
        pstmt.setInt(2, count + 1);
        pstmt.setInt(3, itemId);

        pstmt.executeUpdate();

        String sql2 = "insert into OldCostPrice(itemId, counter, price) " +
                "VALUES(?,?,?)";
        pstmt = conn.prepareStatement(sql2);
        pstmt.setInt(1, itemId);
        pstmt.setInt(2, count + 1);
        pstmt.setDouble(3, oldPrice);

        pstmt.executeUpdate();
    }

    @Override
    public void updateSalePrice(int itemId, double newPrice, double oldPrice) throws SQLException {
        String sqlPrep = "SELECT COUNT(*) AS rowcount FROM OldSalePrice where itemId = ?";
        PreparedStatement s = conn.prepareStatement(sqlPrep);
        s.setInt(1, itemId);
        ResultSet r = s.executeQuery();
        r.next();
        int count = r.getInt("rowcount");
        r.close();

        String sql = "UPDATE Item SET salePrice = ?, saleCounter = ? " +
                "where itemId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDouble(1, newPrice);
        pstmt.setInt(2, count + 1);
        pstmt.setInt(3, itemId);

        pstmt.executeUpdate();

        String sql2 = "insert into OldSalePrice(itemId, counter, price) " +
                "VALUES(?,?,?)";
        pstmt = conn.prepareStatement(sql2);
        pstmt.setInt(1, itemId);
        pstmt.setInt(2, count + 1);
        pstmt.setDouble(3, oldPrice);

        pstmt.executeUpdate();
    }
}

