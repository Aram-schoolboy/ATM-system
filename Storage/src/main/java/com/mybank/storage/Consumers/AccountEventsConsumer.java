package com.mybank.storage.Consumers;

import com.mybank.storage.Messages.AccountBalanceChangedMessage;
import com.mybank.storage.Messages.AccountCreationMessage;
import com.mybank.storage.Repositories.AccountBalanceChangedRepository;
import com.mybank.storage.Repositories.AccountCreationRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventsConsumer {
    private final AccountCreationRepository accountCreationRepository;
    private final AccountBalanceChangedRepository accountBalanceChangedRepository;

    public AccountEventsConsumer(AccountCreationRepository accountCreationRepository,
                                AccountBalanceChangedRepository accountBalanceChangedRepository) {
        this.accountCreationRepository = accountCreationRepository;
        this.accountBalanceChangedRepository = accountBalanceChangedRepository;
    }

    @KafkaListener(
            topics = "account-topic",
            groupId = "storage-account-group",
            containerFactory = "accountKafkaListenerFactory"
    )
    public void consume(ConsumerRecord<Long, Object> record) {
        if (record.value() instanceof Messages.AccountBalanceChangedMessage accountBalanceChangedMessage) {
            accountBalanceChangedRepository.save(new AccountBalanceChangedMessage(accountBalanceChangedMessage));
        } else if (record.value() instanceof Messages.AccountCreationMessage accountCreationMessage) {
            accountCreationRepository.save(new AccountCreationMessage(accountCreationMessage));
        }
    }
}
