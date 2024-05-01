package reviewservice.exception

data class ExceptionMessage (

    val timestamp: String?,
    val status: Int?,
    val error: String?,
    val message: String?,
    val path: String?
){
    constructor() : this(null, null, null, null, null)
}
