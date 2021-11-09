package dao.custom;

import dao.CrudDAO;
import entity.Accounts;

import java.sql.SQLException;

public interface AdminLoginDAO extends CrudDAO<Accounts,String> {
    String generateNewID() throws SQLException, ClassNotFoundException;
}
