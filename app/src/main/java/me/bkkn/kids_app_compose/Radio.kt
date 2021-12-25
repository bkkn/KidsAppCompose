package me.bkkn.kids_app_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.bkkn.kids_app_compose.model.MainViewModel

@Composable
fun RadioBtns(viewModel: MainViewModel = viewModel()) {
    val options: List<ContentScale> = listOf(
        ContentScale.Crop,
        ContentScale.Fit,
        ContentScale.FillWidth,
        ContentScale.FillHeight,
        ContentScale.Fit,
        ContentScale.Inside,
        ContentScale.FillBounds
    )

//    var myContentScele = object : ContentScale {
//        override fun computeScaleFactor(srcSize: Size, dstSize: Size): ScaleFactor {
//            return super.computeScaleFactor()
//        }
//
//        override fun toString(): String {
//            return super.toString()
//        }
//    }

    Column {
        val selectedLanguage = radioGroup(
            viewModel,
            radioOptions = options,
            title = "Choose an option",
            cardBackgroundColor = Color(0xFFFFFAF0)
        )

        viewModel.currentOptionState.value = selectedLanguage
    }
}

@Composable
fun radioGroup(
    viewModel: MainViewModel,
    radioOptions: List<ContentScale> = listOf(),
    title: String = "",
    cardBackgroundColor: Color = Color(0xFFFEFEFA)
): ContentScale {
    if (radioOptions.isNotEmpty()) {
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(radioOptions[0])

        }

        Card(
            backgroundColor = cardBackgroundColor,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                Modifier.padding(10.dp)
            ) {
                Text(
                    text = title,
//                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp),
                )

                radioOptions.forEach { item ->
                    Row(
                        Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (item == selectedOption),
                            onClick = { onOptionSelected(item) }
                        )

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) { append("  $item  ") }
                        }

                        ClickableText(
                            text = annotatedString,
                            onClick = {
                                onOptionSelected(item)
                            }
                        )
                    }
                }
            }
        }
        return selectedOption
    } else {
        return ContentScale.Crop
    }
}