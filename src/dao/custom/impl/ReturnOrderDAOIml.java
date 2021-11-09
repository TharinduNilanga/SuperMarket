package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ReturnDAO;
import entity.Item;
import entity.Return;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnOrderDAOIml implements ReturnDAO {

    @Override
    public boolean add(Return dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO ReturnOrder VALUES (?,?,?,?,?,?)",dto.getReturnId(),
                dto.getOrderId(),dto.getItemCode(),dto.getOrderQty(),dto.getDiscount(),dto.getTotal());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Return returnDAO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Return search(String returnId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM ReturnOrder WHERE returnId=?",  returnId);
        rst.next();
        return new Return(returnId,rst.getString("orderId"),rst.getString("itemCode"),rst.getInt("orderQty"),rst.getInt("discount"),rst.getDouble("total"));

    }

    @Override
    public ArrayList<Return> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Return> allReturn = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM ReturnOrder");
        while (rst.next()) {
            allReturn.add(new Return(rst.getString("returnId"),rst.getString("orderId"),rst.getString("itemCode"),rst.getInt("orderQty"),rst.getInt("discount"),rst.getDouble("total")));
        }
        return allReturn;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT returnId FROM ReturnOrder ORDER BY returnId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("returnId");
            int newReturnId = Integer.parseInt(id.replace("R", "")) + 1;
            return String.format("R%03d", newReturnId);
        } else {
            return "R001";
        }
    }
}
