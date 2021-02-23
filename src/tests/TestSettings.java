package tests;

import dao.ConnectionFactory;
import dao.DbConnectionFactory;
import org.testng.annotations.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
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

    @BeforeSuite
    public void beforeSuite() throws FileNotFoundException, SQLException {
        System.out.println("CREATE DATABASE");

        try {
            String userDirectory = new File("").getAbsolutePath();
            Path createTablesSqlPath = Paths.get(userDirectory,"sql\\mssql\\create.tables.sql");

            String sql = new String ( Files.readAllBytes(createTablesSqlPath));

            try (Connection con = createDBconnectionFactory.getConnection()) {
                Statement stm = con.createStatement();
                String query = "CREATE DATABASE " + TestSettings.DB_NAME;
                stm.executeUpdate(query);
            }

            try (Connection con = TestSettings.connectionFactory.getConnection()) {
                Statement stm = con.createStatement();
                stm.executeUpdate(sql);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("DROP DATABASE");
        try {
            String sql = "DROP DATABASE " + TestSettings.DB_NAME;
            try (Connection con = TestSettings.connectionFactory.getConnection()) {
                Statement stm = con.createStatement();
                stm.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
