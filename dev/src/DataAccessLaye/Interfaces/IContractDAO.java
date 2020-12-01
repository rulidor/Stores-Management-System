package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.ContractDTO;

import java.sql.SQLException;
import java.util.List;

public interface IContractDAO {
    ContractDTO find(int contractId) throws SQLException;
    void insert(ContractDTO contractDTO) throws SQLException;
    List<ContractDTO> findAll() throws SQLException;

}
