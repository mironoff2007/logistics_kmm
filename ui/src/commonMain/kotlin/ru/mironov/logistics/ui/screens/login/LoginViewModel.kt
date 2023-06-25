package ru.mironov.logistics.ui.screens.login

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.domain.model.Res
import ru.mironov.domain.model.web.ErrorCodes
import ru.mironov.domain.settings.UserData
import ru.mironov.domain.viewmodel.State
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.logistics.SharedPreferences
import ru.mironov.logistics.repo.LoginRepo
import ru.mironov.logistics.ui.SingleEventFlow

@Inject
class LoginViewModel(
    private val prefs: SharedPreferences,
    private val loginRepo: LoginRepo,
    private val logger: Logger
) : ViewModel() {

    val loginResult = SingleEventFlow<State<Boolean>?>()
    val userNameLoaded = SingleEventFlow<String>()

    fun login(login: String, password: String) {
        viewModelScope.launch {
            loginResult.postEvent(State.Loading())
            loginRepo.login(userName = login, password = password).let {
                when (it) {
                    is Res.Success -> {
                        val userSettings = prefs.load() ?: UserData()
                        userSettings.add(UserData.UserName, login)
                        prefs.save(userSettings)
                        loginResult.postEvent(State.Success(true))
                    }
                    is Res.Error -> {
                        loginResult.postEvent(State.Error(it.exception.message ?: ""))
                    }
                    is Res.HttpError -> {
                        val msg = ErrorCodes.localizedError(it.error.code)
                        loginResult.postEvent(State.Error(msg))
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            loginRepo.logout()
        }
    }

    fun onScreenComposed() {
        viewModelScope.launch {
            logger.logD("Login Screen", "Login screen opened")
            val userSettings = prefs.load() ?: UserData()
            val userName = userSettings.getString(UserData.UserName) ?: ""
            userNameLoaded.postEvent(userName)
        }
    }


}