package com.example.diskcollectionfx.consoleapp.domain;
import java.sql.Array;
public class Disk {
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    private Array categories;
    public void setCategories(Array categories) {
        this.categories = categories;
    }
    public Array getCategories() {
        return categories;
    }
    private final Long id;
    public Long getId() {
        return this.id;
    }
    private String description;
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
    private DiskType type;
    public void setType(DiskType type) {
        this.type = type;
    }
    public DiskType getType() {
        return this.type;
    }
    public Disk(DiskType type, String description, Long id, String name, Array categories) {
        this.type = type;
        this.description = description;
        this.id = id;
        this.name = name;
        this.categories = categories;
    }
    public String toString(){
        return "Disk number - " + this.id + "\n" + "Name - " + this.name + "\n" + "Type - " + this.type.getValue() + "\n" + "Categories - " + this.categories + "\n"+ this.description + "\n";
    }
    public String getInfo() {
        String type = null;
        if(this.type == DiskType.DVD) {
            type = "DVD";
        }
        if(this.type == DiskType.CDR){
            type = "CD-R";
        }
        if(this.type == DiskType.MINI_DISK){
            type = "mini-disk";
        }
        return "Disk number - " + this.id + "\n" + "Name - " + this.name + "\n" + "Type - " + type + "\n" + "Categories - " + this.categories + "\n" + this.description + "\n";
    }
}