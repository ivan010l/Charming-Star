package charming_star;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtuname;

    @FXML
    private PasswordField txtpass;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnCancel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String uname = txtuname.getText();
        String pass = txtpass.getText();

        if (uname.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "UserName or Password cannot be empty", Alert.AlertType.ERROR);
        } else if (uname.equals("Trainer") && pass.equals("0219")) {
            // Close the login window and open the main application window
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();
            
            // Main window can be opened here (you would need to load and display it)
            // Example: new MainWindow().show();
        } else {
            showAlert("Error", "UserName or Password Do Not Match", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        // Close the current window
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
