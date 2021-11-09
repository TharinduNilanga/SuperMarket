package controller;

import bo.BoFactory;
import bo.custom.OrderBO;
import bo.custom.OrderDetailsBO;
import bo.custom.ReturnOrderBO;
import com.jfoenix.controls.JFXTextField;
import dao.DAOFactory;
import dao.custom.OrderDetailsDAO;
import dao.custom.ReturnDAO;
import dto.CustomerDTO;
import dto.OrderDetailsDTO;
import dto.ReturnDTO;
import entity.OrderDetails;
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
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnFormController {
    public AnchorPane root;
    public ComboBox cmbOrderId;
    public JFXTextField txtItemCode;
    public JFXTextField txtQty;
    public JFXTextField txtTotal;
    public JFXTextField txtDiscount;
    public TableView tblReturn;
    public TableColumn colReturnId;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblReturnId;
    public Button btnSave;
    private final ReturnOrderBO returnBO= (ReturnOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.RETURNORDER);
    private final OrderDetailsBO orderDetailsBO= (OrderDetailsBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDERDETAILS);


    public void initialize() {
        loadAll();
        getAllReturn();
        lblReturnId.setText(generateNewId());
        System.out.println(generateNewId());
        colReturnId.setCellValueFactory(new PropertyValueFactory<>("returnId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    public void HomeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/CashierDashBoardForm.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cashier");
        primaryStage.centerOnScreen();
    }
    private void loadAll() {
        try {
            ArrayList<OrderDetailsDTO> all = orderDetailsBO.getAllOrder();
            for (OrderDetailsDTO orderDetailsDTO : all) {
                cmbOrderId.getItems().add(orderDetailsDTO.getOrderId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cmbOrderIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String oId=cmbOrderId.getValue().toString();
        OrderDetailsDTO orderDetailsDTO=orderDetailsBO.searchOrder(oId);
        if (orderDetailsDTO==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }else{
            setData(orderDetailsDTO);
        }
    }

    private void setData(OrderDetailsDTO o) {
        txtItemCode.setText(o.getItemCode());
        txtDiscount.setText(String.valueOf(o.getDiscount()));
        txtQty.setText(String.valueOf(o.getOrderQty()));
        txtTotal.setText(String.valueOf(o.getTotal()));
    }

    public void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String ReturnId=lblReturnId.getText();
        String orderId=cmbOrderId.getValue().toString();
        String itemCode=txtItemCode.getText();
        int qty=Integer.parseInt(txtQty.getText());
        int  discount=Integer.parseInt(txtDiscount.getText());
        double total=Double.parseDouble(txtTotal.getText());

        ReturnDTO returnDTO=new ReturnDTO(ReturnId,orderId,itemCode,qty,discount,total);
        if(returnBO.add(returnDTO)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getAllReturn();
            lblReturnId.setText(generateNewId());

        }else{
            String title = "Fail ";
            String message = "TryAgain";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.WARNING);
            notification.showAndDismiss(Duration.millis(8000));
        }
    }
    public void getAllReturn(){

        tblReturn.getItems().clear();
        try {

            ArrayList<ReturnDTO> returnDTOS = returnBO.getAll();
            for (ReturnDTO r: returnDTOS) {
                tblReturn.getItems().add(new ReturnDTO(r.getReturnId(),r.getOrderId(),r.getItemCode(),r.getOrderQty(),r.getDiscount(),r.getTotal()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private String generateNewId() {
        try {
            return returnBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "R001";
    }

}
