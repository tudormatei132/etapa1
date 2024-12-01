package org.poo.account;

public class SavingsAccount extends Account {

    public SavingsAccount(final User user, final StringBuilder IBAN, final StringBuilder currency,
                   final double interestRate) {
         super(user, IBAN, currency, interestRate);
        this.setType(new StringBuilder("savings"));
    }

}
