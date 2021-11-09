package bo.custom.impl;

import bo.custom.AdminLoginBO;
import dao.DAOFactory;
import dao.custom.AdminLoginDAO;
import dao.custom.CashierLoginDAO;
import dto.AccountsDTO;
import entity.Accounts;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminLoginBOImpl implements AdminLoginBO {
   AdminLoginDAO adminLoginDAO= (AdminLoginDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ADMINLOGIN);
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return adminLoginDAO.generateNewID();
    }

    @Override
    public boolean add(AccountsDTO dto) throws SQLException, ClassNotFoundException {
        return adminLoginDAO.add(new Accounts(dto.getId(),dto.getUserName(),dto.getPassword()));
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return adminLoginDAO.delete(s);
    }

    @Override
    public ArrayList<AccountsDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<AccountsDTO> all=new ArrayList<>();
        ArrayList<Accounts> allAdmin=adminLoginDAO.getAll();
        for (Accounts accounts:allAdmin){
            all.add(new AccountsDTO(accounts.getId(),accounts.getUserName(),accounts.getPassword()));
        }
        return all;
    }
}
