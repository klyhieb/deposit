package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import kh.com.acleda.deposits.core.util.decimalAmountTransformation.DecimalAmountTransformation
import kh.com.acleda.deposits.core.util.decimalAmountTransformation.InputFilterRegex
import kh.com.acleda.deposits.core.util.decimalAmountTransformation.filteredDecimalText

@Composable
fun TextFieldSelect(
    modifier: Modifier = Modifier,
    valueText: String,
    labelText: String = "",
    placeHolderText: String,
    borderColor: Color,
    textColor: Color,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box {
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = textColor,
                unfocusedBorderColor = borderColor,
                unfocusedLabelColor = textColor,
                unfocusedLeadingIconColor = borderColor,
                unfocusedTrailingIconColor = borderColor,
                focusedTextColor = textColor,
                focusedBorderColor = borderColor,
                focusedLabelColor = textColor,
                focusedLeadingIconColor = borderColor,
                focusedTrailingIconColor = borderColor,
            ),
            value = valueText,
            label = {
                if (labelText.isNotEmpty()) {
                    Text(labelText, style = MaterialTheme.typography.labelLarge)
                }
            },
            onValueChange = { },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(painterResource(leadingIcon), contentDescription = null)
                }
            },
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(painterResource(trailingIcon), contentDescription = null)
                }
            },
            singleLine = true,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
                unfocusedLeadingIconColor = Color.Transparent,
                unfocusedTrailingIconColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                focusedLeadingIconColor = Color.Transparent,
                focusedTrailingIconColor = Color.Transparent,
            ),
            placeholder = { Text(placeHolderText, style = MaterialTheme.typography.labelLarge) },
            value = "",
            onValueChange = { },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(painterResource(leadingIcon), contentDescription = null)
                }
            },
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(painterResource(trailingIcon), contentDescription = null)
                }
            },
            singleLine = true,
            readOnly = true,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        if (interactionSource.collectIsPressedAsState().value) {
            onClick()
        }
    }
}

@Composable
fun TextFieldInput(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = null,
    valueText: TextFieldValue,
    labelText: String,
    borderColor: Color,
    textColor: Color,
    onValueChange: (TextFieldValue) -> Unit
) {
    var text by remember { mutableStateOf(valueText) }
    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = textColor,
            unfocusedBorderColor = borderColor,
            unfocusedLabelColor = textColor,
            unfocusedLeadingIconColor = borderColor,
            unfocusedTrailingIconColor = borderColor,
            focusedTextColor = textColor,
            focusedBorderColor = borderColor,
            focusedLabelColor = textColor,
            focusedLeadingIconColor = borderColor,
            focusedTrailingIconColor = borderColor,
        ),
        value = text,
        onValueChange = {
            if (!it.text.contains(InputFilterRegex.DecimalInput)) {
                return@OutlinedTextField
            }
            text = filteredDecimalText(it)
            onValueChange(text)
        },
        label = { Text(labelText) },
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(painterResource(leadingIcon), contentDescription = null)
            }
        },
        visualTransformation = DecimalAmountTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier.fillMaxWidth()
    )
}