package org.poo.account;

public class OneTimeCard extends Card {


    public OneTimeCard(StringBuilder cardNumber, Account account) {
        super(cardNumber, account);
    }

    public void use(double amount) {
        getAccount().addFunds(-amount);
        setStatus(new StringBuilder("inactive"));
    }

}
