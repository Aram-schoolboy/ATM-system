package Messages;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountBalanceChangedMessage(Long accountId, BigDecimal newBalance, LocalDateTime date) {
}
