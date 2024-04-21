package authorservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class AuthorServiceApplication

fun main(args: Array<String>) {
	runApplication<AuthorServiceApplication>(*args)
}
