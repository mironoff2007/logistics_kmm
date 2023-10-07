package data.file

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries

actual class MultiplatformFile actual constructor(private val path: String, val name: String) {

    private var file: java.io.File? = null

    init {
        create()
    }

    private fun create() {
        try {
            if (!Files.exists(Path(path))) Files.createDirectory(Path(path))
            file = java.io.File("${path}/$name")
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    actual fun write(text: String): Result<Boolean> =
        try {
            file ?: create()
            file?.writeText(text + "\n")
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    actual fun read(): Result<String> =
        try {
            if (file == null) create()
            var text: String?
            file!!.bufferedReader().use { input -> text = input.readLine() }
            Result.success(text!!)
        } catch (e: Exception) {
            Result.failure(e)
        }

    actual fun append(text: String): Result<Boolean> =
        try {
            file ?: create()
            file?.appendText(text + "\n")
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    actual fun listPath(): Result<List<String>> =
        try {
            val path = Path(path)
            Result.success(path.listDirectoryEntries().map { it.toString() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    actual fun path(): String = path
    actual fun name(): String = name
    actual fun delete(): Result<Boolean> = try {
        Result.success(file?.delete() ?: false)
    } catch (e: Exception) {
        Result.failure(e)
    }

}