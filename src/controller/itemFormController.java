package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXTextField;
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
import view.tm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class itemFormController {
    public AnchorPane root;
    public TableView tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public Label lblItemCode;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public Button btnAddNew;

    public void initialize() {
     lblItemCode.setText(generateNewId());
     getAllItems();
     colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
     colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
     colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
     colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
     colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));


    }
    private String generateNewId() {
        try {
            return itemBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I001";
    }
    public void HomeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/DashBoard.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin");
        primaryStage.centerOnScreen();
    }

    public void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
      /*  System.out.println(txtItemCode.getText());
        System.out.println(txtPackSize.getText());
        System.out.println(txtQtyOnHand.getText());
        System.out.println(txtUnitPrice.getText());
        System.out.println(txtDescription.getText());
*/

        String  code = txtItemCode.getText();
        String description = txtDescription.getText();

        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }
        System.out.println(code);
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        String  packSize=txtPackSize.getText();
/*       if (btnSave.getText().equalsIgnoreCase("save")) {
           try {
                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save Item
                ItemDTO dto = new ItemDTO(code, description,packSize, unitPrice, qtyOnHand);
                itemBO.addItem(dto);

                tblItem.getItems().add(new ItemTM(code, description,packSize, unitPrice, qtyOnHand));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


       } else {
           try {
                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }
                //Update Item
                ItemDTO dto = new ItemDTO(code, description,packSize, unitPrice, qtyOnHand);
                itemBO.updateItem(dto);

                ItemTM selectedItem = (ItemTM) tblItem.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setQtyOnHand(qtyOnHand);
                selectedItem.setUnitPrice(unitPrice);
                tblItem.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
       }*/
        ItemDTO dto = new ItemDTO(code, description,packSize, unitPrice, qtyOnHand);
        if(itemBO.addItem(dto)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getAllItems();
            txtQtyOnHand.clear();
            txtUnitPrice.clear();
            txtPackSize.clear();
            txtDescription.clear();
            txtItemCode.clear();
            lblItemCode.setText(generateNewId());
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
        btnAddNew.fire();
    }
    public void getAllItems(){

        tblItem.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItem.getItems().add(new ItemTM(item.getItemCode(), item.getDescription(),item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(code);
    }
    public void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String  code = txtItemCode.getText();
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        String  packSize=txtPackSize.getText();

        ItemDTO dto = new ItemDTO(code, description,packSize, unitPrice, qtyOnHand);
        if(itemBO.updateItem(dto)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getAllItems();
            txtQtyOnHand.clear();
            txtUnitPrice.clear();
            txtPackSize.clear();
            txtDescription.clear();
            txtItemCode.clear();
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
        if (itemBO.deleteItem(txtItemCode.getText())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
            notification.showAndDismiss(Duration.millis(8000));
            getAllItems();
            txtItemCode.clear();
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
        txtItemCode.setText(generateNewId());
    }

    public void itemCodeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String code=txtItemCode.getText();
        ItemDTO item=itemBO.search(code);
        if (item==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(item);


        }
    }

    private void setData(ItemDTO item) {
        txtItemCode.setText(item.getItemCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }

    public void descriptonOnAction(ActionEvent event) {
        String textRegEx=("^[(A-z)(\\s)]*|[(A-z)(,)]*|[(A-z)(,)(\\s)]$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtDescription.getText();
        if (pattern.matcher(brand).matches()){
            txtDescription.setStyle("-fx-border-color: green");
            txtPackSize.requestFocus();
        }else{
            txtDescription.setStyle("-fx-border-color: red");
        }
    }
    public void validateQTY(TextField textField){
        String textRegEx=("^[0-9]{1,5}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.setStyle("-fx-border-color: green");
        }else{
            textField.setStyle("-fx-border-color: red");
        }
    }
    public void PackSizeOnAction(ActionEvent event) {
        validateQTY(txtPackSize);
        txtUnitPrice.requestFocus();
    }

    public void unitPriceOnAction(ActionEvent event) {
        String textRegEx=("^[0-9]*?[0-9]*(.00)*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtUnitPrice.getText();
        if (pattern.matcher(brand).matches()){
            txtUnitPrice.setStyle("-fx-border-color: green");
           txtQtyOnHand.requestFocus();
        }else{
            txtUnitPrice.setStyle("-fx-border-color: red");
        }
    }

    public void qtyOnAction(ActionEvent event) {
        validateQTY(txtQtyOnHand);
    }
}
