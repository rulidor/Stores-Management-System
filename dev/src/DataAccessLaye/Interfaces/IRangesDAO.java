package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.RangeDTO;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IRangesDAO {
    HashMap<Integer, List<Pair<RangeDTO,Double>>> findAll(int contractId) throws SQLException;
    void insert(RangeDTO rangeDTO,int contractId,int catalogItemId, double price) throws SQLException;
    void deleteAllRangesByContractId(int contractId, int catalogItemId );
}
