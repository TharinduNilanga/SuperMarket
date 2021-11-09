package controller;

import bo.BoFactory;
import bo.custom.OrderDetailsBO;
import dto.CustomerDTO;
import dto.OrderDetailsDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsFormController {
    public AnchorPane root;
    public TableView tblOrderDetails;
    public TableColumn colOId;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    private final OrderDetailsBO orderDetailsBO= (OrderDetailsBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDERDETAILS);
    public void initialize() {
        getAll();
        colOId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    public void HomeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/DashBoard.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin");
        primaryStage.centerOnScreen();
    }
    public void getAll(){

        tblOrderDetails.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<OrderDetailsDTO> orderDetails=orderDetailsBO.getAllOrder();
            for (OrderDetailsDTO o : orderDetails) {
                tblOrderDetails.getItems().add(new OrderDetailsDTO(o.getOrderId(),o.getItemCode(),o.getOrderQty(),o.getDiscount(),o.getTotal()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
