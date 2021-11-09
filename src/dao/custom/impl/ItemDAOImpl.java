package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {


    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("itemCode");
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newItemId);
        } else {
            return "I001";
        }
    }



    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?)",dto.getItemCode()
               ,dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
          return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?",code);
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?,packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?", dto.getDescription(),dto.getPackSize(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getItemCode());

    }

    @Override
    public Item search(String itemCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", itemCode);
        rst.next();
        return new Item(itemCode, rst.getString("description"),rst.getString("packSize") , rst.getDouble("unitPrice"),rst.getInt("qtyOnHand"));

    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(rst.getString("itemCode"), rst.getString("description"),rst.getString("packSize") , rst.getDouble("unitPrice"),rst.getInt("qtyOnHand")));
        }
        return allItems;
    }
    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT code FROM Item WHERE itemCode=?", code).next();
    }
}
