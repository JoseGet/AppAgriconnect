package com.example.careiroapp.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.careiroapp.R

@Composable
fun AppAlertDialog(
    onDismissRequest: (() -> Unit),
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    hadDismissButton: Boolean = false
) {
    AlertDialog(
        title = {
            Text(
                text = dialogTitle,
                style = TextStyle(
                    fontFamily = montserratBoldFontFamily
                )
            )
        },
        text = {
            Text(
                text = dialogText,
                style = TextStyle(
                    fontFamily = montserratRegularFontFamily
                )
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    "Ok",
                    fontFamily = montserratBoldFontFamily,
                    color = colorResource(R.color.dark_green)
                )
            }
        },
        dismissButton = if (hadDismissButton) {
            {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        } else {
            null
        }
    )
}

@Preview
@Composable
fun AppAlertDialogPreview() {
    AppAlertDialog(
        onDismissRequest = {},
        onConfirmation = {},
        dialogTitle = "Title",
        dialogText = "Text"
    )
}
