package com.yusuf.yusuf_mucahit_solmaz_final.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yusuf.yusuf_mucahit_solmaz_final.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(modifier: Modifier, value: String, updatedText: (String) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = { newText ->
            updatedText(newText)
        },
        modifier = modifier
            .heightIn(54.dp, 108.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.ai_chat_bubble))
    ) {
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            singleLine = false,
            enabled = true,
            placeholder = { Text("Test Text") },
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(),
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = colorResource(id = R.color.bottom_chat)
            )
        )
    }
}