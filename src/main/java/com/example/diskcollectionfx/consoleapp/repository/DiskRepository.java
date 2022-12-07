package com.example.diskcollectionfx.consoleapp.repository;
import com.example.diskcollectionfx.consoleapp.domain.Disk;
import com.example.diskcollectionfx.consoleapp.domain.DiskType;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Array;

public class DiskRepository implements DBRepository<Disk> {
    private final Connection connection;
    public DiskRepository(DBConnector connector){
        this.connection = connector.getConnection();
    }
    @Override
    public void save(Disk disk) throws SQLException {
        String values = String.format("values ('%s', '%s' ,'%s', '%s')", disk.getType(), disk.getDescription(), disk.getName(), disk.getCategories());
        String query = "insert into collection (type, description, name, categories )\n" + values;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException("Error = " + e.getMessage());
        }
    }
    @Override
    public void remove(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from collection where id = ?;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Error = " + e.getMessage());
        }
    }
    @Override
    public ArrayList<Disk> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from collection;");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Disk> diskList = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                DiskType type = DiskType.valueOf(rs.getString("type"));
                String description = rs.getString("description");
                String name = rs.getString("name");
                Array arrayCategories = rs.getArray("categories");
                Disk disk = new Disk(type, description, id, name, arrayCategories);
                diskList.add(disk);
            }
            return diskList;
        } catch (SQLException e){
            throw new RuntimeException("Error = " + e.getMessage());
        }
    }
    @Override
    public void update(Disk disk) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update collection set type = ?, description = ?, name = ?, categories = ? where id = ?;");
            preparedStatement.setString(1, disk.getType().toString());
            preparedStatement.setString(2, disk.getDescription());
            preparedStatement.setString(3, disk.getName());
            preparedStatement.setArray(4, disk.getCategories());
            preparedStatement.setLong(5, disk.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Error = " + e.getMessage());
        }
    }
}