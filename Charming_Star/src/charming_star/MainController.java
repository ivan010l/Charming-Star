package charming_star;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private ComboBox<String> membershipTypeBox;

    @FXML
    private DatePicker joinDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize ComboBox items
        membershipTypeBox.getItems().addAll("Basic", "Premium", "VIP");
    }

    @FXML
    public void addMember() {
        String name = nameField.getText();
        String age = ageField.getText();
        String membershipType = membershipTypeBox.getValue();
        String joinDate = joinDatePicker.getValue().toString();

        if (name.isEmpty() || age.isEmpty() || membershipType == null || joinDate == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        try (Connection connection = DatabaseHandler.getConnection()) {
            String query = "INSERT INTO members (name, age, membership_type, join_date) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, Integer.parseInt(age));
            statement.setString(3, membershipType);
            statement.setString(4, joinDate);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Member added successfully.");
                clearFields();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        ageField.clear();
        membershipTypeBox.setValue(null);
        joinDatePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void viewMembers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewMembers.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("View Members");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
