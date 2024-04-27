package authorservice.client

import authorservice.dto.BookResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@FeignClient(name = "book-service", path = "/api/v1/books")
interface BookServiceClient {

    @GetMapping("/byAuthors/{authorId}")
    fun getBooksByAuthor(@PathVariable authorId: UUID): ResponseEntity<List<BookResponse>>
}