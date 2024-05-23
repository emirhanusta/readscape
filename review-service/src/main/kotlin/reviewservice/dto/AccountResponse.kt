package reviewservice.dto

import java.util.*

data class AccountResponse(
    val id: UUID,
    val username: String,
    val email: String,
    val role: String
)
