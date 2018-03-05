package Client.Interface;

import Client.Application.Client;

import Server.Utilisateur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import javafx.event.ActionEvent;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.util.Optional;

public class Controller {

    protected Client client;

    public Controller() {
    }

    @FXML
    Button login;
    @FXML
    Button list;
    @FXML
    Button retr ;
    @FXML
    Button dele;
    @FXML
    Button stat;
    @FXML
    TextArea textArea;
    @FXML
    Label statusLabel;

    @FXML
    private void initialize() throws IOException {
        list.setDisable(true);
        retr.setDisable(true);
        dele.setDisable(true);
        stat.setDisable(true);
        client = new Client();
    }

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
            Utilisateur utilisateur = new Utilisateur(usernamePassword.getKey());
            utilisateur.setMdp(usernamePassword.getValue());
            client.setUtilisateur(utilisateur);
            if(client.authentification()){
                statusLabel.setText("Status : Connecté");
                list.setDisable(false);
                retr.setDisable(false);
                dele.setDisable(false);
                stat.setDisable(false);
                client.createMailFile();
            }
        });

    }

    @FXML
    private void handleRetrieveButton(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Connexion");
        dialog.setHeaderText("Entrez nom d'utilisateur et mot de passe :");
        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numMessage = new TextField();
        numMessage.setPromptText("");

        grid.add(new Label("Numéro du message:"), 0, 0);
        grid.add(numMessage, 1, 0);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        // Do some validation (using the Java 8 lambda syntax).
        numMessage.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> numMessage.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return numMessage.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(numMessageTmp -> {
            System.out.println(result.get());
            String reponseServer= client.retr(Integer.parseInt(result.get()));
            this.textArea.setText(reponseServer);
        });

    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
    }

    @FXML
    private void handleStatButton(ActionEvent event) {
    }

    @FXML
    private void handleListButton() {
    String reponseServer=client.list();
    this.textArea.setText(reponseServer);
    }

    @FXML
    public void disableList() {
        list.setDisable(true);

    }


}
