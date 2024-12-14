package org.poo.command;

import org.poo.account.Card;

public class PayOnline implements Command {


    private Card card;
    private double amount;
    private String currency;
    private String email;

    public PayOnline(final Card card, final double amount, final String currency,
                     final String email) {
        this.card = card;
        this.amount = amount;
        this.currency = currency;
        this.email = email;
    }

    public void execute() {
        String correspondingEmail = card.getAccount().getUser().getEmail().toString();
        if (!correspondingEmail.equals(email)) {
            return;
        }
        if (card.getAccount().getBalance() < amount) {
            return;
        }

        card.getAccount().addFunds(-amount);

    }

}
