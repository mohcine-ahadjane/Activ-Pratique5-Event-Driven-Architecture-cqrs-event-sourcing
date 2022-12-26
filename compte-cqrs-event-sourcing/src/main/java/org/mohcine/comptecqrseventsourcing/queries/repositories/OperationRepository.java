package org.mohcine.comptecqrseventsourcing.queries.repositories;

import org.mohcine.comptecqrseventsourcing.queries.entities.Account;
import org.mohcine.comptecqrseventsourcing.queries.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
