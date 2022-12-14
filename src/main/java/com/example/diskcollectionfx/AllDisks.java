package com.example.diskcollectionfx;
import com.example.diskcollectionfx.consoleapp.service.DiskService;
import com.example.diskcollectionfx.consoleapp.domain.Disk;
import com.example.diskcollectionfx.consoleapp.repository.DBConnector;
import com.example.diskcollectionfx.consoleapp.repository.DiskRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
public class AllDisks {
    @FXML
    private ListView<Disk> diskList;
    @FXML
    private Button backButton;
    public void initialize() {
        DBConnector connector = new DBConnector();
        DiskRepository diskRepository = new DiskRepository(connector);
        DiskService diskService = new DiskService(diskRepository);
        ArrayList<Disk> allDisks = diskService.getAll();
        ObservableList<Disk> observableDisks = FXCollections.observableArrayList(allDisks);
        diskList.setCellFactory(diskListView -> new DiskCell());
        diskList.setItems(observableDisks);
    }
    @FXML
    protected void onBackClick() {
        Stage stage = null;
        Parent NewScene = null;
        try {
            stage = (Stage) backButton.getScene().getWindow();
            NewScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("start-screen.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (NewScene != null) {
            Scene scene = new Scene(NewScene, 700, 700);
            stage.setScene(scene);
            stage.setTitle("Start");
            stage.show();
        }
    }
}