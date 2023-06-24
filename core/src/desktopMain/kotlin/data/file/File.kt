package data.file

import java.nio.file.Files
import kotlin.io.path.Path

actual class File actual constructor(val path: String, val name: String) {

    private var file: java.io.File? = null
    private fun create() {
        try {
            if (!Files.exists(Path(path))) Files.createDirectory(Path(path))
            file = java.io.File("${path}/$name")
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    actual fun write(text: String): Result<Boolean> = try {
        if (file == null) create()
        file!!.bufferedWriter().use { out -> out.write(text) }
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    actual fun read(): Result<String> = try {
        if (file == null) create()
        var text: String?
        file!!.bufferedReader().use { input -> text = input.readLine() }
        Result.success(text!!)
    } catch (e: Exception) {
        Result.failure(e)
    }

}