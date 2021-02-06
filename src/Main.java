import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Main {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://127.0.0.1:3306/payment_system?serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "MySQL2307";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String args[]) {
        // Create a variable for the connection string.dbHost\sqlexpress
        String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=PaymentSystem;user=java;password=java123";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            //createTable(stmt);
            String SQL = "SELECT * FROM dbo.Clients;";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.println(rs.getString("id")
                        + " : " + rs.getString("firstName")
                        + " " + rs.getString("lastName")
                        + ", " + rs.getString("dateOfBirth"));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "select * from clients";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString("id")
                        + " : " + rs.getString("firstName")
                        + " " + rs.getString("lastName")
                        + ", " + rs.getDate("dateOfBirth"));
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }
}
