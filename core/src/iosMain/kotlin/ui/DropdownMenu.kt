package ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset

@Composable
actual fun ExpectDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    focusable: Boolean,
    modifier: Modifier,
    offset: DpOffset,
    content: @Composable() ColumnScope.() -> Unit
) = DropdownMenu(
    expanded,
    onDismissRequest,
    focusable,
    modifier,
    offset,
    content
)

@Composable
actual fun ExpectDropdownMenuItem(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier,
    leadingIcon: @Composable() (() -> Unit)?,
    trailingIcon: @Composable() (() -> Unit)?,
    enabled: Boolean,
    contentPadding: PaddingValues,
    interactionSource: MutableInteractionSource
) = DropdownMenuItem(
    text = text,
    onClick = onClick,
    modifier = modifier,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon,
    enabled = enabled,
    contentPadding = contentPadding,
    interactionSource = interactionSource
)