package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.fileio.CommandInput;

import java.util.HashMap;

public class DeleteAccount implements Command {

    private CommandInput command;
    private HashMap<String, Account> map;
    private ObjectMapper mapper;
    private ArrayNode output;
    private int timestamp;
    public DeleteAccount(final CommandInput command, final HashMap<String, Account> map,
                         final ObjectMapper mapper, final ArrayNode output) {
        this.command = command;
        this.map = map;
        this.mapper = mapper;
        this.output = output;
        this.timestamp = command.getTimestamp();
    }


    @Override
    public void execute() {
        Account temp = map.get(command.getAccount());
        if (temp == null) {
            return;
        }
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "deleteAccount");
        node.put("timestamp", timestamp);
        if (temp.getBalance() == 0) {
            temp.getUser().removeAccount(temp);
            map.remove(command.getAccount());
            ObjectNode succes = mapper.createObjectNode();
            succes.put("success", "Account deleted");
            succes.put("timestamp", timestamp);
            node.put("output", succes);
        } else {
            ObjectNode error = mapper.createObjectNode();
            error.put("error", "Account couldn't be deleted - see org.poo.transactions for details");
            error.put("timestamp", timestamp);
            node.put("output", error);
        }
        output.add(node);
    }
}
