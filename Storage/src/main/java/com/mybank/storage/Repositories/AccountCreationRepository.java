package com.mybank.storage.Repositories;

import com.mybank.storage.Messages.AccountCreationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCreationRepository extends JpaRepository<AccountCreationMessage, Long> {
}