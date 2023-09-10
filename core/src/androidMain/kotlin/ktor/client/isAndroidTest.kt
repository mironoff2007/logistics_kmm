package ktor.client

actual fun isAndroidTest(): Boolean =
    try {
        Class.forName("ru.mironov.logistics.CustomTestRunner")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
