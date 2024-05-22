package notificationservice.consumers.consumer;

import lombok.RequiredArgsConstructor;
import notificationservice.consumers.model.ReviewCreatedEvent;
import notificationservice.dto.NotificationRequest;
import notificationservice.service.NotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewCreatedEventConsumer {
    Logger log = LoggerFactory.getLogger(ReviewCreatedEventConsumer.class);
    private final NotificationService notificationService;

    @KafkaListener(topics = "${kafka.topics.review-created.topic}",
            groupId = "${kafka.topics.review-created.consumerGroup}",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void consumeCreatedUserEvent(@Payload ReviewCreatedEvent eventData,
                                        @Headers ConsumerRecord<String, Object> consumerRecord) {
        log.info("ReviewCreatedEventConsumer.consumeApprovalRequestResultedEvent consumed EVENT :{} " +
                        "from partition : {} " +
                        "with offset : {} " +
                        "thread : {} " +
                        "for message key: {}",
                eventData, consumerRecord.partition(), consumerRecord.offset(), Thread.currentThread().getName(), consumerRecord.key());

        NotificationRequest entity = NotificationRequest.from(eventData.getPayload());

        notificationService.sendNotification(entity);

    }
}
