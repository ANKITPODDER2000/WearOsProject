package com.example.mobileapplication.uicomponent

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun GetBasicButton(
    btnText: String,
    modifier: Modifier,
    shape: Shape = ButtonDefaults.shape,
    onClickHandler: () -> Unit,
) {
    Button(
        onClick = { onClickHandler() },
        modifier = modifier, shape = shape
    ) {
        Text(text = btnText)
    }
}