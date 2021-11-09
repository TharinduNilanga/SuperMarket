package bo.custom.impl;

import bo.custom.CashierLoginBO;
import dao.DAOFactory;
import dao.custom.AdminLoginDAO;
import dao.custom.CashierLoginDAO;
import dto.AccountsDTO;
import entity.Accounts;

import java.sql.SQLException;
import java.util.ArrayList;

public class CashierLoginBOImpl implements CashierLoginBO {
    CashierLoginDAO cashierLoginDAO= (CashierLoginDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CASHIERLOGIN);

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return cashierLoginDAO.generateNewID();
    }

    @Override
    public boolean add(AccountsDTO dto) throws SQLException, ClassNotFoundException {
        return cashierLoginDAO.add(new Accounts(dto.getId(),dto.getUserName(),dto.getPassword()));
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return cashierLoginDAO.delete(s);
    }

    @Override
    public ArrayList<AccountsDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<AccountsDTO> all=new ArrayList<>();
        ArrayList<Accounts> allCashier=cashierLoginDAO.getAll();
        for (Accounts accounts:allCashier){
            all.add(new AccountsDTO(accounts.getId(),accounts.getUserName(),accounts.getPassword()));
        }
        return all;
    }
}
