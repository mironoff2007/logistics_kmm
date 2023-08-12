package ru.mironov.logistics

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.logistics.MainApp

class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(
        classLoader, MainApp::class.java.name, context
    )
}