package org.poo.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
@Getter
public class Payment extends Transaction  {

    private double amount;
    private String commerciant;

    public Payment(int timestamp, String description, double amount, String commerciant) {
        super(timestamp, description);
        this.amount = amount;
        this.commerciant = commerciant;
    }

    public ObjectNode print(ObjectMapper mapper) {
        ObjectNode result = mapper.createObjectNode();
        result.put("timestamp", getTimestamp());
        result.put("description", getDescription());
        result.put("amount", amount);
        result.put("commerciant", commerciant);
        return result;
    }



}
