package ru.mironov.logistics.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mironov.localization.Localization
import kotlinx.collections.immutable.toPersistentList
import ru.mironov.domain.settings.CommonSettings
import ru.mironov.domain.settings.Setting
import ru.mironov.domain.settings.SettingItem
import ru.mironov.common.ui.Spinner
import kotlin.math.roundToLong

@Composable
fun SettingViewItem(
    setting: SettingItem,
    updateItem: (Setting<out Any>, Any) -> Unit,
    textStyle: TextStyle
) {
    Card(
        modifier = Modifier.wrapContentWidth(),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {

        when (setting.setting.defaultValue) {
            is Boolean -> {
                SwitchViewItem(setting, updateItem, textStyle)
            }
            is String -> {
                EditTextViewItem(setting, updateItem)
            }
            is Enum<*> -> {
                when (setting.value) {
                    is Localization.Language -> {
                        val values = Localization.Language.values().map { it.showName }.toPersistentList()
                        val onChange = fun(value: String) {
                            val lang = Localization.Language.getByShowName(value)
                            Localization.setLang(lang)
                            updateItem.invoke(CommonSettings.AppLocale, lang)
                        }
                        Spinner(
                            modifier = Modifier.width(250.dp).padding(10.dp),
                            list = values,
                            onValueChange = onChange,
                            selected = (setting.value as? Localization.Language)?.showName,
                            textSize = 25.sp,
                        )
                    }
                }
            }
            is Long -> {
                when {
                    /* -> TimeEditViewItem(
                        settingItem = setting,
                        updateItem = updateItem,
                        textStyle = textStyle

                    )*/
                    else -> LongViewItem(
                        settingItem = setting,
                        updateItem = updateItem,
                        textStyle = textStyle
                    )
                }
            }
        }
    }

}

@Composable
fun SwitchViewItem(
    setting: SettingItem,
    updateItem: (Setting<out Any>, Any) -> Unit,
    textStyle: TextStyle,
) {
    val checked = (setting.value) as Boolean

    Row(
        modifier = Modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 15.dp),
            text = setting.setting.name + ":", fontSize = 15.sp,
            textAlign = TextAlign.Center,
            style = textStyle
        )
        Checkbox(
            modifier = Modifier.padding(horizontal = 10.dp),
            checked = checked,
            onCheckedChange = {
                updateItem(setting.setting, !checked)
            },
            colors = CheckboxDefaults.colors(
                checkmarkColor = MaterialTheme.colors.surface,
                uncheckedColor = MaterialTheme.colors.onSurface,
                checkedColor = Color.Green
            )
        )
    }
}

@Composable
fun EditTextViewItem(
    settingItem: SettingItem,
    updateItem: (Setting<out Any>, Any) -> Unit,
) {
    Column(
        modifier = Modifier.wrapContentWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var text by remember { mutableStateOf(settingItem.value as? String ?: "") }
        OutlinedTextField(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            value = text,
            textStyle = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 20.sp),
            onValueChange = { value ->
                text = value
                updateItem(settingItem.setting, value)
            },
            label = {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp, end = 15.dp),
                    text = settingItem.setting.name, fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontFamily = MaterialTheme.typography.body1.fontFamily,
                        color = MaterialTheme.colors.primary
                    )
                )
            },
            //enabled = !settingsNotEditable.contains(settingItem.setting)
        )
    }
}


@Composable
private fun LongViewItem(
    settingItem: SettingItem,
    updateItem: (Setting<out Any>, Any) -> Unit,
    textStyle: TextStyle
) {
    var sliderPosition by remember { mutableStateOf(settingItem.value as? Long ?: 0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {


        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = settingItem.setting.name + " - ", style = textStyle)
            Text(text = sliderPosition.toString(), style = textStyle)
        }

        Slider(
            modifier = Modifier.padding(horizontal = 15.dp),
            value = sliderPosition.toFloat(), onValueChange = {
                sliderPosition = it.roundToLong()
                updateItem.invoke(settingItem.setting, it.roundToLong())
            },
            valueRange = 1f..10f
        )
    }
}