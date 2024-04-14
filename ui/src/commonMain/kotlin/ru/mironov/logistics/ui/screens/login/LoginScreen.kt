package ru.mironov.logistics.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mironov.localization.StringRes
import kotlinx.coroutines.Job
import ru.mironov.common.navigation.TopBar
import ru.mironov.common.res.ImageRes
import ru.mironov.common.res.localizedString
import ru.mironov.common.util.ENTER_SYMBOL
import ru.mironov.domain.viewmodel.State
import ru.mironov.logistics.ui.navigation.Navigator
import ui.getPainterResource

@Composable
fun LoginScreen(
    openDrawer: () -> Job,
    vm: LoginViewModel,
    navigator: Navigator,
    showMsg: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            title = localizedString(StringRes.Login),
            buttonIcon = Icons.Filled.Menu
        ) { openDrawer() }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(Unit) {
                vm.onScreenComposed()
            }

            val loading = remember { mutableStateOf(false) }
            vm.loginResult.Observe()
            vm.loginResult.onEvent { state ->
                when (state) {
                    is State.Success -> {
                        navigator.navigate(state.value.getName())
                        loading.value = false
                    }
                    is State.Loading -> {
                        loading.value = true
                    }

                    is State.Error -> {
                        loading.value = false
                        showMsg.invoke(state.msg)
                    }
                    else -> {}
                }
            }

            val userName = remember { mutableStateOf(TextFieldValue("")) }
            vm.userNameLoaded.Observe()
            vm.userNameLoaded.onEvent { userName.value = TextFieldValue(it) }

            val onChangeName = fun(name: TextFieldValue) {
                userName.value = name
            }

            val login = fun(login: String, password: String) {
                vm.login(login, password)
            }

            LoginLayout(login, loading.value, userName.value, onChangeName)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginLayout(
    login: (String, String) -> Unit,
    loading: Boolean,
    userName: TextFieldValue,
    onChaneName: (TextFieldValue) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val keyboardController = LocalSoftwareKeyboardController.current

        val password = remember { mutableStateOf(TextFieldValue()) }
        val showPassword = remember { mutableStateOf(false) }
        val transformPassword = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation()

        if (password.value.text.contains(ENTER_SYMBOL)) {
            password.value = TextFieldValue(password.value.text.replace(ENTER_SYMBOL, ""))
            keyboardController?.hide()
        }

        Text(
            text = localizedString(StringRes.Login),
            style = TextStyle(fontSize = 30.sp, fontFamily = MaterialTheme.typography.h1.fontFamily)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = localizedString(StringRes.UserName)) },
            value = userName,
            onValueChange = { value ->onChaneName.invoke(value) },
            enabled = !loading
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = localizedString(StringRes.Password)) },
            value = password.value,
            visualTransformation = transformPassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { value -> password.value = value  },
            enabled = !loading,
            trailingIcon = {
                Image(
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { showPassword.value = !showPassword.value },
                    painter = getPainterResource(ImageRes.Eye),
                    contentDescription = "show password icon",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                    alpha = if (showPassword.value) 1f else 0.5f
                )
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier
                .padding(40.dp, 0.dp, 40.dp, 0.dp)
                .testTag(LOGIN_BTN_TAG)
        ) {
            if (loading) CircularProgressIndicator()
            else Button(
                onClick = { login.invoke(userName.text, password.value.text) },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Text(text = localizedString(StringRes.SignIn))
            }
        }
    }
}

const val LOGIN_BTN_TAG = "LOGIN_BTN_TAG"



