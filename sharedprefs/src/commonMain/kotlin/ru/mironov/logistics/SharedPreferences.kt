package ru.mironov.logistics

import data.file.File
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.data.DataConstants
import ru.mironov.common.data.getFilesPath
import ru.mironov.domain.settings.BaseSettings

@Inject
class SharedPreferences {

    val path = getFilesPath() + "/" + DataConstants.APP_FOLDER_NAME + "/"

    inline fun <reified T : BaseSettings> createFile(): File? {
        val kClass = T::class
        val settingName = kClass.simpleName
        return try {
            File(path, "$settingName.json")
        } catch (e: Exception) {
            println(e.stackTraceToString())
            null
        }
    }

    inline fun <reified T: BaseSettings> save(settings: T): Boolean {
        val file = createFile<T>()
        return try {
            val string = Json.encodeToString(settings)
            file?.write(string)
            true
        }
        catch (e: Exception) {
            false
        }
    }

    inline fun <reified T : BaseSettings> load(): T? {
        val file = createFile<T>()
        return try {
            val read = file?.read()
            var text = read?.getOrThrow()
            return if (text.isNullOrBlank()) null
            else Json.decodeFromString<T>(text)
        } catch (e: Exception) {
            println(e.stackTraceToString())
            null
        }
    }

}