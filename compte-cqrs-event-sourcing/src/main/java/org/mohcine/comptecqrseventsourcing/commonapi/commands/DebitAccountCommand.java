package org.mohcine.comptecqrseventsourcing.commonapi.commands;

public class DebitAccountCommand extends BaseCommand<String>{
    private double amount;
    private String currency;

    public DebitAccountCommand(String id, double initialBalance, String currency) {
        super(id);
        this.amount = initialBalance;
        this.currency = currency;
    }
}
