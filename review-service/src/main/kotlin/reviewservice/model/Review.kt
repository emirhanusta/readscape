package reviewservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Review(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
        val accountId: UUID,
        val bookId: UUID,
        var rating: Float,
        var review: String,
        @CreationTimestamp
        val createdAt: LocalDateTime? = null,
        @UpdateTimestamp
        val updatedAt: LocalDateTime? = null
)
