package reviewservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class ReviewServiceApplication

fun main(args: Array<String>) {
	runApplication<ReviewServiceApplication>(*args)
}
