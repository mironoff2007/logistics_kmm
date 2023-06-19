package ru.mironov.common.data

import android.os.Build
import androidx.core.content.ContextCompat.getExternalFilesDirs
import ru.mironov.common.AppContext

actual fun getFilesPath(): String {
    val applicationContext = AppContext.appContext
    var rootPath = ""
    if (applicationContext.getExternalFilesDir(null) == null) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            rootPath = getExternalFilesDirs(applicationContext, null)[0].absolutePath

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            rootPath = applicationContext.filesDir.absolutePath
        }
    } else {
        rootPath = applicationContext.getExternalFilesDir(null)!!.absolutePath
    }
    return rootPath + ""
}