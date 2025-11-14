package com.mybank.storage.Repositories;

import com.mybank.storage.Messages.AccountBalanceChangedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceChangedRepository extends JpaRepository<AccountBalanceChangedMessage, Long> {
}
