package com.example.diskcollectionfx;
import com.example.diskcollectionfx.logic.domain.Disk;
import com.example.diskcollectionfx.logic.domain.DiskType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
public class DetailedLookScreenController {
    @FXML
    private Label diskName;
    @FXML
    private Label diskDescription;
    @FXML
    private Label diskType;
    @FXML
    private Label diskCategories;
    @FXML
    private Button backButton;
    private Disk disk;
    public DetailedLookScreenController(Disk newDisk, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("detailed-look-screen.fxml"));
            disk = newDisk;
            loader.setController(this);
            stage.setScene(new Scene(loader.load(), 700, 700));
            stage.setTitle("Disk");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initialize() {
        backButton.setOnAction(actionEvent -> {
            onBackClick();
        });
        diskName.setText("Name - " + disk.getName());

        String type;
        if (disk.getType() == DiskType.DVD) {
            type = "DVD";
        } else if (disk.getType() == DiskType.CDR) {
            type = "CD-R";
        } else {
            type = "mini-disk";
        }

        diskType.setText("Type - " + type);
        diskDescription.setText("Description - " + disk.getDescription());
        String[] categoryString = new String[0];
        try {
            categoryString = (String[])disk.getCategories().getArray();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String category = String.join(",", categoryString);
        diskCategories.setText("Categories - " + category);
    }
    @FXML
    protected void onBackClick() {
        Stage stage = null;
        Parent myNewScene = null;
        try {
            stage = (Stage) backButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("all-items.fxml")));
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

}