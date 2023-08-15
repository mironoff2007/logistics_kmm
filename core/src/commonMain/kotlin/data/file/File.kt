package data.file

expect class File constructor(path: String, name: String) {

    fun append(text: String): Result<Boolean>
    fun write(text: String): Result<Boolean>
    fun read(): Result<String>

}