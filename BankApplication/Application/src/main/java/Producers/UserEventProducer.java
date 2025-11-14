package Producers;

import Messages.UserCreationMessage;
import Messages.UserFriendsModificationMessage;
import Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topicName = "client-topic";

    @Autowired
    public UserEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void ProduceUserCreationMessage(User user) {
        UserCreationMessage message = new UserCreationMessage(
                user.getUserId(),
                user.getLogin(),
                user.getName(),
                user.getAge(),
                user.getGender().toString(),
                user.getHairColor().toString(),
                LocalDateTime.now()
        );

        kafkaTemplate.send(topicName, user.getLogin(), message);
    }

    public void ProduceUserFriendsModificationMessage(String authorLogin, String targetLogin, String operationType) {
        UserFriendsModificationMessage message = new UserFriendsModificationMessage(
                authorLogin,
                targetLogin,
                operationType,
                LocalDateTime.now()
        );

        kafkaTemplate.send(topicName, authorLogin, message);
    }
}