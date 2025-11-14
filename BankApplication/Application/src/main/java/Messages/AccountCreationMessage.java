package Messages;

import java.time.LocalDateTime;

public record AccountCreationMessage(String ownerLogin, Long accountId, LocalDateTime date) {

}
