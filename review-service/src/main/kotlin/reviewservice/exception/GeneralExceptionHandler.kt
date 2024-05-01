package reviewservice.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Objects

@RestControllerAdvice
class GeneralExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(ReviewNotFoundException::class)
    fun handleReviewNotFoundException(ex: ReviewNotFoundException): ResponseEntity<Any> {
        return ResponseEntity.status(404).body(Objects.requireNonNull(ex.message))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<Any> {
        return ResponseEntity.status(404).body(Objects.requireNonNull(ex.getMessage()))
    }
}