package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.BranchDTO;

import java.sql.SQLException;
import java.util.List;

public interface IBranchDAO {
    BranchDTO find (int branchId) throws SQLException;

    void insert(BranchDTO branchDTO) throws SQLException;

    List<BranchDTO> findAll() throws SQLException;

    void updateDescription(int branchId, String description) throws SQLException;
}
