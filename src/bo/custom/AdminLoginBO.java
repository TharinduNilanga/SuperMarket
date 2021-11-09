package bo.custom;

import bo.SuperBO;
import dto.AccountsDTO;
import entity.Accounts;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminLoginBO extends SuperBO {
     String generateNewID() throws SQLException, ClassNotFoundException;
     boolean add(AccountsDTO dto) throws SQLException, ClassNotFoundException;
     boolean delete(String s) throws SQLException, ClassNotFoundException;
     ArrayList<AccountsDTO> getAll() throws SQLException, ClassNotFoundException;
}
