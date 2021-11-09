package dao.custom;

import dao.CrudDAO;
import entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order, String> {
    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;
    String generateNewOrderId() throws SQLException, ClassNotFoundException;
}