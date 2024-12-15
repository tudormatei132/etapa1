package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Card;
import org.poo.transactions.Transaction;

public class CheckCardStatus implements Command {

    private Card card;
    private int timestamp;
    private ArrayNode output;
    private ObjectMapper mapper;
    public CheckCardStatus(final Card card, final int timestamp, final ArrayNode output,
                           final ObjectMapper mapper) {
        this.card = card;
        this.timestamp = timestamp;
        this.output = output;
        this.mapper = mapper;
    }

    public void execute() {
        ObjectNode checkCard = mapper.createObjectNode();
        if (card == null) {
            checkCard.put("command", "checkCardStatus");
            ObjectNode error = mapper.createObjectNode();
            error.put("timestamp", timestamp);
            error.put("description", "Card not found");
            checkCard.put("output", error);
            checkCard.put("timestamp", timestamp);
            output.add(checkCard);
            return;
        }
        if (card.getStatus().toString().equals("frozen")) {
            return;
        }
        card.update();
        String new_status = card.getStatus().toString();


        Transaction checkStatus;
        if (new_status.equals("frozen")) {
            checkStatus = new Transaction(timestamp, "You have reached the minimum amount of " +
                    "funds, the card will be frozen");
            card.getAccount().getTransactions().add(checkStatus);
            card.getAccount().getUser().getTransactions().add(checkStatus);
        }


    }
}
