package authorservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthorServiceApplication

fun main(args: Array<String>) {
	runApplication<AuthorServiceApplication>(*args)
}
