package data.file

import okio.FileHandle
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

actual class File {

    private var file: FileHandle? = null
    private var path: Path? = null
    actual fun create(path: String, name: String): Result<Boolean> = try {
        FileSystem.SYSTEM.createDirectory(path.toPath(normalize = true), true)
        this.path = (path + "/" + name).toPath(normalize = true)
        file = FileSystem.SYSTEM.openReadWrite(
            this.path!!,
            true,
            false
        )
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    actual fun write(text: String): Result<Boolean> = try {
        FileSystem.SYSTEM.write(path!!, true) {
            writeUtf8(text)
        }
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    actual fun read(): Result<String> = try {
        val text = FileSystem.SYSTEM.read(path!!) {
            readUtf8()
        }
        Result.success(text)
    } catch (e: Exception) {
        Result.failure(e)
    }
}