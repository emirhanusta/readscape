package reviewservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reviewservice.dto.BookResponse
import java.util.*

@FeignClient(name = "book-service", path = "/api/v1/books")
interface BookServiceClient {

    @GetMapping("/{bookId}")
    fun getBookById(@PathVariable bookId: UUID): ResponseEntity<BookResponse>
}