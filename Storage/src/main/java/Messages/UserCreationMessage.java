package Messages;


import java.time.LocalDateTime;

public record UserCreationMessage(Long userId, String login, String name, int age, String gender, String hairColor, LocalDateTime date) {
}
