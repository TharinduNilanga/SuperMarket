package bo.custom.impl;

import bo.custom.OrderDetailsBO;
import dao.DAOFactory;
import dao.custom.OrderDetailsDAO;
import dto.ItemDTO;
import dto.OrderDetailsDTO;
import entity.Item;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsBOImpl implements OrderDetailsBO {
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public OrderDetailsDTO searchOrder(String s) throws SQLException, ClassNotFoundException {
        OrderDetails rst=orderDetailsDAO.search(s);
        return new OrderDetailsDTO(rst.getOrderId(),rst.getItemCode(),rst.getOrderQty(),rst.getDiscount(),rst.getTotal());
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsDTO> allO=new ArrayList<>();
        ArrayList<OrderDetails> all = orderDetailsDAO.getAll();

        for (OrderDetails i:all){
            allO.add(new OrderDetailsDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getTotal()));
        }
        return allO;
    }
}
