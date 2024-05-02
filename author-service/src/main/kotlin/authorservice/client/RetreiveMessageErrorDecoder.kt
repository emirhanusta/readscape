package authorservice.client

import authorservice.exception.ExceptionMessage
import authorservice.exception.NotFoundException
import feign.Response
import feign.codec.ErrorDecoder
import org.apache.commons.io.IOUtils
import org.springframework.http.HttpStatus
import java.io.IOException
import java.lang.Exception
import java.nio.charset.StandardCharsets

class RetreiveMessageErrorDecoder : ErrorDecoder {
    private val errorDecoder = ErrorDecoder.Default()
    override fun decode(methodKey: String?, response: Response?): Exception {
        var exceptionMessage = ExceptionMessage()
        try {
            if (response != null) {
                response.body()?.let { body ->
                    body.asInputStream().use {
                        exceptionMessage = ExceptionMessage(
                            response.headers()["date"]?.toString(),
                            response.status(),
                            HttpStatus.resolve(response.status())!!.reasonPhrase,
                            IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8),
                            response.request().url()
                        )
                    }
                }
            }
        } catch (exception: IOException) {
            return Exception(exception.message)
        }
        if (response != null) {
            if (response.status() == 404) {
                throw NotFoundException(exceptionMessage)
            }
        }
        return errorDecoder.decode(methodKey, response)
    }
}