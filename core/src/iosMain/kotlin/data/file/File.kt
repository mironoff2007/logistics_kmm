package data.file

import okio.FileHandle
import okio.FileSystem
import okio.Path.Companion.toPath

actual class File actual constructor(path: String, name: String) {

    private var dir = path.toPath().normalized()
    private var filePath = "$path$name"
    private val path = filePath.toPath(true)
    private fun create() {
        try {
            FileSystem.SYSTEM.createDirectory(dir, false)

            FileSystem.SYSTEM.openReadWrite(
                path,
                true,
                false
            )
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    actual fun write(text: String): Result<Boolean> = try {
        val exist = FileSystem.SYSTEM.exists(path)
        FileSystem.SYSTEM.write(file = path, mustCreate = !exist) {
            writeUtf8(text)
        }
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    actual fun read(): Result<String> = try {
        val exist = FileSystem.SYSTEM.exists(path)
        if (!exist) create()
        val text = FileSystem.SYSTEM.read(path) {
            readUtf8()
        }
        Result.success(text)
    } catch (e: Exception) {
        Result.failure(e)
    }
}