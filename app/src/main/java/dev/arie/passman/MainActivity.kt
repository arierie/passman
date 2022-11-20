package dev.arie.passman

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val passwordGenerator by lazy {
        PasswordGenerator()
    }
    private val clipboardManager by lazy {
        getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val password = remember {
                    mutableStateOf("")
                }
                PasswordScreen(
                    password,
                    onPasswordClicked = {
                        clipboardManager.setPrimaryClip(
                            ClipData.newPlainText(
                                "Password",
                                password.value
                            )
                        )
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                            Toast.makeText(this.applicationContext, "Copied", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    onButtonClicked = {
                        password.value = passwordGenerator.generatePassword() ?: ""
                    }
                )
            }
        }
    }

    companion object {
        init {
            System.loadLibrary("rust")
        }
    }
}

@Composable
fun PasswordScreen(
    password: MutableState<String>,
    onButtonClicked: () -> Unit,
    onPasswordClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = password.value, modifier = Modifier.clickable {
            onPasswordClicked()
        })
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = {
            onButtonClicked()
        }) {
            Text(text = "Generate password")
        }
    }
}
