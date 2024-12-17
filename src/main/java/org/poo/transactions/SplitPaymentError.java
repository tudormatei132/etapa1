package org.poo.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.command.SplitPayment;

import java.util.ArrayList;
import java.util.List;

public class SplitPaymentError extends Transaction {

    private double amount;
    private String currency, brokeOne;
    private List<String> involvedAccounts;

    public SplitPaymentError(int timestamp, String description, double amount,
                             String currency, List<String> involvedAccounts,
                             String brokeOne) {
        super(timestamp, description);
        this.amount = amount;
        this.currency = currency;

        this.involvedAccounts = involvedAccounts;
        this.brokeOne = brokeOne;
    }

    public ObjectNode print(ObjectMapper mapper) {
        ObjectNode result = mapper.createObjectNode();
        result.put("timestamp", getTimestamp());
        result.put("description", getDescription());
        result.put("currency", currency);
        result.put("amount", amount);
        ArrayNode accounts = mapper.createArrayNode();
        for (String account : involvedAccounts) {
            accounts.add(account);
        }
        result.put("involvedAccounts", accounts);
        result.put("error", "Account " + brokeOne + " has insufficient funds for a split payment.");
        return result;
    }

}
