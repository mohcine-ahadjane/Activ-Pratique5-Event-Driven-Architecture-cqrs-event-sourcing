package org.mohcine.comptecqrseventsourcing.queries.repositories;

import org.mohcine.comptecqrseventsourcing.queries.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
