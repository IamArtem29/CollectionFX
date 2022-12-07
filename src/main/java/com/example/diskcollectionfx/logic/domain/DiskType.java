package com.example.diskcollectionfx.logic.domain;
public enum DiskType {
    DVD("DVD"),
    CDR("CD-R"),
    MINI_DISK("mini-disk");
    DiskType(String value){
        this.value = value;
    }
    private final String value;
    public String getValue() {
        return value;
    }
}