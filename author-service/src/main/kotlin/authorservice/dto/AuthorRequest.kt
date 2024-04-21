package authorservice.dto

import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class AuthorRequest(
    @NotNull
    val name: String,
    @NotNull
    val biography: String,
    @NotNull
    val birthDate: LocalDate
)
