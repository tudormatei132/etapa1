package org.poo.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Account {
    private User user;
    private StringBuilder IBAN;
    private StringBuilder currency;
    private StringBuilder type;
    private double interestRate;
    private double balance;
    private ArrayList<Card> cards;


    public Account(final User user, final StringBuilder IBAN, final StringBuilder currency,
                   final double interestRate) {
        this.user = user;
        this.IBAN = IBAN;
        this.currency = currency;
        this.interestRate = interestRate;
        this.balance = 0;
        cards = new ArrayList<>();
    }


    public void AddCard(final Card card) {
        cards.add(card);
    }

    public ObjectNode printAccount(ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("IBAN", IBAN.toString());
        node.put("currency", currency.toString());
        node.put("balance", balance);
        node.put("type", type.toString());
        ArrayNode cardsNode = mapper.createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.printCard(mapper));
        }
        node.set("cards", cardsNode);
        return node;
    }

    public void addFunds(final double funds) {
        balance += funds;
    }

}
