package org.poo.system;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.account.*;
import org.poo.command.CommandHandler;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
@Getter
public class SystemManager {

    private ArrayList<User> users;
    private HashMap<String, Account> map;
    private HashMap<String, User> userMap;
    private HashMap<String, Card> cardMap;
    private Converter converter;



    public SystemManager() {
        users = new ArrayList<>();
        map = new HashMap<>();
        userMap = new HashMap<>();
        cardMap = new HashMap<>();
        converter = new Converter();
    }



    public void run(final ObjectInput input, final ObjectMapper mapper, final ArrayNode output) {
        for (UserInput user : input.getUsers()) {
            User toBeAdded = new User(user);
            users.add(toBeAdded);
            userMap.put(toBeAdded.getEmail().toString(), toBeAdded);
        }

        for (ExchangeInput exchange : input.getExchangeRates()) {
            converter.addNewRate(exchange.getFrom(), exchange.getTo(), exchange.getRate());
        }

        CommandHandler handler = new CommandHandler(this, mapper, output);

        for (CommandInput command : input.getCommands()) {
            handler.execute(command);
        }
    }

}
