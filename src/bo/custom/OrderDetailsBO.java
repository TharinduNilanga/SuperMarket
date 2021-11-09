package bo.custom;

import bo.SuperBO;
import dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {
    OrderDetailsDTO searchOrder(String s) throws SQLException, ClassNotFoundException;
    ArrayList<OrderDetailsDTO> getAllOrder() throws SQLException, ClassNotFoundException;
}
