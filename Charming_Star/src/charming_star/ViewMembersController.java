package charming_star;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ViewMembersController {

    @FXML
    private TableView<Member> membersTable;

    @FXML
    private TableColumn<Member, Integer> idColumn;

    @FXML
    private TableColumn<Member, String> nameColumn;

    @FXML
    private TableColumn<Member, Integer> ageColumn;

    @FXML
    private TableColumn<Member, String> membershipTypeColumn;

    @FXML
    private TableColumn<Member, String> joinDateColumn;

    private ObservableList<Member> membersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        membershipTypeColumn.setCellValueFactory(new PropertyValueFactory<>("membershipType"));
        joinDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

        // Load data into the table
        loadMembers();
    }

    private void loadMembers() {
        membersList.clear(); // Clear previous data
        try (Connection connection = DatabaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members")) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String membershipType = resultSet.getString("membership_type");
                String joinDate = resultSet.getString("join_date");

                membersList.add(new Member(id, name, age, membershipType, joinDate));
            }

            membersTable.setItems(membersList);
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }
    }

    @FXML
    public void handleUpdate() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember == null) {
            showAlert("No Selection", "Please select a member to update.");
            return;
        }

        // Open a dialog or new window to edit the selected member
        // For simplicity, let's update the membership type directly in this example:
        try (Connection connection = DatabaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE members SET membership_type = ? WHERE id = ?")) {

            // Example: Update membership type to "Updated Type"
            statement.setString(1, "Updated Type");
            statement.setInt(2, selectedMember.getId());
            statement.executeUpdate();

            showAlert("Success", "Member updated successfully!");
            loadMembers(); // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember == null) {
            showAlert("No Selection", "Please select a member to delete.");
            return;
        }

        // Confirm deletion
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText("Are you sure you want to delete this member?");
        confirmation.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection connection = DatabaseHandler.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "DELETE FROM members WHERE id = ?")) {

                statement.setInt(1, selectedMember.getId());
                statement.executeUpdate();

                showAlert("Success", "Member deleted successfully!");
                loadMembers(); // Refresh the table
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void goBack() {
        // Close the current window
        Stage stage = (Stage) membersTable.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
