package bo.custom;

import bo.SuperBO;
import dto.OrderDetailsDTO;
import dto.ReturnDTO;
import entity.OrderDetails;
import entity.Return;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnOrderBO extends SuperBO {
     String generateNewID() throws SQLException, ClassNotFoundException;
     boolean add(ReturnDTO dto) throws SQLException, ClassNotFoundException;
     ReturnDTO search(String returnId) throws SQLException, ClassNotFoundException;
     ArrayList<ReturnDTO> getAll() throws SQLException, ClassNotFoundException;

}
