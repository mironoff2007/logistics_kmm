package ru.mironov.logistics.ui.screens.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DialogLayout(showMsg: String) {
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(15.dp),
        elevation = 10.dp,
        color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier, contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier.padding(15.dp),
                text = AnnotatedString(showMsg),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                )
            )
        }
    }
}