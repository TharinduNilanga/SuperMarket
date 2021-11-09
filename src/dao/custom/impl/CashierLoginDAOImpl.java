package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CashierLoginDAO;
import entity.Accounts;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierLoginDAOImpl implements CashierLoginDAO {
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT cashierId FROM Cashier ORDER BY cashierId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("cashierId");
            int newC = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newC);
        } else {
            return "C001";
        }
    }

    @Override
    public boolean add(Accounts dto) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("INSERT INTO  Cashier VALUES(?,?,md5(?))",dto.getId(),dto.getUserName(),dto.getPassword());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Cashier WHERE cashierId=?",s);
    }

    @Override
    public boolean update(Accounts accounts) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Accounts search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Accounts> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Accounts> all = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Cashier");
        while (rst.next()) {
            all.add(new Accounts(rst.getString("cashierId"),rst.getString("userName"),rst.getString("password")));
        }
        return all;
    }
}
