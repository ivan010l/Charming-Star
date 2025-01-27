package charming_star;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    public void handleSignup() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.WARNING, "Passwords do not match.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management", "root", "");
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION, "Signup successful!");
                // Redirect to login screen if needed
            } else {
                showAlert(AlertType.ERROR, "Signup failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database connection failed.");
        }
    }

    public void goToLogin() {
                // Load the Signup FXML and switch to the signup screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
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
