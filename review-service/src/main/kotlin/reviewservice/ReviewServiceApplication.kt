package reviewservice

import feign.Logger
import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import reviewservice.client.RetreiveMessageErrorDecoder

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
class ReviewServiceApplication{

	@Bean
	fun errorDecoder(): ErrorDecoder {
		return RetreiveMessageErrorDecoder()
	}

	@Bean
	fun feignLoggerLevel(): Logger.Level {
		return Logger.Level.FULL
	}
}
fun main(args: Array<String>) {
	runApplication<ReviewServiceApplication>(*args)
}
