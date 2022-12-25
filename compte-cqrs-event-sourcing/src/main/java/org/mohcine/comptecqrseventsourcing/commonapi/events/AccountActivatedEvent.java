package org.mohcine.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;
import org.mohcine.comptecqrseventsourcing.commonapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{
    @Getter private AccountStatus status;
    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
