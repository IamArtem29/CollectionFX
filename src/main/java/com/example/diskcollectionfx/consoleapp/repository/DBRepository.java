package com.example.diskcollectionfx.consoleapp.repository;
import java.sql.SQLException;
import java.util.ArrayList;
public interface DBRepository<T> {
    void save(T target) throws SQLException, ClassNotFoundException;
    void remove(long id) throws SQLException, ClassNotFoundException;
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    void update(T target) throws SQLException, ClassNotFoundException;
}