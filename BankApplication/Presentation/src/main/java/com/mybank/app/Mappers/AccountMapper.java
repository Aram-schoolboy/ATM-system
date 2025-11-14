package com.mybank.app.Mappers;

import Models.Account;
import com.mybank.app.DTO.AccountDTO;

public class AccountMapper {
    public static AccountDTO toDto(Account account) {
        return new  AccountDTO(
                account.getId(),
                account.getBalance(),
                UserMapper.toDTO(account.getOwner()));
    }
}
