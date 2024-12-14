package org.poo.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Card {
    private StringBuilder status;
    private StringBuilder cardNumber;
    private Account account;

    public Card(final StringBuilder cardNumber, final Account account) {
        this.status = new StringBuilder("active");
        this.cardNumber = cardNumber;
        this.account = account;
    }

    public ObjectNode printCard(ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("status", status.toString());
        node.put("cardNumber", cardNumber.toString());
        return node;
    }


}
