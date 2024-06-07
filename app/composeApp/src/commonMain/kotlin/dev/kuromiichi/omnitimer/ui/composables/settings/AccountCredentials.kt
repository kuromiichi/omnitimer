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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
            TextField(
                value = email,
                onValueChange = { onEmailChange(it) },
                singleLine = true,
                label = { Text(text = "Email") }
            )
            TextField(
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { onPasswordChange(it) },
                singleLine = true,
                label = { Text(text = "Password") }
            )
            if (!isLogin) {
                var pwdRepeat by rememberSaveable { mutableStateOf("") }
                TextField(
                    value = pwdRepeat,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { pwdRepeat = it },
                    singleLine = true,
                    label = { Text(text = "Repeat Password") }
                )
            }
            Column(
                modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { onTopButtonClicked(isLogin) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isLogin) "Login" else "Register")
                }
                Button(
                    onClick = { onBottomButtonClicked(isLogin) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isLogin) "Register" else "Login")
                }
            }
        }
    }
}
