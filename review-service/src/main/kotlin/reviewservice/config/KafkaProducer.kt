package reviewservice.config

import org.apache.kafka.clients.producer.RecordMetadata
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class KafkaProducer(
     val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val log = LoggerFactory.getLogger(KafkaProducer::class.java)

    fun sendMessage(message: GenericMessage<Any>) {
        val future: CompletableFuture<SendResult<String, Any>> = kafkaTemplate.send("review_service.review_created.0",message)

        future.whenComplete { result, ex ->
            if (ex == null) {
                if (result == null) {
                    log.info("Empty result on success for message {}", message)
                    return@whenComplete
                }
                val metadata: RecordMetadata = result.recordMetadata
                log.info("Message :{} published, topic : {}, partition : {} and offset : {}",
                    result.producerRecord.value(),
                    metadata.topic(),
                    metadata.partition(),
                    metadata.offset())
            } else {
                log.error("Unable to deliver message to kafka", ex)
            }
        }
    }
}
