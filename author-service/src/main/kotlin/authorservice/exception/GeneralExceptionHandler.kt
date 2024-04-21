package authorservice.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import java.util.Objects

@RestControllerAdvice
class GeneralExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AuthorNotFoundException::class)
    fun handleReviewNotFoundException(ex: AuthorNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(404).body(Objects.requireNonNull(ex.message))
    }
}
