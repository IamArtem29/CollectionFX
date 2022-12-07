package com.example.diskcollectionfx;
import com.example.diskcollectionfx.logic.service.DiskService;
import com.example.diskcollectionfx.logic.domain.Disk;
import com.example.diskcollectionfx.logic.domain.DiskType;
import com.example.diskcollectionfx.logic.repository.DBConnector;
import com.example.diskcollectionfx.logic.repository.DiskRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
public class DiskUpdateFormController {
    private Disk disk;
    public DiskUpdateFormController(Disk newDisk, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("disk-update-form.fxml"));
            disk = newDisk;
            loader.setController(this);
            stage.setScene(new Scene(loader.load(), 700, 700));
            stage.setTitle("Collection");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private TextField diskName;
    @FXML
    private TextField diskDescription;
    @FXML
    private TextField diskCategories;
    @FXML
    private ToggleButton diskTypeDVD;
    @FXML
    private ToggleButton diskTypeCDR;
    @FXML
    private ToggleButton diskTypeMINI_DISK;
    @FXML
    private Button backButton;
    @FXML
    private Button updateButton;
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
    public void initialize() {
        diskName.setText(String.valueOf(disk.getName()));
        diskTypeDVD.setSelected(disk.getType() == DiskType.DVD);
        diskTypeCDR.setSelected(disk.getType() == DiskType.CDR);
        diskTypeMINI_DISK.setSelected(disk.getType() == DiskType.MINI_DISK);
        String[] categoryString = new String[0];
        try {
            categoryString = (String[])disk.getCategories().getArray();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String category = String.join(",", categoryString);
        diskCategories.setText(category);
        diskDescription.setText(String.valueOf(disk.getDescription()));
        backButton.setOnAction(actionEvent -> {
            onBackClick();
        });
        diskTypeDVD.setOnAction(actionEvent -> {
            onDVDChoice();
        });
        diskTypeCDR.setOnAction(actionEvent -> {
            onCDRChoice();
        });
        diskTypeMINI_DISK.setOnAction(actionEvent -> {
            onMINI_DISKChoice();
        });
        updateButton.setOnAction(actionEvent -> {
            try {
                onFormSubmit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setOnAction(actionEvent -> {
            onBackClick();
        });
    }
    @FXML
    protected void onBackClick() {
        Stage stage = null;
        Parent NewScene = null;
        try {
            stage = (Stage) backButton.getScene().getWindow();
            NewScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("all-items-screen.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (NewScene != null) {
            Scene scene = new Scene(NewScene, 700, 700);
            stage.setScene(scene);
            stage.setTitle("Collection");
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
        String[] categories = diskCategories.getText().split(",");
        Array arrayCategories = connection.createArrayOf("text", categories);
        if (diskTypeDVD.isSelected()) {
            type = DiskType.DVD;
        } else if (diskTypeCDR.isSelected()) {
            type = DiskType.CDR;
        } else {
            type = DiskType.MINI_DISK;
        }

        Disk innerDisk = new Disk(type, description, disk.getId(), name, arrayCategories);
        diskService.update(innerDisk);

        Stage stage = null;
        Parent NewScene = null;
        try{
            stage = (Stage) backButton.getScene().getWindow();
            NewScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("all-items-screen.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (NewScene != null){
            Scene scene = new Scene(NewScene, 700, 700);
//            stage.getScene(scene);
            stage.getScene();
            stage.setTitle("Update");
            stage.show();
        }
    }
}