package notificationservice.consumers.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.topics.review-created")
@Getter
@Setter
public class ReviewCreatedConsumerProperties {
    private String topic;
    private String consumerGroup;
}
