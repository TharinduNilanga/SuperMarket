package controller;

import bo.BoFactory;
import bo.custom.AdminLoginBO;
import bo.custom.CashierLoginBO;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXTextField;
import dto.AccountsDTO;
import dto.CustomerDTO;
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
import java.util.regex.Pattern;

public class AccountFormController {
    public TableView tblAdmin;
    public TableColumn colAdminId;
    public TableColumn colAdminPassword;
    public TableView tblCashier;
    public TableColumn colCashierId;
    public TableColumn colCashierPassword;
    public JFXTextField txtAdminId;
    public JFXTextField txtPasswordAdmin;
    public Label lblAdminId;
    public Button btnDeleteAdmin;
    public Button btnSaveAdmin;
    public Button btnAddAdminNew;
    public JFXTextField txtCashierId;
    public JFXTextField txtCashierPassword;
    public Label lblCashierId;
    public Button btnDeleteCashier;
    public Button btnSaveCashier;
    public Button btnAddNewCashier;
    public AnchorPane root;
    public JFXTextField txtAdminUserName;
    public JFXTextField txtCashierUserName;
    private final AdminLoginBO adminLoginBO = (AdminLoginBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ADMINLOGIN);
    private final CashierLoginBO cashierLoginBO = (CashierLoginBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CASHIERLOGIN);
    public TableColumn colAdminUserName;
    public TableColumn colCashierUserName;


    public void initialize() {
       btnSaveAdmin.setDisable(true);
       btnSaveCashier.setDisable(true);
       lblAdminId.setText(generateNewId());
       lblCashierId.setText(generateNewIdCashier());
       getAllAdmin();
       getAllCashier();
       colAdminId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colAdminUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
       colAdminPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
       colCashierId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colCashierUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
       colCashierPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }
    private String generateNewId() {
        try {
            return adminLoginBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "A001";
    }
    private String generateNewIdCashier() {
        try {
            return cashierLoginBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "C001";
    }
    public void btnAddAdmin_OnAction(ActionEvent event) {
        btnSaveAdmin.setDisable(false);
        txtAdminId.setText(generateNewId());
    }


    public void DeleteAdminOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (adminLoginBO.delete(txtAdminId.getText())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
            notification.showAndDismiss(Duration.millis(8000));
            getAllAdmin();
            lblAdminId.setText(generateNewId());
            txtAdminId.clear();
        }else{
            String title = "Failed";
            String message = "Try Again";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.WARNING);
            notification.showAndDismiss(Duration.millis(8000));
        }
    }

    public void SaveAdminOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id=txtAdminId.getText();
        String userName=txtAdminUserName.getText();
        String password=txtPasswordAdmin.getText();
        AccountsDTO dto=new AccountsDTO(id,userName,password);
        if(adminLoginBO.add(dto)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            lblAdminId.setText(generateNewId());
            getAllAdmin();
            txtAdminId.clear();
            txtAdminUserName.clear();
            txtPasswordAdmin.clear();
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

    public void btnAddNewCashier_OnAction(ActionEvent event) {
        btnSaveCashier.setDisable(false);
        txtCashierId.setText(generateNewIdCashier());
    }


    public void DeleteCashierOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (cashierLoginBO.delete(txtCashierId.getText())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
            notification.showAndDismiss(Duration.millis(8000));
            lblCashierId.setText(generateNewIdCashier());
            getAllCashier();
            txtCashierId.clear();
        }else{
            String title = "Failed";
            String message = "Try Again";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.WARNING);
            notification.showAndDismiss(Duration.millis(8000));
        }
    }

    public void SaveCashierOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id=txtCashierId.getText();
        String userName=txtCashierUserName.getText();
        String password=txtCashierPassword.getText();
        AccountsDTO dto=new AccountsDTO(id,userName,password);
        if(cashierLoginBO.add(dto)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            lblCashierId.setText(generateNewIdCashier());
            getAllCashier();
            txtCashierId.clear();
            txtCashierUserName.clear();
            txtCashierPassword.clear();
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

    public void HomeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/DashBoard.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin");
        primaryStage.centerOnScreen();
    }
    public void getAllAdmin(){

        tblAdmin.getItems().clear();
        try {
            ArrayList<AccountsDTO> accountsDTOS = adminLoginBO.getAll();
            for (AccountsDTO accounts: accountsDTOS) {
                tblAdmin.getItems().add(new AccountsDTO(accounts.getId(),accounts.getUserName(),accounts.getPassword()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void getAllCashier(){

        tblCashier.getItems().clear();
        try {
            ArrayList<AccountsDTO> accountsDTOS = cashierLoginBO.getAll();
            for (AccountsDTO accounts: accountsDTOS) {
                tblCashier.getItems().add(new AccountsDTO(accounts.getId(),accounts.getUserName(),accounts.getPassword()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void validateName(TextField textField){
        String textRegEx=("^[A-z]*|[A-z]*(\\s)[A-z]*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.setStyle("-fx-border-color: green");
        }else{
            textField.setStyle("-fx-border-color: red");
        }
    }
    public void validateId(TextField textField){
        String textRegEx=("^[A-z](-)[0-9]{3}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.setStyle("-fx-border-color: green");
        }else{
            textField.setStyle("-fx-border-color: red");
        }
    }

    public void AdminUserNameOnAction(ActionEvent event) {
      validateName(txtAdminUserName);
      txtPasswordAdmin.requestFocus();
    }

    public void AdminIdOnAction(ActionEvent event) {
        validateId(txtAdminId);

    }

    public void AdminPasswordOnAction(ActionEvent event) {
        String password=("^[0-9]* ?[(0-9)(A-z)]* ?[(0-9)(*)]* ?[a-z]*$");

        Pattern passwordPattern=Pattern.compile(password);
        String typePassword=txtPasswordAdmin.getText();
        if (passwordPattern.matcher(typePassword).matches()){
            txtPasswordAdmin.setStyle("-fx-border-color: green");
        }else{
            txtPasswordAdmin.setStyle("-fx-border-color: red");

        }
    }

    public void CasheirIdOnAction(ActionEvent event) {
       validateId(txtCashierId);

    }

    public void cashierPassordOnAction(ActionEvent event) {
        String password=("^[0-9]* ?[(0-9)(A-z)]* ?[(0-9)(*)]* ?[a-z]*$");

        Pattern passwordPattern=Pattern.compile(password);
        String typePassword=txtCashierPassword.getText();
        if (passwordPattern.matcher(typePassword).matches()){
            txtCashierPassword.setStyle("-fx-border-color: green");
        }else{
            txtCashierPassword.setStyle("-fx-border-color: red");

        }
    }

    public void CashierUserNameOnAction(ActionEvent event) {
    validateName(txtCashierUserName);
    txtCashierPassword.requestFocus();
    }

}
