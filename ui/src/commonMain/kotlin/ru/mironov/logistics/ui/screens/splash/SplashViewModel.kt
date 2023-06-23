package ru.mironov.logistics.ui.screens.splash

import com.mironov.localization.Localization
import com.mironov.localization.Localization.getByLocale
import com.mironov.localization.Localization.setLang
import com.mironov.localization.Localization.setLocale
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.settings.CommonSettings
import ru.mironov.logistics.SharedPreferences
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.logistics.repo.CityRepo

@Inject
class SplashViewModel(
    private val prefs: SharedPreferences,
    private val citiesRepo: CityRepo
): ViewModel() {

    val goToNextScreen = MutableStateFlow(false)

    fun onStartUp(locale: String) {
        viewModelScope.launch {
            val lang = getByLocale(locale)
            var commonPrefs = prefs.load<CommonSettings>()
            if (commonPrefs == null) {
                commonPrefs = CommonSettings()
                commonPrefs.add(CommonSettings.AppLocale, lang)
                prefs.save(commonPrefs)
                setLocale(locale)
            } else {
                val loadedLang = commonPrefs.getEnum(CommonSettings.AppLocale) as? Localization.Language
                if (loadedLang == null) {
                    commonPrefs.add(CommonSettings.AppLocale, lang)
                    prefs.save(commonPrefs)
                } else {
                    setLang(loadedLang)
                }
            }
            citiesRepo.fetchCities()
            delay(2000)
            goToNextScreen.emit(true)
        }
    }
}