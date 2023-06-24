package data.file

import okio.FileHandle
import okio.FileSystem
import okio.Path.Companion.toPath

actual class File actual constructor(val path: String, val name: String) {

    private var file: FileHandle? = null
    private var dir = path.toPath().normalized()
    private var filePath = ("$path/$name").toPath().normalized()
    private fun create() {
        try {
            FileSystem.SYSTEM.createDirectory(dir, true)

            file = FileSystem.SYSTEM.openReadWrite(
                filePath,
                true,
                false
            )
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    actual fun write(text: String): Result<Boolean> = try {
        val exist = FileSystem.SYSTEM.exists(filePath)
        if (!exist) create()
        FileSystem.SYSTEM.write(filePath, false) {
            writeUtf8(text)
        }
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    actual fun read(): Result<String> = try {
        val exist = FileSystem.SYSTEM.exists(filePath)
        if (!exist) create()
        val text = FileSystem.SYSTEM.read(filePath) {
            readUtf8()
        }
        Result.success(text)
    } catch (e: Exception) {
        Result.failure(e)
    }
}