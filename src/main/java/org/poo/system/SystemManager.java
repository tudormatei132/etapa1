package org.poo.system;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.*;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class SystemManager {

    private ArrayList<User> users;
    private HashMap<String, Account> map;
    private HashMap<String, User> userMap;
    public SystemManager() {
        users = new ArrayList<>();
        map = new HashMap<>();
        userMap = new HashMap<>();
    }

    public void run(final ObjectInput input, final ObjectMapper mapper, final ArrayNode output) {
        for (UserInput user : input.getUsers()) {
            User toBeAdded = new User(user);
            users.add(toBeAdded);
            userMap.put(toBeAdded.getEmail().toString(), toBeAdded);
        }


        for (CommandInput command : input.getCommands()) {
            ObjectNode actionOutput = mapper.createObjectNode();
            actionOutput.put("command", command.getCommand());
            actionOutput.put("timestamp", command.getTimestamp());
            switch (command.getCommand()) {
                case "printUsers": {
                    ArrayNode userArrayNode = mapper.createArrayNode();
                    for (User user : users) {
                        userArrayNode.add(user.printUser(mapper));
                    }
                    actionOutput.set("output", userArrayNode);
                    output.add(actionOutput);
                }
                break;
                case "addAccount": {
                    User temp = userMap.get(command.getEmail());
                    Account account;
                    if (command.getAccountType().equals("classic")) {
                        account = new ClassicAccount(temp,
                                new StringBuilder(Utils.generateIBAN()),
                                new StringBuilder(command.getCurrency()),
                                command.getInterestRate());
                    }
                    else {
                        account = new SavingsAccount(temp,
                                new StringBuilder(Utils.generateIBAN()),
                                new StringBuilder(command.getCurrency()),
                                command.getInterestRate());
                    }
                    temp.addAccount(account);
                    System.out.println(account.getIBAN().toString());
                    map.put(account.getIBAN().toString(), account);
                }
                break;
                case "createCard": {
                    Account account = map.get(command.getAccount());
                    if (account == null)
                        break;
                    account.AddCard(new Card(new StringBuilder(Utils.generateCardNumber())));
                }
                break;
                case "addFunds": {
                    Account account = map.get(command.getAccount());
                    if (account == null)
                        break;
                    account.addFunds(command.getAmount());
                }
                break;
                case "removeAccount": {
                    Account account = map.get(command.getAccount());
                    account.getUser().removeAccount(account);
                    map.remove(account.getIBAN().toString());
                }
                break;
                default:
            }
        }
    }

}
