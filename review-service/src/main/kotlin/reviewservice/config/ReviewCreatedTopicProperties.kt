package reviewservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka.topics.review-created")
class ReviewCreatedTopicProperties {
    lateinit var topicName: String
    lateinit var partitionsCount: String
    var replicationFactor: Short = 0
    lateinit var retentionMs: String
}