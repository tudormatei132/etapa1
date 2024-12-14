package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.account.Account;
import org.poo.account.Card;
import org.poo.account.User;
import org.poo.fileio.CommandInput;
import org.poo.system.SystemManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandHandler {

    private final SystemManager system;
    private final ArrayNode output;
    private final ObjectMapper mapper;
    public CommandHandler(final SystemManager system, final ObjectMapper mapper, ArrayNode output) {
        this.system = system;
        this.mapper = mapper;
        this.output = output;
    }


    public void execute(CommandInput input) {
        Command command;
        try {
            command = getCommand(input);
            if (command == null) {
                throw new IllegalArgumentException();
            } else {
                command.execute();
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid command");
        }

    }

    private Command getCommand(CommandInput command) throws IllegalArgumentException {
        switch (command.getCommand()) {
            case "addAccount": {
                return new AddAccount(command, system.getUserMap(), system.getMap());
            }

            case "deleteAccount": {
                return new DeleteAccount(command, system.getMap(), mapper, output);
            }


            case "printUsers": {
                return new PrintUsers(system.getUsers(), mapper, command.getTimestamp(), output);
            }

            case "createCard": {
                return new CreateCard(system.getMap().get(command.getAccount()),
                        system.getCardMap());
            }

            case "addFunds": {
                return new AddFunds(system.getMap().get(command.getAccount()), command.getAmount());
            }

            case "createOneTimeCard": {
                return new CreateOneTimeCard(system.getMap().get(command.getAccount()),
                        system.getCardMap());
            }

            case "deleteCard": {
                return new RemoveCard(command.getCardNumber(), system.getCardMap());
            }

            case "PayOnline": {

            }

        }
        return null;
    }

}
