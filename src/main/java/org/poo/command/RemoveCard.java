package org.poo.command;

import org.poo.account.Card;
import org.poo.fileio.CommandInput;

import java.util.HashMap;

public class RemoveCard implements Command {

    private String cardNumber;
    private HashMap<String, Card> cards;
    public RemoveCard (final String cardNumber, final HashMap<String, Card> cards) {
        this.cardNumber = cardNumber;
        this.cards = cards;
    }

    @Override
    public void execute() {
        Card temp = cards.get(cardNumber);
        temp.getAccount().removeCard(temp);

    }
}
