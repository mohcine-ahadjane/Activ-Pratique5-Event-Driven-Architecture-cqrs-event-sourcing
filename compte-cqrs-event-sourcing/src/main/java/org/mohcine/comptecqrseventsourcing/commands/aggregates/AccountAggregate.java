package org.mohcine.comptecqrseventsourcing.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.mohcine.comptecqrseventsourcing.commonapi.commands.CreateAccountCommand;
import org.mohcine.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import org.mohcine.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import org.mohcine.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import org.mohcine.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import org.mohcine.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import org.mohcine.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import org.mohcine.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import org.mohcine.comptecqrseventsourcing.commonapi.exceptions.AmountNegativeException;
import org.mohcine.comptecqrseventsourcing.commonapi.exceptions.BalanceNotSuffisantException;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accounId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
        // Required by AXON
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getInitialBalance()< 0) throw new RuntimeException("Impossible transaction");
        //OK
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accounId=event.getId();
        this.balance=event.getInitialBalance();
        this.currency= event.getCurrency();
        this.status=AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status=event.getStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand command){
        if(command.getAmount()<0) throw new AmountNegativeException("Amount shouldn't be negative");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency(),
                status));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance+=event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        if(command.getAmount()<0) throw new AmountNegativeException("Amount shouldn't be negative");
        if (this.balance< command.getAmount()) throw new BalanceNotSuffisantException("Balance not suffisant Exception balance = "+ balance);
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance-=event.getAmount();
    }
}
