package controller;

import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {


    public AnchorPane root;
    public TextField txtUserName;
    public PasswordField txtPassword;

    public void btnLogin(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();


        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Admin WHERE userName=? AND password=md5(?) ");
        preparedStatement.setObject(1, userName);
        preparedStatement.setObject(2, password);
        ResultSet set = preparedStatement.executeQuery();

        PreparedStatement stm = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Cashier WHERE userName=? AND password=md5(?) ");
        stm.setObject(1, userName);
        stm.setObject(2, password);

        ResultSet rst = stm.executeQuery();


        if (set.next()) {
            Parent root = FXMLLoader.load(this.getClass().getResource("../view/DashBoard.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Cashier ");
            primaryStage.centerOnScreen();
        } else if (rst.next()) {
            Parent root = FXMLLoader.load(this.getClass().getResource("../view/CashierDashBoardForm.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(scene);

            primaryStage.setTitle("Admin");
            primaryStage.centerOnScreen();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid Password/userName");
            Optional<ButtonType> buttonType = alert.showAndWait();
            txtUserName.clear();
            txtPassword.clear();
            txtUserName.requestFocus();

        }
    }
}
