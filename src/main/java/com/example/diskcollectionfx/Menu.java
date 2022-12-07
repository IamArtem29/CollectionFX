package com.example.diskcollectionfx;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
public class Menu {
    @FXML
    private Button allItemsButton;
    @FXML
    private Button addNewItemButton;
    @FXML
    private Button exitButton;
    @FXML
    protected void onAllItemsClick() {
        Stage stage = null;
        Parent myNewScene = null;
        try {
            stage = (Stage) allItemsButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("all-disks.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (myNewScene != null) {
            Scene scene = new Scene(myNewScene, 700, 700);
            stage.setScene(scene);
            stage.setTitle("Collection");
            stage.show();
        }
    }
    @FXML
    protected void onAddNewItemClick() {
        Stage stage = null;
        Parent myNewScene = null;
        try {
            stage = (Stage) addNewItemButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("disk-create.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (myNewScene != null) {
            Scene scene = new Scene(myNewScene, 700, 700);
            stage.setScene(scene);
            stage.setTitle("Create");
            stage.show();
        }
    }
    @FXML
    protected void onExitClick() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}