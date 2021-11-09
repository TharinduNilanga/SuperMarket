package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.AdminLoginDAO;
import entity.Accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminLoginDAOImpl implements AdminLoginDAO {
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT adminId FROM Admin ORDER BY adminId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("adminId");
            int newA = Integer.parseInt(id.replace("A", "")) + 1;
            return String.format("A%03d", newA);
        } else {
            return "A001";
        }
    }

    @Override
    public boolean add(Accounts dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO  Admin VALUES(?,?,md5(?))",dto.getId(),dto.getUserName(),dto.getPassword());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Admin WHERE adminId=?",s);
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
        ArrayList<Accounts> allAdmin = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Admin");
        while (rst.next()) {
            allAdmin.add(new Accounts(rst.getString("adminId"),rst.getString("userName"),rst.getString("password")));
        }
        return allAdmin;
    }
}
