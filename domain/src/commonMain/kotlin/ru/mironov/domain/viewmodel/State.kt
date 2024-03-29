package ru.mironov.domain.viewmodel

sealed class State<T> {
    class Success<T>(val value: T) : State<T>()
    class Loading<T> : State<T>()

    class Error<T>(val msg: String) : State<T>()

}
