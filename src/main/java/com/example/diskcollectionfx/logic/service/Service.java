package com.example.diskcollectionfx.logic.service;
import java.sql.SQLException;
import java.util.ArrayList;
public interface Service<T> {
    ArrayList<T> getAll();
    void update(T target) throws SQLException;
    void add(T target) throws SQLException;
    void deleteById(Long id);
}