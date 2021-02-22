import services.ClientService;
import dao.ClientDao;
import dao.ClientDaoImpl;
import dao.ConnectionFactory;
import dao.DbConnectionFactory;
import entities.Client;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String args[]) {
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

        ClientDao<Integer> clientDao = new ClientDaoImpl(connectionFactory);
        ClientService<Integer> clientService = new ClientService<Integer>(clientDao);
        List<Client> clients = null;
        try {
            Client newClient = new Client("Lara", "Croft", Date.valueOf("1959-02-14"));
            clientService.create(newClient);
            clients = clientDao.getAll();
            for (Client c:
                    clients) {
                System.out.println(c);
            }

            Client client = clientDao.findById(newClient.getId());
            System.out.println(client);

            List<Client> clientsByName = clientService.findByName("ro");
            for (Client c:
                    clientsByName) {
                System.out.println(c);
            }

            client.setLastName("foo");
            clientService.update(client);
            System.out.println(client);

            clientService.delete(client);

            clients = clientDao.getAll();
            for (Client c:
                    clients) {
                System.out.println(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
