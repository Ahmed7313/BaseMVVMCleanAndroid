package com.aramex.mypos.Presentation.Components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.daman.edman.screens.components.AppSpacer
import com.daman.edman.screens.components.HeaderText
import com.example.basemvvmcleanandroid.ui.theme.BahijSansArabic
import com.example.basemvvmcleanandroid.ui.theme.borderColor
import com.example.basemvvmcleanandroid.ui.theme.labelColor
import com.trend.camelx.ui.theme.extraSmall
import com.trend.camelx.ui.theme.large
import com.trend.camelx.ui.theme.small
import kotlinx.coroutines.delay


@Composable
fun MainEditText(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    onTextChange: (String) -> Unit,
    isError: Boolean,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardOptions = KeyboardOptions.Default,
    textAlign: Boolean = false,
    readOnly: Boolean = false,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done, // New argument to specify IME action
    onImeAction: (() -> Unit)? = null,  // Optional callback for custom action on IME action
    focusRequester: FocusRequester = remember { FocusRequester() } // New focus requester
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    var passwordVisible by rememberSaveable { mutableStateOf(false) } // State to toggle password visibility
    val visualTransformation =
        if (!passwordVisible && isPassword) PasswordVisualTransformation()
        else VisualTransformation.None

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester), // Add focusRequester to control focus
        interactionSource = interactionSource,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = borderColor,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
        ),
        trailingIcon = {
            if (isPassword) {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            } else {
                trailingIcon?.invoke()
            }
        },
        leadingIcon = {
            leadingIcon?.invoke()
        },
        isError = isError,
        readOnly = readOnly,
        label = {
            AutoSizeText(
                text = label ?: "",
                color = Color.Gray,
                fontFamily = BahijSansArabic,
                fontSize = 12.sp
            )
        },
        keyboardOptions = keyboardType.copy(
            keyboardType = if (isPassword) KeyboardType.Password else keyboardType.keyboardType,
            imeAction = imeAction // Set the IME action
        ),
        visualTransformation = visualTransformation,
        shape = shape,
        keyboardActions = KeyboardActions(
            onNext = {
                onImeAction?.invoke() // Call custom callback for next action
                focusManager.moveFocus(FocusDirection.Next) // Move to next field
            },
            onDone = {
                onImeAction?.invoke() // Call custom callback for done action
                keyboardController?.hide()
            }
        ),
        textStyle = if (textAlign) LocalTextStyle.current.copy(textAlign = TextAlign.Center) else LocalTextStyle.current,
    )
}

@Composable
fun MainEditTextWithoutIcon(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    aboveLabel: String? = null,
    labelIcon: ImageVector? = null,
    onTextChange: (String) -> Unit,
    isError: Boolean,
    isPassword: Boolean = false,
    keyboardType: KeyboardOptions = KeyboardOptions.Default,
    textAlign: Boolean = false,
    readOnly: Boolean = false,
    showLabelFocused: Boolean = false
) { // Added parameter to control label visibility) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    var passwordVisible by rememberSaveable { mutableStateOf(false) } // State to toggle password visibility
    val focusRequester = remember { FocusRequester() }
    val showKeyboard = remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current


    val visualTransformation =
        if (!passwordVisible && isPassword) PasswordVisualTransformation()
        else
            VisualTransformation.None


    Column(
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (labelIcon != null && aboveLabel != null) {
                Icon(
                    imageVector = labelIcon,
                    contentDescription = "Trailing icon",
                    modifier = Modifier.size(large)
                )

                AppSpacer(width = extraSmall)

                Text(
                    text = aboveLabel,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = BahijSansArabic,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF898FA4),
                        textAlign = TextAlign.Right,
                    )
                )
            }

        }
        AppSpacer(height = small)
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
//                    focusManager.clearFocus(true)
            interactionSource = interactionSource,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = borderColor,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            isError = isError,
            readOnly = readOnly,
            label = {
                // The label should be visible only when the text field is not focused and the text is empty
                if (!isFocused && text.isEmpty()) {
                    AutoSizeText(
                        text = label ?: "",
                        color = Color.Gray,
                        fontFamily = BahijSansArabic,
                        fontSize = 12.sp
                    )
                }
            }, // Show the label based on isFocused or showLabelFocused
            keyboardOptions = keyboardType.copy(
                keyboardType = if (isPassword) KeyboardType.Password
                else keyboardType.keyboardType
            ),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(15.dp),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                }
            ),
            textStyle = if (textAlign) LocalTextStyle.current.copy(textAlign = TextAlign.Center) else LocalTextStyle.current,

            )
    }
}

@Composable
fun MainEditTextFramed(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    onTextChange: (String) -> Unit,
    isError: Boolean,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardOptions = KeyboardOptions.Default,
    textAlign: Boolean = false,
    readOnly: Boolean = false,
    aboveText: String,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done, // New argument to specify IME action
    onImeAction: (() -> Unit)? = null,  // Optional callback for custom action on IME action
    focusRequester: FocusRequester = remember { FocusRequester() } // New focus requester
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    var passwordVisible by rememberSaveable { mutableStateOf(false) } // State to toggle password visibility
    val visualTransformation =
        if (!passwordVisible && isPassword) PasswordVisualTransformation()
        else VisualTransformation.None

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray, shape = shape)
    ) {
        HeaderText(
            text = aboveText,
            color = Color(0xFF898FA4),
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            fontSize = 12
        )

        AppSpacer(height = 8.dp)

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .focusRequester(focusRequester), // Add focusRequester to control focus
            interactionSource = interactionSource,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedLabelColor = borderColor,
                focusedLabelColor = Color.Transparent
            ),
            trailingIcon = {
                if (isPassword) {
                    val image =
                        if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                } else {
                    trailingIcon?.invoke()
                }
            },
            isError = isError,
            readOnly = readOnly,
            label = {
                AutoSizeText(
                    text = label ?: "",
                    color = labelColor ,
                    fontFamily = BahijSansArabic,
                    fontSize = 12.sp
                )
            },
            keyboardOptions = keyboardType.copy(
                keyboardType = if (isPassword) KeyboardType.Password else keyboardType.keyboardType,
                imeAction = imeAction // Set the IME action
            ),
            visualTransformation = visualTransformation,
            keyboardActions = KeyboardActions(
                onNext = {
                    onImeAction?.invoke() // Call custom callback for next action
                    focusManager.moveFocus(FocusDirection.Next) // Move to next field
                },
                onDone = {
                    onImeAction?.invoke() // Call custom callback for done action
                    keyboardController?.hide()
                }
            ),
            textStyle = if (textAlign) LocalTextStyle.current.copy(textAlign = TextAlign.Center) else LocalTextStyle.current,
        )

    }
}

