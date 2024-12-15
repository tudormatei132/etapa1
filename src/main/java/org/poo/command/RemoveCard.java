package org.poo.command;

import org.poo.account.Card;
import org.poo.fileio.CommandInput;
import org.poo.transactions.CardDestruction;

import java.util.HashMap;

public class RemoveCard implements Command {

    private String cardNumber;
    private HashMap<String, Card> cards;
    private int timestamp;
    public RemoveCard (final String cardNumber, final HashMap<String, Card> cards,
                       final int timestamp) {
        this.cardNumber = cardNumber;
        this.cards = cards;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Card temp = cards.get(cardNumber);
        if (temp == null) {
            return;
        }
        CardDestruction removed = new CardDestruction(timestamp, cardNumber,
                                    temp.getAccount().getUser().getEmail().toString(),
                                    temp.getAccount().getIBAN().toString());
        temp.getAccount().getUser().getTransactions().add(removed);
        temp.getAccount().removeCard(temp);
        cards.remove(temp.getCardNumber().toString());
    }
}
