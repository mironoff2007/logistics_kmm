package ru.mironov.common.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList


@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    list: ImmutableList<String>,
    onValueChange: (value: String) -> Unit,
    label: String = "",
    selected: String? = null,
    textSize:TextUnit = TextUnit.Unspecified,
    resetSelected: Boolean = false
) {
    var mExpanded by remember { mutableStateOf(false) }

    var selectedValue by remember { mutableStateOf(selected ?: "") }

    if (resetSelected) selectedValue = selected ?: ""

    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    Box(
        modifier = modifier.wrapContentHeight(),
    ) {
        val separator = if (label.isNotBlank()) "-" else ""
        Row(
            modifier = modifier.wrapContentHeight().border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(5.dp))
                .padding(5.dp)
                .clickable { mExpanded = !mExpanded },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AnnotatedString("$label $separator $selectedValue"), style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = textSize,
                    color = MaterialTheme.colors.onSurface
                )
            )
            Icon(icon, "label", Modifier)
        }
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.wrapContentSize()
        ) {
            list.forEach { menuItem ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange.invoke(menuItem)
                        selectedValue = menuItem
                        mExpanded = false
                    }) {
                    Text(
                        text = AnnotatedString(menuItem),
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.h4.fontFamily,
                            fontSize = textSize,
                            color = MaterialTheme.colors.onSurface
                        )
                    )
                }
            }
        }
    }
}
