package Messages;


import java.time.LocalDateTime;

public record UserFriendsModificationMessage(
        String authorLogin, String targetLogin, String operationType, LocalDateTime date
) {
}
