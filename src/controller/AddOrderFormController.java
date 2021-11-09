package controller;

import bo.BoFactory;
import bo.custom.OrderBO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddOrderFormController {
    public AnchorPane root;
    public TableView tblOrder;
    public TableColumn colCustId;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colQty;
    public TableColumn colTotal;
    public JFXComboBox cmbCustomerId;
    public JFXComboBox cmbItemId;
    public JFXTextField txtCustTitle;
    public JFXTextField txtCustName;
    public JFXTextField txtCustAddress;
    public JFXTextField txtCity;
    public JFXTextField txtPostalCode;
    public JFXTextField txtProvince;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public Button btnClear;
    public Button btnAddToCart;
    public Label lblTotal;
    public Button btnPlaceOrder;
    public Label lblDate;
    private final OrderBO orderBO = (OrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    public Label lblOrderId;
    public Button btnPrintBill;

    public void initialize() {
        System.out.println(generateNewId());
        lblOrderId.setText(generateNewId());
        loadAllCustomerIds();
        loadAllItemCodes();
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }
    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = orderBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getCustId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> all = orderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemId.getItems().add(dto.getItemCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cmbItemIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemCode=cmbItemId.getValue().toString();
        ItemDTO item=orderBO.searchItem(itemCode);
        if (item==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(item); }
    }
    private void setData(ItemDTO item) {
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }
    public void cmbCustomerIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String cId=cmbCustomerId.getValue().toString();
        CustomerDTO customerDTO=orderBO.searchCustomer(cId);
        if (customerDTO==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }else{
            setData(customerDTO);
        }
    }

    private void setData(CustomerDTO cust) {
        txtCustTitle.setText(cust.getCustTitle());
        txtCustName.setText(cust.getCustName());
        txtCustAddress.setText(cust.getCustAddress());
        txtCity.setText(cust.getCity());
        txtProvince.setText(cust.getProvince());
        txtPostalCode.setText(cust.getPostalCode());
    }
    ObservableList<CartTM> obList= FXCollections.observableArrayList();
    public void AddToCartOnAction(ActionEvent event) {
        int QtyOnHand=Integer.parseInt(txtQtyOnHand.getText());
        int Qty=Integer.parseInt(txtQty.getText());
        int Discount=Integer.parseInt(txtDiscount.getText());
        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        double price=(Qty)*(unitPrice);
        double discPrice=(price/100)*Discount;
        double total=(price)-(discPrice);

        if (Qty > QtyOnHand){
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }
        CartTM tm=new CartTM(
                (String)cmbCustomerId.getValue(),
                (String)cmbItemId.getValue(),
                txtDescription.getText(),
                unitPrice,
                Discount,
                Qty,
                total

        );
        int rowNumber = isExists(tm);
        if (rowNumber == -1) {// new Add
            obList.add(tm);

        }else{
            CartTM temp=obList.get(rowNumber);
            CartTM newTm=new CartTM(
                    temp.getCustId(),
                    temp.getItemId(),
                    temp.getDescription(),
                    temp.getUnitPrice(),
                    temp.getDiscount(),
                    temp.getQty()+Qty,
                    temp.getTotal()+total
            );
            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblOrder.setItems(obList);
        calculateCost();
    }
    private int isExists(CartTM tm){
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemId().equals(obList.get(i).getItemId())){
                return i;
            }
        }
        return -1;
    }

    void calculateCost(){
        double ttl=0;
        for (CartTM tm:obList
        ) {
            ttl+=tm.getTotal();
        }
        lblTotal.setText(ttl+" /=");


    }
/*
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId,Double unitPrice, List<OrderDetailsDTO> orderDetail) {
        try {
            OrderDTO orderDTO = new OrderDTO(orderId, orderDate, customerId, orderDetail);
            return purchaseOrderBO.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
*/

    public void PlaceOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsDTO> orderDetail=new ArrayList<>();
        double total=0;
        String oId=lblOrderId.getText();
        for (CartTM  tm:obList) {
            total += tm.getTotal();
            orderDetail.add(new OrderDetailsDTO(oId, tm.getItemId(), tm.getQty(), tm.getDiscount(), tm.getTotal()));
        }
        System.out.println(orderDetail);
        OrderDTO dto=new OrderDTO(
                lblOrderId.getText(),
                LocalDate.now(),
                (String)cmbCustomerId.getValue(),
                total,
                orderDetail
        );
        System.out.println(dto);
        if (orderBO.purchaseOrder(dto)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            txtQty.clear();
            txtCustName.clear();
            txtDescription.clear();
            txtUnitPrice.clear();
            txtDiscount.clear();
            txtQtyOnHand.clear();
            txtCity.clear();txtCustAddress.clear();
            txtCustTitle.clear();
            txtPackSize.clear();
            txtPostalCode.clear();
            txtProvince.clear();
            cmbItemId.getSelectionModel().clearSelection();
            cmbCustomerId.getSelectionModel().clearSelection();

        }else{
            String title = "Fail ";
            String message = "TryAgain";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
        }
        /* boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(),
                tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblId.setText("Order Id: " + orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        txtQty.clear();
        calculateTotal();*/
    }

    int cartSelectedRowForRemove = -1;
    public void ClearOnAction(ActionEvent event) {

            if (cartSelectedRowForRemove==-1){
                new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
            }else{
                obList.remove(cartSelectedRowForRemove);
                calculateCost();
                tblOrder.refresh();
            }
    }
    public void HomeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/CashierDashBoardForm.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cashier");
        primaryStage.centerOnScreen();
    }
    private String generateNewId() {
        try {
            return orderBO.generateNewOrderId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void QtyOnAction(ActionEvent event) {
        String textRegEx=("^[0-9]{1,5}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtQty.getText();
        if (pattern.matcher(brand).matches()){
            txtQty.setStyle("-fx-border-color: green");

            txtDiscount.requestFocus();
        }else{
            txtQty.setStyle("-fx-border-color: red");
        }
    }

    public void DiscountOnAction(ActionEvent event) {
        String textRegEx=("^[0-9]{1,5}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtDiscount.getText();
        if (pattern.matcher(brand).matches()){
            txtDiscount.setStyle("-fx-border-color: green");
            btnAddToCart.setDisable(false);
        }else{
            txtDiscount.setStyle("-fx-border-color: red");
        }
    }
}
