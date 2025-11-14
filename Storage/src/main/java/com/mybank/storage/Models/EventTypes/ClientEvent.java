package com.mybank.storage.Models.EventTypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mybank.storage.Messages.UserCreationMessage;
import com.mybank.storage.Messages.UserFriendsModificationMessage;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserCreationMessage.class, name = "creation"),
        @JsonSubTypes.Type(value = UserFriendsModificationMessage.class, name = "friends")
})
public interface ClientEvent {
}
