package data.file

expect class File constructor() {

    fun create(path: String, name: String): Result<Boolean>

    fun write(text: String): Result<Boolean>

    fun read(): Result<String>

}