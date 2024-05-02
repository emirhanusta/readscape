package reviewservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
class NotFoundException(message: ExceptionMessage) : RuntimeException() {

    private val exceptionMessage: ExceptionMessage = message

    fun getMessage(): ExceptionMessage{
        return exceptionMessage
    }
}