import dao.ConnectionFactory;
import dao.DbConnectionFactory;

public class Main {

    public static void main(String args[]) throws Exception {
        ConnectionFactory connectionFactory = new DbConnectionFactory(
                "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=PaymentSystem;",
                "java",
                "java123"
        );

        /*ConnectionFactory connectionFactory = new ConnectionFactoryImpl(
                "jdbc:mysql://127.0.0.1:3306/payment_system?serverTimezone=UTC",
                "root",
                "MySQL2307"
        );*/

        new UI(connectionFactory).run();

    }
}
