package DataAccessLaye;

import bussinessLayer.DTOPackage.*;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import DataAccessLaye.Interfaces.*;

public class ContractDAOImpl implements IContractDAO {

    private Connection conn;
    private IDeliveryDaysDAO daysDAO;
    private IRangesDAO rangesDAO;
    private ICatalogItemDAO catalogItemDAO;
    public ContractDAOImpl (Connection conn)
    {

        this.conn = conn;
        daysDAO = new DeliveryDaysDAOImpl(conn);
        rangesDAO = new RangesDAODAOImpl(conn);
        catalogItemDAO = new CatalogItemDAOImpl(conn);
    }

    @Override
    public ContractDTO find(int contractId) throws SQLException {
        String sql = "SELECT * "
                + "FROM Contracts WHERE contractId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,contractId);

      /*  // set the value
        pstmt.set(1, catalogItemId,contractId);*/
        //
        ResultSet rs = pstmt.executeQuery();
        if(!rs.next()) throw new SQLException("Contract not Found");
        int contractIds = rs.getInt("contractId"); // TODO : creatIndexs IN TABLE
        boolean isDeliver = rs.getBoolean("isDeliver");
        DeliveryDaysDTO dayOfWeeks = daysDAO.findAllByContract(contractId);
        HashMap<Integer,List<Pair<RangeDTO,Double>>> rangesDto = this.rangesDAO.findAll(contractId);
        List<CatalogItemDTO> catalogItemDTOList = this.catalogItemDAO.findAll(contractId);
        CatalogDTO catalogDTO = new CatalogDTO(catalogItemDTOList);
        ContractDTO contractDTO = new ContractDTO(contractIds,dayOfWeeks.dayOfWeeks(),isDeliver,catalogDTO,rangesDto);
        return contractDTO;
    }

    @Override
    public void insert(ContractDTO contractDTO) throws SQLException {
        String sql = "INSERT INTO Contracts(contractId,isDeliver) VALUES(?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, contractDTO.getSupplierId());
        pstmt.setBoolean(2, contractDTO.getIsDeliver());
        pstmt.executeUpdate();

        for (DayOfWeek dayOfWeek : contractDTO.getConstDayDelivery())
        {
            String sql2 = "INSERT INTO DeliveryDays(contractId,Deliday) VALUES(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.setInt(1,contractDTO.getSupplierId());
            preparedStatement.setString(2,dayOfWeek.toString());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<ContractDTO> findAll() throws SQLException {
        List<ContractDTO> contractDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Contracts" +
                "order By contractId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        //
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int contractId = rs.getInt("contractId");
            boolean isDeliver = rs.getBoolean("Deliday");
            DeliveryDaysDTO dayOfWeeks = daysDAO.findAllByContract(contractId);
            HashMap<Integer,List<Pair<RangeDTO,Double>>> rangesDto = this.rangesDAO.findAll(contractId);
            List<CatalogItemDTO> catalogItemDTOList = this.catalogItemDAO.findAll(contractId);
            CatalogDTO catalogDTO = new CatalogDTO(catalogItemDTOList);
            ContractDTO contractDTO = new ContractDTO(contractId,dayOfWeeks.dayOfWeeks(),isDeliver,catalogDTO,rangesDto);
            contractDTOS.add(contractDTO);
        }
        return contractDTOS;
    }
}
