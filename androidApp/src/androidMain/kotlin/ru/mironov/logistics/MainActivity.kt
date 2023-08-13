package ru.mironov.logistics

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ru.mironov.common.Logger
import ru.mironov.logistics.MainScreen
import ru.mironov.logistics.logging.LoggerImpl

class MainActivity : AppCompatActivity() {

    val logger: Logger = LoggerImpl()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logger.logD("activity", "create")
        setContent {
            MainScreen()
        }
    }
}