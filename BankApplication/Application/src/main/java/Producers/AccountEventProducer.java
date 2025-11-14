package Producers;

import Messages.AccountBalanceChangedMessage;
import Messages.AccountCreationMessage;
import Models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class AccountEventProducer {
    private final KafkaTemplate<Long, Object> kafkaTemplate;
    private final String topicName = "account-topic";

    @Autowired
    public AccountEventProducer(KafkaTemplate<Long, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void ProduceAccountCreationMessage(Account account) {
        AccountCreationMessage message = new AccountCreationMessage(
                account.getOwner().getLogin(),
                account.getId(),
                LocalDateTime.now()
        );

        kafkaTemplate.send(topicName, account.getId(), message);
    }

    public void ProduceAccountBalanceChangedMessage(Long id, BigDecimal newBalance) {
        AccountBalanceChangedMessage message = new AccountBalanceChangedMessage(
                id,
                newBalance,
                LocalDateTime.now()
        );

        kafkaTemplate.send(topicName, id, message);
    }
}