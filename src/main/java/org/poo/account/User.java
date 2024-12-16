package org.poo.account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.UserInput;
import org.poo.transactions.Transaction;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@Getter @Setter
public class User {
    private StringBuilder firstName;
    private StringBuilder surname;
    private StringBuilder email;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private HashMap<String, Account> aliases;


    public User(final UserInput input) {
        firstName = new StringBuilder(input.getFirstName());
        surname = new StringBuilder(input.getLastName());
        email = new StringBuilder(input.getEmail());
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        aliases = new HashMap<>();
    }

    /**
     * adds an account for the user
     * @param account the account to be added in the list
     */
    public void addAccount(final Account account) {
        accounts.add(account);
    }

    public ObjectNode printUser(ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("firstName", firstName.toString());
        node.put("lastName", surname.toString());
        node.put("email", email.toString());
        ArrayNode accountsNode = mapper.createArrayNode();
        for (Account ac : accounts) {
            accountsNode.add(ac.printAccount(mapper));
        }
        node.set("accounts", accountsNode);
        return node;
    }

    public void removeAccount(final Account account) {
        accounts.remove(account);
    }
}
