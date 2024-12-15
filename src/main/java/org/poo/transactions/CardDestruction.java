package org.poo.transactions;

public class CardDestruction extends CardCreation {

    public CardDestruction(final int timestamp, final String card, final String cardHolder, final String account) {
        super(timestamp, card, cardHolder, account);
        setDescription("The card has been destroyed");
    }
}
