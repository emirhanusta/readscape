package reviewservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reviewservice.dto.AccountResponse
import java.util.*

@FeignClient(name = "account-service", path = "/api/v1/accounts")
interface AccountServiceClient {

    @GetMapping("/{accountId}")
    fun getAccountById(@PathVariable accountId: UUID): ResponseEntity<AccountResponse>
}