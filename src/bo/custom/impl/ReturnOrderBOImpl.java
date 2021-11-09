package bo.custom.impl;

import bo.SuperBO;
import bo.custom.ReturnOrderBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.ReturnDAO;
import dto.OrderDetailsDTO;
import dto.ReturnDTO;
import entity.OrderDetails;
import entity.Return;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnOrderBOImpl implements ReturnOrderBO {
    private final ReturnDAO returnDAO= (ReturnDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.RETURNORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return returnDAO.generateNewID();
    }

    @Override
    public boolean add(ReturnDTO dto) throws SQLException, ClassNotFoundException {
        return returnDAO.add(new Return(dto.getReturnId(),
                dto.getOrderId(),dto.getItemCode(),dto.getOrderQty(),dto.getDiscount(),dto.getTotal()));
    }

    @Override
    public ReturnDTO search(String returnId) throws SQLException, ClassNotFoundException {
        Return r=returnDAO.search(returnId);
        return new ReturnDTO(r.getReturnId(),
                r.getOrderId(),r.getItemCode(),r.getOrderQty(),r.getDiscount(),r.getTotal());

    }

    @Override
    public ArrayList<ReturnDTO> getAll() throws SQLException, ClassNotFoundException {
       ArrayList<ReturnDTO> allReturn=new ArrayList<>();
       ArrayList<Return> all=returnDAO.getAll();
       for (Return r:all){
           allReturn.add(new ReturnDTO(r.getReturnId(),r.getOrderId(),r.getItemCode(),r.getOrderQty(),r.getDiscount(),r.getTotal()));
       }
       return allReturn;
    }




}
