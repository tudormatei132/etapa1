package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.User;
import org.poo.transactions.Transaction;

public class PrintTransactions implements Command {

    private User user;
    private ArrayNode output;
    private ObjectMapper mapper;
    private int timestamp;
    public PrintTransactions(User user, ArrayNode output, ObjectMapper mapper, int timestamp) {
        this.user = user;
        this.output = output;
        this.mapper = mapper;
        this.timestamp = timestamp;
    }


    public void execute() {
        if (user == null) {
            return;
        }
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "printTransactions");

        ArrayNode transactions = mapper.createArrayNode();
        for (Transaction transaction : user.getTransactions()) {
            transactions.add(transaction.print(mapper));
        }

        node.put("output", transactions);
        node.put("timestamp", timestamp);
        output.add(node);
    }
}
