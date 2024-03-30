package reviewservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Review not found")
class ReviewNotFoundException(message: String) : RuntimeException(message) {
}