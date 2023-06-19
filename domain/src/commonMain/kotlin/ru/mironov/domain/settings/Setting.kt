@file:Suppress("UNCHECKED_CAST")

package ru.mironov.domain.settings
import com.mironov.localization.Localization
import kotlinx.serialization.*

abstract class Setting<V>(val defaultValue: V) {
    val name: String = this::class.simpleName ?: ""
}

@Serializable
open class BaseSettings() {
    private val intMap = hashMapOf<String, Int>()
    private val enumMap = hashMapOf<String, String>()
    private val stringMap = hashMapOf<String, String>()
    private val boolMap = hashMapOf<String, Boolean>()

    fun getInt(setting: Setting<out Int>): Int? {
       return intMap[setting.name]
    }

    fun getBool(setting: Setting<out Boolean>): Boolean? {
        return boolMap[setting.name]
    }

    fun getEnum(setting: Setting<out Enum<*>>): Enum<*>? {
        val valueString = enumMap[setting.name]
        return when (setting.defaultValue) {
            is Localization.Language -> Localization.Language.valueOf(valueString ?: Localization.Language.English.name)
            else -> null

        }
    }

    fun getString(setting: Setting<out String>): String? {
        return stringMap[setting.name]
    }

    fun add(setting: Setting<out Any>, value: Any) {
        if (setting.defaultValue::class != value::class) throw Exception("passed value is wrong type")
        when (setting.defaultValue) {
            is Int -> intMap[setting.name] = value as Int
            is Enum<*> -> enumMap[setting.name] = (value as Enum<*>).name
            is Boolean -> boolMap[setting.name] = value as Boolean
            is String -> stringMap[setting.name] = value as String
            else -> {}
        }
    }

    fun loadByList(list: List<Setting<out Any>>): Map<Setting<out Any>, Any> {
        val valuesMap = mutableMapOf<Setting<out Any>, Any>()
        list.forEach { setting ->
            val value: Any? = when (setting.defaultValue) {
                is Int -> getInt(setting as Setting<out Int>)
                is Enum<*> -> getEnum(setting as Setting<out Enum<*>>)
                is Boolean -> getBool(setting as Setting<out Boolean>)
                is String-> getString(setting as Setting<out String>)
                else -> null
            }
            if (value == null ) valuesMap[setting] = setting.defaultValue
            else valuesMap[setting] = value
        }
        return valuesMap
    }
}

@Serializable
class CommonSettings(): BaseSettings(){
    object LogsEnabled : Setting<Boolean>(false)

    object AppLocale : Setting<Localization.Language>(Localization.Language.English)
}

@Serializable
class UserSettings(): BaseSettings(){
    object UserName : Setting<String>("")
}



