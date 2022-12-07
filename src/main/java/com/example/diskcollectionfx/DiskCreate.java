package com.example.diskcollectionfx;
import com.example.diskcollectionfx.consoleapp.service.DiskService;
import com.example.diskcollectionfx.consoleapp.domain.Disk;
import com.example.diskcollectionfx.consoleapp.domain.DiskType;
import com.example.diskcollectionfx.consoleapp.repository.DBConnector;
import com.example.diskcollectionfx.consoleapp.repository.DiskRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Objects;
public class DiskCreate {
    @FXML
    private TextField diskName;
    @FXML
    private TextField diskDescription;
    @FXML
    private ToggleButton diskTypeDVD;
    @FXML
    private ToggleButton diskTypeCDR;
    @FXML
    private ToggleButton diskTypeMINI_DISK;
    @FXML
    private TextField diskCategories;
    @FXML
    private Button backButton;
    @FXML
    protected void onDVDChoice() {
        diskTypeCDR.setSelected(false);
        diskTypeMINI_DISK.setSelected(false);
    }
    @FXML
    protected void onCDRChoice() {
        diskTypeDVD.setSelected(false);
        diskTypeMINI_DISK.setSelected(false);
    }
    @FXML
    protected void onMINI_DISKChoice() {
        diskTypeDVD.setSelected(false);
        diskTypeCDR.setSelected(false);
    }
    @FXML
    protected void onBackClick() {
        Stage stage = null;
        Parent myNewScene = null;
        try {
            stage = (Stage) backButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("start-screen.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (myNewScene != null) {
            Scene scene = new Scene(myNewScene, 700, 700);
            stage.setScene(scene);
            stage.setTitle("Start");
            stage.show();
        }
    }
    @FXML
    protected void onFormSubmit() throws SQLException {
        DBConnector connector = new DBConnector();
        DiskRepository diskRepository = new DiskRepository(connector);
        DiskService diskService = new DiskService(diskRepository);
        Connection connection = connector.getConnection();

        DiskType type;
        String name = diskName.getText();
        String description = diskDescription.getText();
        String[] categories = {};
        Array arrayCategories = connection.createArrayOf("text", categories);
        if (diskTypeDVD.isSelected()) {
            type = DiskType.DVD;
        } else if (diskTypeCDR.isSelected()) {
            type = DiskType.CDR;
        } else {
            type = DiskType.MINI_DISK;
        }

        Disk innerDisk = new Disk(type, description, 1L, name, arrayCategories);
        diskService.add(innerDisk);

        Stage stage = null;
        Parent NewScene = null;
        try {
            stage = (Stage) backButton.getScene().getWindow();
            NewScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("start-screen.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (NewScene != null){
            Scene scene = new Scene(NewScene, 700, 700);
            stage.setScene(scene);
            stage.setTitle("Create");
            stage.show();
        }
    }
}