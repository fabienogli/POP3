package Client.Interface;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import javafx.event.ActionEvent;

import java.util.Optional;

public class Controller {


    @FXML
     Button list;
    @FXML
    Button retr = new Button();
    @FXML
    Button dele;

    @FXML
    private void handleLoginButton(ActionEvent evt) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connexion");
        dialog.setHeaderText("Entrez nom d'utilisateur et mot de passe :");
        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Utilisateur");
        PasswordField password = new PasswordField();
        password.setPromptText("Mot de passe");

        grid.add(new Label("login:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("mot de passe:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });

    }

    @FXML
    private void handleRetrieveButton(ActionEvent event) {
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
    }

    @FXML
    private void handleStatButton(ActionEvent event) {
    }

    @FXML
    private void handleListButton() {
    }

    public Button getList() {
        return list;
    }

    public void setList(Button list) {
        this.list = list;
    }
}
