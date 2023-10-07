package data.file

expect class MultiplatformFile constructor(path: String, name: String) {

    fun append(text: String): Result<Boolean>
    fun write(text: String): Result<Boolean>
    fun read(): Result<String>
    fun path(): String
    fun name(): String
    fun listPath(): Result<List<String>>
    fun delete(): Result<Boolean>

}