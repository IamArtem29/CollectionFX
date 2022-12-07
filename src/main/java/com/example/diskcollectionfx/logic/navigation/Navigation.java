package com.example.diskcollectionfx.logic.navigation;

import com.example.diskcollectionfx.logic.domain.Disk;
import com.example.diskcollectionfx.logic.domain.DiskType;
import com.example.diskcollectionfx.logic.repository.DBConnector;
import com.example.diskcollectionfx.logic.service.DiskService;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Navigation {
    private final Connection connection;
    private final DiskService diskService;
    Scanner scanner = new Scanner(System.in);

    public Navigation(DBConnector connector, DiskService diskService) {
        this.connection = connector.getConnection();
        this.diskService = diskService;
    }

    public void start() throws SQLException {
        while (true) {
            System.out.println("What do you want?");
            System.out.println("1 - Show collection");
            System.out.println("2 - Add new disk");
            System.out.println("0 - exit");
            int command = scanner.nextInt();
            boolean isExit = false;
            switch (command) {
                case 1 -> {
                    showAllDisks();
                }
                case 2 -> {
                    insertNewDisk();
                }
                case 0 -> {
                    isExit = true;
                }
                default -> {
                    System.out.println("Wrong command");
                }
            }
            if (isExit) {
                break;
            }
        }
    }
    private void insertNewDisk() throws SQLException {
        System.out.println("Name");
        String name = scanner.next();

        System.out.println("Description");
        String description = scanner.next();

        System.out.println("Choose type");
        System.out.println("1 - DVD");
        System.out.println("2 - CD-R");
        System.out.println("3 - mini-disk");
        int typeNumber = scanner.nextInt();
        DiskType type;
        switch (typeNumber) {
            case 1 -> {
                type = DiskType.DVD;
            }
            case 2 -> {
                type = DiskType.CDR;
            }
            case 3 -> {
                type = DiskType.MINI_DISK;
            }
            default -> {
                System.out.println("Wrong Type");
                return;
            }
        }
        System.out.println("Add categories");
        List<String> categoryList = new ArrayList<String>();
        do {
            System.out.println("Add something? (y/n)");
            if(scanner.next().startsWith("y")){
                System.out.println("Enter: ");
                categoryList.add(scanner.next());
            }
            else{
                break;
            }
        } while(true);
        String[] category = categoryList.toArray(new String[0]);
        Array arrayCategories = connection.createArrayOf("text", category);
        Disk disk = new Disk(type, description, 1L, name, arrayCategories);
        diskService.add(disk);
    }
    private void showAllDisks() throws SQLException {
        ArrayList<Disk> allDisks = diskService.getAll();
        allDisks.forEach(System.out::println);
        while (true) {
            System.out.println("What do you want?");
            System.out.println("1 - Show disk");
            System.out.println("2 - Redact disk");
            System.out.println("3 - Delete disk");
            System.out.println("0 - Exit");
            int command = scanner.nextInt();
            boolean isExit = false;
            switch (command) {
                case 1 -> {
                    getInfo(allDisks);
                }
                case 2 -> {
                    updateDisk(allDisks);
                }
                case 3 -> {
                    deleteDisk();
                }
                case 0 -> {
                    isExit = true;
                }
                default -> {
                    System.out.println("Wrong command");
                }
            }
            if (isExit) {
                break;
            }
        }
    }
    public void getInfo(ArrayList<Disk> collection) {
        System.out.println("Choose id");
        long id = scanner.nextLong();
        Disk disk = null;
        for (Disk loopDisk : collection) {
            if (loopDisk.getId().equals(id)) {
                disk = loopDisk;
                break;
            }
        }
        if (disk == null) {
            System.out.println("Wrong command");
            return;
        }
        System.out.println(disk.getInfo());
    }
    private void updateDisk(ArrayList<Disk> collection) throws SQLException {
        System.out.println("Choose id");
        long id = scanner.nextLong();
        Disk disk = null;
        for (Disk loopDisk : collection) {
            if (loopDisk.getId().equals(id)) {
                disk = loopDisk;
                break;
            }
        }
        if (disk == null) {
            System.out.println("Wrong command");
            return;
        }
        System.out.println("Select field to change");
        System.out.println("1 - Name");
        System.out.println("2 - Description");
        System.out.println("3 - Type");
        System.out.println("4 - Categories");
        int updateField = scanner.nextInt();
        switch (updateField) {
            case 1 -> {
                String name = scanner.next();
                disk.setName(name);
            }
            case 2 -> {
                String description = scanner.next();
                disk.setDescription(description);
            }
            case 3 -> {
                System.out.println("Choose type");
                System.out.println("1 - DVD");
                System.out.println("2 - CD-R");
                System.out.println("3 - mini-disk");
                int type = scanner.nextInt();
                switch (type) {
                    case 1 -> {
                        disk.setType(DiskType.DVD);
                    }
                    case 2 -> {
                        disk.setType(DiskType.CDR);
                    }
                    case 3 -> {
                        disk.setType(DiskType.MINI_DISK);
                    }
                    default -> {
                        System.out.println("Wrong Type");
                    }
                }
            }
            case 4 -> {
                System.out.println("Change Categories");
                System.out.println("1 - Add");
                System.out.println("2 - Delete");
                System.out.println("3 - Change category");
                int type = scanner.nextInt();
                switch (type) {
                    case 1 -> {
                        String[] categoryString = (String[])disk.getCategories().getArray();
                        List<String> categoryList = new ArrayList<String>(Arrays.asList(categoryString));
                        do {
                            System.out.println("Add something? (y/n)");
                            if(scanner.next().startsWith("y")){
                                System.out.println("Enter: ");
                                categoryList.add(scanner.next());
                            }
                            else{
                                break;
                            }
                        } while(true);
                        String[] category = categoryList.toArray(new String[0]);
                        Array arrayCategories = connection.createArrayOf("text", category);
                        disk.setCategories(arrayCategories);
                    }
                    case 2 -> {
                        System.out.println("Choose number");
                        String[] categoryString = (String[])disk.getCategories().getArray();
                        List<String> categoryList = new ArrayList<String>(Arrays.asList(categoryString));
                        int counter = 0;
                        for (String categoryListItem : categoryList) {
                            counter++;
                            System.out.println(categoryListItem + " - " + counter);
                        }
                        int idCategory = scanner.nextInt();
                        try {
                            categoryList.remove(idCategory-1);
                            String[] category = categoryList.toArray(new String[0]);
                            Array arrayCategories = connection.createArrayOf("text", category);
                            disk.setCategories(arrayCategories);
                        } catch (Exception e){
                            System.out.println("Wrong number");
                        }
                    }
                    case 3 -> {
                        System.out.println("Choose number");
                        String[] categoryString = (String[])disk.getCategories().getArray();
                        List<String> categoryList = new ArrayList<String>(Arrays.asList(categoryString));
                        int counter = 0;
                        for (String categoryListItem : categoryList) {
                            counter++;
                            System.out.println(categoryListItem + " - " + counter);
                        }
                        int idCategory = scanner.nextInt();
                        try {
                            System.out.println("New value");
                            categoryList.set(idCategory-1, scanner.next());
                            String[] category = categoryList.toArray(new String[0]);
                            Array arrayCategories = connection.createArrayOf("text", category);
                            disk.setCategories(arrayCategories);
                        } catch (Exception e){
                            System.out.println("Wrong number");
                        }
                    }
                    default -> {
                        System.out.println("Wrong command");
                    }
                }
            }
        }
        diskService.update(disk);
    }
    private void deleteDisk() {
        System.out.println("Choose id");
        long id = scanner.nextLong();
        diskService.deleteById(id);
    }
}