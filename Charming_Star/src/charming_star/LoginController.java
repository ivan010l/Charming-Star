package charming_star;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management", "root", "");
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Successful login: Show a success alert and go to the main screen
                showAlert(AlertType.INFORMATION, "Login successful!");
                
                // Load Main.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                Scene mainScene = new Scene(loader.load());
                
                // Get current stage and set the main screen
                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.setScene(mainScene);
                currentStage.show();
            } else {
                showAlert(AlertType.ERROR, "Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database connection failed.");
        }
    }

    public void goToSignup() {
        // Load the Signup FXML and switch to the signup screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Scene signupScene = new Scene(loader.load());
            
            // Get current stage and switch to the signup screen
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setScene(signupScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Failed to load signup screen.");
        }
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
