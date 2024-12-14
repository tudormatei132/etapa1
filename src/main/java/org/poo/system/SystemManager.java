package org.poo.system;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.*;
import org.poo.command.CommandHandler;
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
    private HashMap<String, Card> cardMap;

    public SystemManager() {
        users = new ArrayList<>();
        map = new HashMap<>();
        userMap = new HashMap<>();
        cardMap = new HashMap<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public HashMap<String, Account> getMap() {
        return map;
    }

    public HashMap<String, User> getUserMap() {
        return userMap;
    }

    public HashMap<String, Card> getCardMap() {
        return cardMap;
    }

    public void run(final ObjectInput input, final ObjectMapper mapper, final ArrayNode output) {
        for (UserInput user : input.getUsers()) {
            User toBeAdded = new User(user);
            users.add(toBeAdded);
            userMap.put(toBeAdded.getEmail().toString(), toBeAdded);
        }


        CommandHandler handler = new CommandHandler(this, mapper, output);

        for (CommandInput command : input.getCommands()) {
            handler.execute(command);
        }
    }

}
