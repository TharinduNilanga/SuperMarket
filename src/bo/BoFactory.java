package bo;

import bo.custom.OrderDetailsBO;
import bo.custom.impl.*;
import dao.custom.impl.ReturnOrderDAOIml;


public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case RETURNORDER:
                return new ReturnOrderBOImpl();
            case ORDERDETAILS:
                return new OrderDetailsBOImpl();
            case ADMINLOGIN:
                return new AdminLoginBOImpl();
            case CASHIERLOGIN:
                return new CashierLoginBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, ORDER,RETURNORDER,ORDERDETAILS,ADMINLOGIN,CASHIERLOGIN
    }
}
