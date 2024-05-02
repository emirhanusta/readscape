package accountservice.client;


import accountservice.exception.ExceptionMessage;
import accountservice.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public Exception decode(String s, Response response) {
        ExceptionMessage exceptionMessage;
        try(InputStream inputStream = response.body().asInputStream()) {
            exceptionMessage = new ExceptionMessage(
                    response.headers().get("date").toArray()[0].toString(),
                    response.status(),
                    Objects.requireNonNull(HttpStatus.resolve(response.status())).getReasonPhrase(),
                    IOUtils.toString(inputStream, StandardCharsets.UTF_8),
                    response.request().url()
            );
        } catch (IOException e) {
            return new Exception("Failed to process response body", e);
        }
        if (response.status() == 404) {
            return new NotFoundException(exceptionMessage);
        }
        return errorDecoder.decode(s, response);
    }
}
