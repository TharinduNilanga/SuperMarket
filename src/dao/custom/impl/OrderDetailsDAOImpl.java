package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import entity.Item;
import entity.OrderDetails;
import entity.Return;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean add(OrderDetails dto) throws SQLException, ClassNotFoundException {
        System.out.println(dto
        .getOrderId());
        return CrudUtil.executeUpdate("INSERT INTO `OrderDetails`  VALUES (?,?,?,?,?)", dto.getOrderId(), dto.getItemCode(), dto.getOrderQty(),dto.getDiscount(),dto.getTotal());

    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(OrderDetails orderDetailDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetails search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetails WHERE orderId=?",  s);
        rst.next();
        return new OrderDetails(s,rst.getString("itemCode"),rst.getInt("orderQty"),rst.getInt("discount"),rst.getDouble("total"));
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> all = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetails");
        while (rst.next()) {
            all.add(new OrderDetails(rst.getString("orderId"), rst.getString("itemCode"),rst.getInt("orderQty"),rst.getInt("discount"),rst.getDouble("total")));
        }
        return all;
    }
}
