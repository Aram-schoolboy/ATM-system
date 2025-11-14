package com.mybank.storage.Models.EventTypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mybank.storage.Messages.AccountBalanceChangedMessage;
import com.mybank.storage.Messages.AccountCreationMessage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AccountCreationMessage.class, name = "creation"),
        @JsonSubTypes.Type(value = AccountBalanceChangedMessage.class, name = "balance")
})
public interface AccountEvent {
}
