package ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DropdownMenu
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