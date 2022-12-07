package com.example.diskcollectionfx;
import com.example.diskcollectionfx.consoleapp.service.DiskService;
import com.example.diskcollectionfx.consoleapp.domain.Disk;
import com.example.diskcollectionfx.consoleapp.domain.DiskType;
import com.example.diskcollectionfx.consoleapp.repository.DBConnector;
import com.example.diskcollectionfx.consoleapp.repository.DiskRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
public class DiskCell extends ListCell<Disk> {
    @FXML
    private VBox gridPane;
    @FXML
    private Label name;
    @FXML
    private Label type;
    @FXML
    private Label id;
    @FXML
    private Label categories;
    @FXML
    private Label description;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button detailedLookButton;
    @Override
    protected void updateItem(Disk disk, boolean empty) {
        super.updateItem(disk, empty);
        System.out.println(disk);
        if (empty || disk == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader mLLoader = new FXMLLoader(getClass().getResource("disk-cell.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String diskType;
            if (disk.getType() == DiskType.DVD) {
                diskType = "DVD";
            } else if (disk.getType() == DiskType.CDR) {
                diskType = "CD-R";
            } else {
                diskType = "mini-disk";
            }
            deleteButton.setOnAction(actionEvent -> {
                DBConnector connector = new DBConnector();
                DiskRepository diskRepository = new DiskRepository(connector);
                DiskService diskService = new DiskService(diskRepository);
                ListView<Disk> listView = this.getListView();
                ObservableList<Disk> observableDiskList = listView.getItems();
                observableDiskList.remove(this.getIndex());
                listView.setItems(observableDiskList);
                diskService.deleteById(disk.getId());
            });
            updateButton.setOnAction(actionEvent -> {
                new DiskUpdate(disk, (Stage) updateButton.getScene().getWindow());
            });

            String[] categoryString = new String[0];
            try {
                categoryString = (String[])disk.getCategories().getArray();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String categoriesString = String.join(",", categoryString);
            categories.setText("Categories - " + categoriesString);

            detailedLookButton.setOnAction(actionEvent -> {
                new DetailedDisk(disk, (Stage) detailedLookButton.getScene().getWindow());
            });
            type.setText("Type - " + diskType);
            id.setText("Number of disk - " + disk.getId());
            name.setText("Name - " + disk.getName());
            setGraphic(gridPane);
        }
    }
}