package dao.custom;

import dao.CrudDAO;
import dao.CrudUtil;
import entity.Return;

import java.sql.SQLException;

public interface ReturnDAO extends CrudDAO<Return,String> {
    String generateNewID() throws SQLException, ClassNotFoundException;
}
