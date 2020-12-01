package DataAccessLaye;

import bussinessLayer.DTOPackage.RangeDTO;
import DataAccessLaye.Interfaces.*;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RangesDAODAOImpl implements IRangesDAO {
    private Connection conn;

    public RangesDAODAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public HashMap<Integer, List<Pair<RangeDTO, Double>>> findAll(int contractId) throws SQLException {

        HashMap<Integer, List<Pair<RangeDTO, Double>>> res = new HashMap<>();

        String sql = "SELECT * "
                + "FROM Ranges WHERE contractId = ? ORDER BY catalogItemId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,contractId);

        ResultSet rs = pstmt.executeQuery();
        if(!rs.next())return res;
        int currentCatalogItemId = rs.getInt("catalogItemId");
        res.putIfAbsent(currentCatalogItemId, new ArrayList<Pair<RangeDTO, Double>>());
        int catalogItemId;
        do {
            catalogItemId = rs.getInt("catalogItemId");
            int minimum = rs.getInt("minimum");
            int maximum = rs.getInt("maximum");
            Double price = rs.getDouble("price");
            if(currentCatalogItemId != catalogItemId){
                res.put(catalogItemId,new ArrayList<Pair<RangeDTO, Double>>());
                currentCatalogItemId = catalogItemId;
            }
            res.get(currentCatalogItemId).add(new Pair<RangeDTO,Double>(new RangeDTO(minimum,maximum),price));
        }while (rs.next());
        return res;
    }


    @Override
    public void insert(RangeDTO rangeDTO,int contractId,int catalogItemId,double price) throws SQLException {
        String sql = "INSERT INTO Ranges(rangeId, catalogItemId, contractId,minimum,maximum,price) VALUES(?,?,?,?,?,?)";

        HashMap<Integer, List<Pair<RangeDTO, Double>>> hash = findAll(contractId);
        int rangeId=1;
        for (Integer r : hash.keySet()) {
			rangeId += hash.get(r).size();
		}
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, rangeId);
        pstmt.setInt(2, catalogItemId);
        pstmt.setInt(3, contractId);
        pstmt.setInt(4, rangeDTO.getMin());
        pstmt.setInt(5, rangeDTO.getMax());
        pstmt.setDouble(6,price);
        pstmt.executeUpdate();

    }

    @Override
    public void deleteAllRangesByContractId(int contractId, int catalogItemId) {
    }
}
