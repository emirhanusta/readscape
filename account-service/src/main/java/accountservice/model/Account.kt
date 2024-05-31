package accountservice.model

import accountservice.model.enums.Role
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import java.util.UUID


@Entity
data class Account @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(unique = true)
    var username: String,
    var email: String,
    val password: String,
    @Enumerated(EnumType.ORDINAL)
    val role: Role,
    @CreationTimestamp
    val createdAt: Timestamp? = null,
    @UpdateTimestamp
    val updatedAt: Timestamp? = null
)
