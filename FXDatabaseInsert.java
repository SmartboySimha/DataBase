import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class FXDatabaseInsert extends Application {

    @Override
    public void start(Stage stage) {
        // UI elements
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");

        Button submitButton = new Button("Submit");

        Label statusLabel = new Label();

        // Submit button logic
        submitButton.setOnAction(e -> {
            int id;
            try {
                id = Integer.parseInt(idField.getText());
            } catch (NumberFormatException ex) {
                statusLabel.setText("ID must be a number.");
                return;
            }

            String name = nameField.getText();
            String email = emailField.getText();

            if (name.isEmpty() || email.isEmpty()) {
                statusLabel.setText("Name and Email cannot be empty.");
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/DS", "root", "root");

                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO stdemo VALUES (?, ?, ?)");
                pst.setInt(1, id);
                pst.setString(2, name);
                pst.setString(3, email);

                int result = pst.executeUpdate();
                if (result > 0) {
                    statusLabel.setText("Data inserted successfully!");
                    idField.clear();
                    nameField.clear();
                    emailField.clear();
                } else {
                    statusLabel.setText("Failed to insert data.");
                }

                pst.close();
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10, idField, nameField, emailField, submitButton, statusLabel);
        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.setTitle("Insert to MySQL (JavaFX)");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 // using vs code by extention sql connect and add sdk and connect server then develope a databse
