package reviewservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import reviewservice.model.Review
import java.util.*

interface ReviewRepository : JpaRepository<Review, UUID>{
}