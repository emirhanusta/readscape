package reviewservice.dto

import java.time.LocalDate

data class BookResponse(
    val id: String,
    val title: String,
    val authorId: String,
    val description: String,
    val genres: List<String>,
    val publishedDate: LocalDate?
)
