package ru.mironov.logistics.ui.screens.login

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.domain.model.Result
import ru.mironov.domain.model.web.ErrorCodes
import ru.mironov.domain.settings.UserData
import ru.mironov.domain.viewmodel.State
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.logistics.SharedPreferences
import ru.mironov.logistics.UserRole
import ru.mironov.logistics.repo.ParcelsSynchronizer
import ru.mironov.logistics.repo.UserSessionRepo
import ru.mironov.logistics.ui.SingleEventFlow
import ru.mironov.logistics.ui.navigation.Screens

@Inject
class LoginViewModel(
    private val prefs: SharedPreferences,
    private val userSessionRepo: UserSessionRepo,
    private val parcelsSynchronizer: ParcelsSynchronizer,
    private val logger: Logger
) : ViewModel() {

    val loginResult = SingleEventFlow<State<Screens>?>()
    val userNameLoaded = SingleEventFlow<String>()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        logger.logE(LOG_TAG, throwable.stackTraceToString())
    }
    private val supervisor = SupervisorJob()

    fun login(login: String, password: String) {
        viewModelScope.launch(exceptionHandler + supervisor) {
            loginResult.postEvent(State.Loading())
            userSessionRepo.login(userName = login, password = password).let {
                when (it) {
                    is Result.Success -> {
                        val userSettings = prefs.load() ?: UserData()
                        userSettings.add(UserData.UserName, login)
                        prefs.save(userSettings)

                        parcelsSynchronizer.start()

                        val goToScreen = when (userSessionRepo.getRole()) {
                            UserRole.DRIVER -> Screens.CarCargo
                            else -> Screens.RegisterSenderParcel
                        }
                        loginResult.postEvent(State.Success(goToScreen))
                    }
                    is Result.Error -> loginResult.postEvent(State.Error(it.exception.message ?: ""))

                    is Result.HttpError -> {
                        val msg = ErrorCodes.localizedError(it.error.code)
                        loginResult.postEvent(State.Error(msg))
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(exceptionHandler + supervisor) {
            userSessionRepo.logout()
        }
    }

    fun onScreenComposed() {
        viewModelScope.launch(exceptionHandler + supervisor) {
            logger.logD(LOG_TAG, "Login screen opened")
            val userSettings = prefs.load() ?: UserData()
            val userName = userSettings.getString(UserData.UserName) ?: ""
            userNameLoaded.postEvent(userName)
        }
    }

    companion object {
        private const val LOG_TAG = "LoginVM"
    }
}