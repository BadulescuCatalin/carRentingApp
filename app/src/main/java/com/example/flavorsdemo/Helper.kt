import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.flavorsdemo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomClickableTextLoginRegister(
    text: String,
    onClickAction: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        append(text)
        pushStringAnnotation(
            tag = "CLICK",
            annotation = "clickableText"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("here")
        }
        pop()
        append("!")
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "CLICK", start = offset, end = offset)
                .firstOrNull()?.let {
                    onClickAction()
                }
        },
        style = TextStyle(color = Color.Gray, fontSize = 14.sp)
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboard: KeyboardOptions
) {
    val context = LocalContext.current
    var showPopup by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                // Reset popup visibility when user starts typing
                showPopup = false
            },
            shape = RoundedCornerShape(50.dp),
            label = { Text(text = label) },
            keyboardOptions = keyboard,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(id = R.color.light_brown), // Ensure you have this color defined in your resources
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
                if (keyboard.keyboardType == KeyboardType.Email && !value.matches(emailPattern.toRegex())) {
                    val image = Icons.Rounded.Error
                    val description = "Invalid email address"
                    IconButton(onClick = {
                        showPopup = true
                        // Auto-hide the popup after 2 seconds
                        coroutineScope.launch {
                            delay(1500)
                            showPopup = false
                        }
                    }) {
                        Icon(
                            imageVector = image, contentDescription = description,
                            tint = Color.Red
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        if (showPopup) {
            Box( modifier = Modifier
                .padding(end = 48.dp)
                .align(Alignment.End)
            ) {
                Popup(
                    alignment = Alignment.BottomEnd,
                    properties = PopupProperties()
                ) {
                    Text(
                        text = "Invalid email address",
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.Red, RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp),

                        color = Color.White
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    label: String
) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        shape = RoundedCornerShape(50.dp),
        label = { Text(text = label) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(R.color.light_brown),
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = onPasswordVisibilityChange) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}