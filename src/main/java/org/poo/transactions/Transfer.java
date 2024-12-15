package org.poo.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Transfer extends Transaction {

    private String amount, transferType, senderIBAN, receiverIBAN;

    public Transfer(final int timestamp, final String description, final String amount,
                    final String transferType, final String senderIBAN, final String receiverIBAN) {
        super(timestamp, description);
        this.amount = amount;
        this.transferType = transferType;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
    }

    public ObjectNode print(ObjectMapper mapper) {
        ObjectNode result = mapper.createObjectNode();
        result.put("timestamp", getTimestamp());
        result.put("description", getDescription());
        result.put("senderIBAN", senderIBAN);
        result.put("receiverIBAN", receiverIBAN);
        result.put("amount", amount);
        result.put("transferType", transferType);
        return result;
    }

}
