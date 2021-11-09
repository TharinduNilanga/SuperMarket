package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
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
import view.tm.CustomerTM;
import view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class AddCustomerFormController {
    public AnchorPane root;
    public TableView tblCustomer;
    public TableColumn colCustId;
    public TableColumn colTitle;
    public TableColumn colCustName;
    public TableColumn colCustAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public JFXTextField txtCustomerId;
    public JFXTextField txtTitle;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Label lblCustomerId;
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public void initialize() {
        lblCustomerId.setText(generateNewId());
        getAllCustomer();
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("custTitle"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));


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
            return customerBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "C001";
    }


    public void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id=txtCustomerId.getText();
        String Ctitle=txtTitle.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String city=txtCity.getText();
        String province=txtProvince.getText();
        String postalCode=txtPostalCode.getText();

        CustomerDTO dto=new CustomerDTO(id,Ctitle,name,address,city,province,postalCode);
        if(customerBO.addCustomer(dto)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getAllCustomer();
            lblCustomerId.setText(generateNewId());
            txtCustomerId.setText(generateNewId());
            txtPostalCode.clear();
            txtProvince.clear();
            txtAddress.clear();
            txtName.clear();
            txtCity.clear();
            txtTitle.clear();
            txtCustomerId.clear();
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
    public void getAllCustomer(){

       tblCustomer.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<CustomerDTO> customer = customerBO.getAllCustomer();
            for (CustomerDTO customerDTO : customer) {
                tblCustomer.getItems().add(new CustomerDTO(customerDTO.getCustId(),customerDTO.getCustTitle(),customerDTO.getCustName(),customerDTO.getCustAddress(),customerDTO.getCity(),customerDTO.getProvince(),customerDTO.getPostalCode()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id=txtCustomerId.getText();
        String Ctitle=txtTitle.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String city=txtCity.getText();
        String province=txtProvince.getText();
        String postalCode=txtPostalCode.getText();

        CustomerDTO dto=new CustomerDTO(id,Ctitle,name,address,city,province,postalCode);
        if(customerBO.updateCustomer(dto)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getAllCustomer();
            txtPostalCode.clear();
            txtProvince.clear();
            txtAddress.clear();
            txtName.clear();
            txtCity.clear();
            txtTitle.clear();
            txtCustomerId.clear();
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

    public void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (customerBO.deleteCustomer(txtCustomerId.getText())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
            notification.showAndDismiss(Duration.millis(8000));
            getAllCustomer();
            lblCustomerId.setText(generateNewId());
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

    public void btnAddNew_OnAction(ActionEvent event) {
        txtCustomerId.setText(generateNewId());
    }

    public void CustomerIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        validateId(txtCustomerId);
        String id=txtCustomerId.getText();
        CustomerDTO cust=customerBO.search(id);
        if (cust==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }else{
            setData(cust);
        }
    }

    private void setData(CustomerDTO cust) {
        txtCustomerId.setText(cust.getCustId());
        txtTitle.setText(cust.getCustTitle());
        txtName.setText(cust.getCustName());
        txtAddress.setText(cust.getCustAddress());
        txtCity.setText(cust.getCity());
        txtProvince.setText(cust.getProvince());
        txtPostalCode.setText(cust.getPostalCode());
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
    public void validateId(TextField textField) {
        String textRegEx = ("^[A-z][0-9]{3}$");
        Pattern pattern = Pattern.compile(textRegEx);
        String brand = textField.getText();
        if (pattern.matcher(brand).matches()) {
            textField.setStyle("-fx-border-color: green");
        } else {
            textField.setStyle("-fx-border-color: red");
        }
    }
    public void validateCode(TextField textField){
        String textRegEx=("^[0-9]{0,3}(-)[0-9]{7}|[0-9]{0,10}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.setStyle("-fx-border-color: green");
        }else{
            textField.setStyle("-fx-border-color: red");
        }
    }
    public void TitleOnAction(ActionEvent event) {
        validateName(txtTitle);
        txtName.requestFocus();
    }

    public void NameOnAction(ActionEvent event) {
        validateName(txtName);
        txtAddress.requestFocus();
    }

    public void AddressOnAction(ActionEvent event) {
        String textRegEx = ("^[(A-z)(1-9)]*[A-z] *[1-9]*$");
        Pattern pattern = Pattern.compile(textRegEx);
        String brand = txtAddress.getText();
        if (pattern.matcher(brand).matches()) {
            txtAddress.setStyle("-fx-border-color: green");
            txtCity.requestFocus();
        } else {
            txtAddress.setStyle("-fx-border-color: red");
        }
    }

    public void CityOnAction(ActionEvent event) {
        validateName(txtCity);
        txtProvince.requestFocus();
    }

    public void ProvinceOnAction(ActionEvent event) {
        validateName(txtProvince);
        txtPostalCode.requestFocus();
    }

    public void CodeOnAction(ActionEvent event) {
        validateCode(txtPostalCode);
    }
}
