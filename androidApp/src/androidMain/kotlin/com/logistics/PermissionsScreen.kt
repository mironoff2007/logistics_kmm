package ru.mironov.logistics

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


@Composable
fun requestWrite(switchFlag: () -> Unit) {
    requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, switchFlag)
}

@Composable
fun requestRead(switchFlag: () -> Unit) {
    requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, switchFlag)
}

@Composable
fun requestPermission(permission: String, switchFlag: () -> Unit) {
    var launchAgain by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("PermissionsScreen","PERMISSION GRANTED $permission")
            launchAgain = false
            switchFlag.invoke()

        } else {
            // Permission Denied: Do something
            Log.d("PermissionsScreen","PERMISSION DENIED $permission")
            launchAgain = true
        }
    }
    val context = LocalContext.current


    if(launchAgain) {
        launchAgain = false
        // Check permission
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) -> {
                // Some works that require permission
                Log.d("PermissionsScreen", "Already granted $permission")
                switchFlag.invoke()
            }
            else -> {
                // Asking for permission
                Log.d("PermissionsScreen", "Ask $permission")
                SideEffect {
                    launcher.launch(permission)
                }
            }
        }
    }
}