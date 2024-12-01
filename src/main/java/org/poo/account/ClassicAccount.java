package org.poo.account;

public class ClassicAccount extends Account {

    public ClassicAccount(final User user, final StringBuilder IBAN, final StringBuilder currency,
                          final double interestRate) {
        super(user, IBAN, currency, interestRate);
        this.setType(new StringBuilder("classic"));
    }
}
