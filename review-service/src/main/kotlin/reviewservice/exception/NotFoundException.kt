package reviewservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
class NotFoundException : RuntimeException {

    private val exceptionMessage: ExceptionMessage
    constructor(message: ExceptionMessage){
        this.exceptionMessage = message
    }

    fun getMessage(): ExceptionMessage{
        return exceptionMessage
    }
}