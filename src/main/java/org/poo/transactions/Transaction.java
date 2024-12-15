package org.poo.transactions;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Transaction {

    private int timestamp;
    private String description;

    public Transaction(int timestamp, String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    public ObjectNode print(ObjectMapper mapper) {
        ObjectNode result = mapper.createObjectNode();
        result.put("timestamp", timestamp);
        result.put("description", description);
        return result;
    }

}
