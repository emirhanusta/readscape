package reviewservice.dto

import org.jetbrains.annotations.NotNull
import java.util.UUID

data class ReviewRequest(
    @NotNull
    val userId: UUID,
    @NotNull
    val bookId: UUID,
    @NotNull
    val rating: Float,
    val review: String
)
