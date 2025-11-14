package com.mybank.storage.Consumers;

import com.mybank.storage.Messages.UserCreationMessage;
import com.mybank.storage.Messages.UserFriendsModificationMessage;
import com.mybank.storage.Repositories.UserCreationRepository;
import com.mybank.storage.Repositories.UserFriendsModificationRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClientEventsConsumer {

    private final UserCreationRepository userCreationRepository;
    private final UserFriendsModificationRepository userFriendsModificationRepository;

    public ClientEventsConsumer(UserCreationRepository userCreationRepository,
                                UserFriendsModificationRepository userFriendsModificationRepository) {
        this.userCreationRepository = userCreationRepository;
        this.userFriendsModificationRepository = userFriendsModificationRepository;
    }

    @KafkaListener(
            topics = "client-topic",
            groupId = "storage-client-group",
            containerFactory = "clientKafkaListenerFactory"
    )
    public void consume(ConsumerRecord<String, Object> record) {
        if (record.value() instanceof Messages.UserFriendsModificationMessage userFriendsModificationMessage) {
            userFriendsModificationRepository.save(new UserFriendsModificationMessage(userFriendsModificationMessage));
        } else if (record.value() instanceof Messages.UserCreationMessage userCreationMessage) {
            userCreationRepository.save(new UserCreationMessage(userCreationMessage));
        }
    }

}
