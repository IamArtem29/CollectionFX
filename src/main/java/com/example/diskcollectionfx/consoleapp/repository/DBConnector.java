package com.example.diskcollectionfx.consoleapp.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;
public class DBConnector {
    private final Connection connection;
    public DBConnector() {
        Dotenv dotenv;
        dotenv = Dotenv.configure().load();
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    dotenv.get("USER"),
                    dotenv.get("PASSWORD"));
            System.out.println("DB: " + connection.getMetaData().getDatabaseProductName());
        } catch (SQLException e){
            throw new RuntimeException("Error in connection = ", e);
        }
    }
    public Connection getConnection() {
        return connection;
    }
}