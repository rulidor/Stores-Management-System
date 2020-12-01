package DataAccessLaye.Interfaces;

import bussinessLayer.DTOPackage.OrderDTO;

import java.util.List;

public interface IOrderDAO {

     OrderDTO find(int orderId) throws Exception;
    List<OrderDTO> findAll() throws Exception;
    int insert(OrderDTO orderDTO) throws Exception;



}
