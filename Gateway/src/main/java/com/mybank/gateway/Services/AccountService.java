package com.mybank.gateway.Services;

import com.mybank.gateway.DTO.AccountDTO;
import com.mybank.gateway.Exceptions.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    public AccountDTO getAccount(List<AccountDTO> accounts, Long id) {
        if (accounts == null) {
            throw new AccountNotFoundException(id);
        }
        for (AccountDTO account : accounts) {
            if (account.id().equals(id)) {
                return account;
            }
        }

        throw new AccountNotFoundException(id);
    }
}
