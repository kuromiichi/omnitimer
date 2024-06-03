package ui.composables.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AccountCredentials(
    isLogin: Boolean,
    email: String,
    password: String,
    passwordRepeat: String,
    name: String,
    errorMessage: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordRepeatChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onTopButtonClicked: (Boolean) -> Unit,
    onBottomButtonClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isLogin) "Login" else "Register",
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(value = email,
                onValueChange = { onEmailChange(it) },
                singleLine = true,
                label = { Text(text = "Email") })
            TextField(value = password,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { onPasswordChange(it) },
                singleLine = true,
                label = { Text(text = "Password") })
            if (!isLogin) {
                TextField(value = passwordRepeat,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { onPasswordRepeatChange(it) },
                    singleLine = true,
                    label = { Text(text = "Repeat Password") })
                TextField(value = name,
                    onValueChange = { onNameChange(it) },
                    singleLine = true,
                    label = { Text(text = "Name") })
            }
            if (errorMessage.isNotEmpty()) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.errorContainer,
                    shadowElevation = 4.dp
                ) {
                    Text(
                        text = errorMessage, modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { onTopButtonClicked(isLogin) }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isLogin) "Login" else "Register")
                }
                Button(
                    onClick = { onBottomButtonClicked(isLogin) }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isLogin) "Register" else "Login")
                }
            }
        }
    }
}
