package tests;

import dao.ConnectionFactory;
import dao.DbConnectionFactory;
import entities.Client;
import org.testng.annotations.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSettings {
    public final static String DB_NAME = "PaymentSystemTest";

    public static ConnectionFactory connectionFactory = new DbConnectionFactory(
            "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=" + DB_NAME,
            "java",
            "java123"
    );

    private ConnectionFactory createDBconnectionFactory = new DbConnectionFactory(
            "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=master",
            "java",
            "java123"
    );

    public static Client[] clients = {
            new Client("Manning", "Victoria", Date.valueOf("1980-02-14")),
            new Client("Rampling", "Cameron", Date.valueOf("1959-02-14")),
            new Client("Davidson", "Audrey", Date.valueOf("1959-02-14")),
            new Client("Lawrence", "Benjamin", Date.valueOf("2000-02-14")),
            new Client("James", "Fiona", Date.valueOf("1999-02-14")),
    };

    @BeforeSuite
    public void beforeSuite() throws IOException, SQLException {
        System.out.println("CREATE DATABASE");

        String userDirectory = new File("").getAbsolutePath();
        Path createTablesSqlPath = Paths.get(userDirectory,"sql\\mssql\\create.tables.sql");

        String sql = new String ( Files.readAllBytes(createTablesSqlPath));

        try (Connection con = createDBconnectionFactory.getConnection()) {
            Statement stm = con.createStatement();
            stm.executeUpdate("IF EXISTS(select * from sys.databases where name='"+ TestSettings.DB_NAME + "')\n" +
                    "DROP DATABASE " + TestSettings.DB_NAME + ";");
            stm.executeUpdate("DROP DATABASE IF EXISTS " + TestSettings.DB_NAME + ";");
            stm.executeUpdate("CREATE DATABASE " + TestSettings.DB_NAME +";");
        }

        try (Connection con = TestSettings.connectionFactory.getConnection()) {
            Statement stm = con.createStatement();
            stm.executeUpdate(sql);
        }
    }

    @AfterSuite(enabled = false)
    public void afterSuite() {
        System.out.println("DROP DATABASE");
        try {
            String sql = "ALTER DATABASE " + TestSettings.DB_NAME + " SET OFFLINE WITH ROLLBACK IMMEDIATE;\n" +
                    "DROP DATABASE " + TestSettings.DB_NAME + ";";
            try (Connection con = TestSettings.connectionFactory.getConnection()) {
                Statement stm = con.createStatement();
                stm.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
