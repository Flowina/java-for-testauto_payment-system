import dao.*;
import entities.*;
import services.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UI {
    private final ClientService<Integer> clientService;
    private final AccountService<Integer> accountService;
    private final CardService<Integer> cardService;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public UI(ConnectionFactory connectionFactory) {
        ClientDao<Integer> clientDao = new ClientDaoImpl(connectionFactory);
        AccountDao<Integer> accountDao = new AccountDaoImpl(connectionFactory);
        CardDao<Integer> cardDao = new CardDaoImpl(connectionFactory);
        clientService = new ClientService<>(clientDao);
        accountService = new AccountService<>(accountDao);
        cardService = new CardService<>(cardDao);
    }

    public void run() throws Exception {
        String action = "";
        try {
            do {
                //System.out.println("A. Accounts");
                //System.out.println("B. Cards");
                System.out.println("C. Clients");
                System.out.println("Q. Exit");
                System.out.println("===========================================");
                System.out.println("Enter an action");
                System.out.println("===========================================");
                action = br.readLine();
                System.out.println("\n");

                switch(action.toUpperCase())
                {
                    case "A":
                        accountActions();
                        break;

                    case "B":
                        cardActions();
                        break;

                    case "C":
                        clientActions();
                        break;

                    case "Q":
                        System.out.println("BYE!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid Action! Please enter again");
                        break;
                }
            } while (!action.toUpperCase().equals("Q"));
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clientActions() throws Exception {
        String action = "";

        do {
            System.out.println("A. View Clients");
            System.out.println("B. Add Client");
            //System.out.println("C. Update Client");
            System.out.println("D. Delete Client");
            System.out.println("E. Edit Client");
            System.out.println("S. Search Client");
            System.out.println("Q. Exit");
            System.out.println("===========================================");
            System.out.println("Enter an action");
            System.out.println("===========================================");
            action = br.readLine();
            System.out.println("\n");

            switch(action.toUpperCase())
            {
                case "A":
                    viewClients();
                    break;

                case "B":
                    addClient();
                    break;

                case "Q":
                    return;

                case "D":
                    deleteClient();
                    break;

                case "E":
                    editClientActions();
                    break;

                case "S":
                    searchClients();
                    break;

                default:
                    System.out.println("Invalid Action! Please enter again");
                    break;
            }
        } while (!action.toUpperCase().equals("Q"));
    }

    private void editClientActions() throws Exception {
        String action = "";
        Client client = null;

        do {
            if (client != null) {
                System.out.println("Selected client: " + client);
                System.out.println("\n");
                System.out.println("A. Edit client's accounts");
            }
            System.out.println("S. Select");
            System.out.println("Q. Exit");
            System.out.println("===========================================");
            System.out.println("Enter an action");
            System.out.println("===========================================");
            action = br.readLine();
            System.out.println("\n");

            switch(action.toUpperCase())
            {
                case "A":
                    if (client == null) {
                        System.out.println("Select client");
                    } else {
                        editAccountsActions(client);
                    }

                    break;
                case "S":
                    client = selectClient();
                    break;

                case "Q":
                    client = null;
                    return;


                default:
                    System.out.println("Invalid Action! Please enter again");
                    break;
            }
        } while (!action.toUpperCase().equals("Q"));

    }

    private void accountActions() throws Exception {
        String action = "";

        System.out.println("Not implemented yet");
    }

    private void cardActions() throws Exception {
        String action = "";

        System.out.println("Not implemented yet");
    }

    private void editAccountsActions(Client client) throws Exception {
        String action = "";
        Account account = null;

        do {
            if (account != null) {
                System.out.println("Selected account: " + account);
                System.out.println("\n");
            }

            System.out.println("A. Add");
            System.out.println("S. Select");
            if (account != null) {
                System.out.println("C. Cards");
                System.out.println("+. Increase");
                System.out.println("-. Decrease");
            }
            System.out.println("V. View client accounts");
            System.out.println("Q. Exit");
            System.out.println("===========================================");
            System.out.println("Enter an action");
            System.out.println("===========================================");
            action = br.readLine();
            System.out.println("\n");

            switch(action.toUpperCase())
            {
                case "A":
                    account = addAccount(client);
                    break;
                case "S":
                    account = selectClientAccount(client);
                    break;
                case "C":
                    if (isAccountSelected(account)) {
                        editAccountCardsActions(account);
                    }
                    break;
                case "+":
                    if (isAccountSelected(account)) {
                        increaseAccountAmount(account);
                    }
                    break;
                case "-":
                    if (isAccountSelected(account)) {
                        decreaseAccountAmount(account);
                    }
                    break;
                case "V":
                    viewClientAccounts(client);
                    break;

                case "Q":
                    account = null;
                    return;

                default:
                    System.out.println("Invalid Action! Please enter again");
                    break;
            }
        } while (!action.toUpperCase().equals("Q"));

    }

    private void editAccountCardsActions(Account<Integer> account) throws Exception {
        String action = "";
        Card card = null;

        do {
            if (card != null) {
                System.out.println("Selected card: " + card);
                System.out.println("\n");
            }
            System.out.println("A. Add");
            System.out.println("S. Select");
            if (card != null) {
                System.out.println("D. Delete");
            }
            System.out.println("V. View cards");
            System.out.println("Q. Exit");
            System.out.println("===========================================");
            System.out.println("Enter an action");
            System.out.println("===========================================");
            action = br.readLine();
            System.out.println("\n");

            switch(action.toUpperCase())
            {
                case "A":
                    card = addCard(account);
                    break;
                case "S":
                    card = selectCard();
                    break;
                case "D":
                    if (isCardSelected(card)) {
                        deleteCard(card);
                    }
                    break;

                case "V":
                    viewCards(account);
                    break;

                case "Q":
                    card = null;
                    return;

                default:
                    System.out.println("Invalid Action! Please enter again");
                    break;
            }
        } while (!action.toUpperCase().equals("Q"));
    }

    private void viewCards(Account account) {
        showLoader();
        List<Card<Integer>> cards = cardService.findByAccount(account);
        hideLoader();
        if (cards.size() == 0) {
            System.out.println("There are no cards");
            System.out.println("\n");
            return;
        }
        for (Card card:
            cards) {
            System.out.println(card);
        }
        System.out.println("\n");
    }

    private void deleteCard(Card card) throws Exception {
        showLoader();
        cardService.delete(card);
        hideLoader();
        System.out.println("The card deleted");
        System.out.println("\n");
    }

    private Card addCard(Account<Integer> account) throws IOException {
        System.out.println("------------------------------------------------");
        System.out.println("Enter card number:");
        System.out.println("------------------------------------------------");
        Integer number = Integer.valueOf(br.readLine());
        System.out.println("------------------------------------------------");
        System.out.println("Enter owner name");
        System.out.println("------------------------------------------------");
        String name = br.readLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter cvv");
        System.out.println("------------------------------------------------");
        Short cvv = Short.parseShort(br.readLine());
        System.out.println("------------------------------------------------");
        System.out.println("Enter month expired");
        System.out.println("------------------------------------------------");
        Short month = Short.parseShort(br.readLine());
        System.out.println("------------------------------------------------");
        System.out.println("Enter year expired");
        System.out.println("------------------------------------------------");
        Short year = Short.parseShort(br.readLine());

        Card card = new Card(account.getId(), number, name, cvv, year, month );

        showLoader();
        cardService.create(card);
        hideLoader();

        System.out.println("Card created");
        System.out.println("\n");
        return  card;
    }

    private Card selectCard() throws IOException {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Card id:");
        System.out.println("------------------------------------------------");
        Integer id = Integer.valueOf(br.readLine());

        showLoader();
        Card card = cardService.findById(id);
        hideLoader();

        if (card == null) {
            System.out.println("The card not found (id = "+ id + ")");
        }
        return card;
    }

    private void viewClients()
    {
        System.out.println("-----------------------------------------------");
        showLoader();
        List<Client> clients = clientService.getAll();
        hideLoader();
        for(Client product: clients)
        {
            System.out.println(product);
        }
        System.out.println("-----------------------------------------------");
        System.out.println("\n");
    }

    private void searchClients() throws Exception
    {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Client's name:");
        System.out.println("------------------------------------------------");
        String name = br.readLine();

        showLoader();
        List<Client> clients = clientService.findByName(name);
        hideLoader();

        for(Client client: clients)
        {
            System.out.println(client);
        }
        System.out.println("\n");
    }

    private Client selectClient() throws Exception
    {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Client's id:");
        System.out.println("------------------------------------------------");
        Integer id = Integer.valueOf(br.readLine());

        showLoader();
        Client client = clientService.findById(id);
        hideLoader();

        if (client == null) {
            System.out.println("The client not found (id = "+ id + ")");
        }
        return client;
    }

    private void deleteClient() throws Exception
    {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Client ID:");
        System.out.println("------------------------------------------------");
        String productId = br.readLine();

        try {
            clientService.delete(Integer.valueOf(productId));
            System.out.println("Client deleted successfully");
        }
        catch (Exception e) {
            System.out.println("ERROR while deleting client");
            e.printStackTrace();
        }
        System.out.println("\n");

    }

    private void addClient() throws Exception
    {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Last name:");
        System.out.println("------------------------------------------------");
        String lastName = br.readLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter First Name:");
        System.out.println("------------------------------------------------");
        String firstName = br.readLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Date of birth:");
        System.out.println("------------------------------------------------");

        Date dateOfBirth = dateFormat.parse(br.readLine());

        Client client = new Client(lastName, firstName, dateOfBirth);

        showLoader();
        clientService.create(client);
        hideLoader();

        System.out.println("\n");
    }

    private Account addAccount(Client client) throws Exception
    {
        System.out.println("------------------------------------------------");
        System.out.println("Enter account number:");
        System.out.println("------------------------------------------------");
        Integer number = Integer.valueOf(br.readLine());
        System.out.println("------------------------------------------------");
        System.out.println("Enter type (1 - visa, 2 - mc):");
        System.out.println("------------------------------------------------");
        short type = Short.parseShort(br.readLine());

        Account acc = new Account(client.getId(), number, type, 0, new Date(), null );

        showLoader();
        accountService.create(acc);
        hideLoader();

        System.out.println("Account created");
        System.out.println("\n");
        return  acc;
    }

    private Account selectClientAccount(Client client) throws Exception {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Account id:");
        System.out.println("------------------------------------------------");
        Integer id = Integer.valueOf(br.readLine());

        showLoader();
        Account account = accountService.findById(id);
        hideLoader();

        if (account == null) {
            System.out.println("The account not found (id = "+ id + ")");
        }
        return account;
    }

    private void viewClientAccounts(Client client) {
        List<Account> accounts = accountService.findByClient(client);
        if (accounts.size() == 0) {
            System.out.println("There are no accounts");
        }
        for (Account acc:
                accounts) {
            System.out.println(acc);
        }
    }

    private void increaseAccountAmount(Account<Integer> acc) throws Exception {
        System.out.println("------------------------------------------------");
        System.out.println("Enter value:");
        System.out.println("------------------------------------------------");
        double value = Double.parseDouble(br.readLine());
        showLoader();
        accountService.increaseBallance(acc.getId(), value);
        hideLoader();
    }

    private void decreaseAccountAmount(Account<Integer> acc) throws Exception {
        System.out.println("------------------------------------------------");
        System.out.println("Enter value:");
        System.out.println("------------------------------------------------");
        double value = Double.parseDouble(br.readLine());
        showLoader();
        accountService.decreaseBallance(acc.getId(), value);
        hideLoader();
    }

    private void showLoader() {
        System.out.print("Loading...");
    }

    private void hideLoader() {
        System.out.print("\b\b\b\b\b\b\b\b\b\b");
    }

    private boolean isAccountSelected(Account account) {
        boolean result = account != null;
        if (!result) {
            System.out.println("Select account");
            System.out.println("\n");
        }
        return result;
    }

    private boolean isCardSelected(Card card) {
        boolean result = card != null;
        if (!result) {
            System.out.println("Select card");
            System.out.println("\n");
        }
        return result;
    }
}
