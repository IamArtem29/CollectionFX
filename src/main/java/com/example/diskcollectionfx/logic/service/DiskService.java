package com.example.diskcollectionfx.logic.service;
import com.example.diskcollectionfx.logic.domain.Disk;
import com.example.diskcollectionfx.logic.repository.DiskRepository;
import java.sql.SQLException;
import java.util.ArrayList;
public class DiskService implements Service<Disk>{
    private final DiskRepository repository;
    public DiskService(DiskRepository repository) {
        this.repository = repository;
    }
    @Override
    public void update(Disk disk) throws SQLException {
        repository.update(disk);
    }
    @Override
    public void add(Disk disk) throws SQLException {
        repository.save(disk);
    }
    @Override
    public void deleteById(Long id) {
        repository.remove(id);
    }
    @Override
    public ArrayList<Disk> getAll() {
        return repository.getAll();
    }
}