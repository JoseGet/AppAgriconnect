package com.example.careiroapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.common.components.cards.CardAboutUs
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun AboutUsView(
    targetSection: Int = -1,
    scrollState: ScrollState? = null,
    scrollTopOffsetPx: Float = 0f
) {
    var section0Y by remember { mutableStateOf(0) }
    var section1Y by remember { mutableStateOf(0) }
    var section2Y by remember { mutableStateOf(0) }
    var didScroll by remember { mutableStateOf(false) }

    LaunchedEffect(section0Y, section1Y, section2Y) {
        if (didScroll || targetSection < 0 || scrollState == null) return@LaunchedEffect
        val targetY = when (targetSection) {
            0 -> section0Y
            1 -> section1Y
            2 -> section2Y
            else -> return@LaunchedEffect
        }
        if (targetY > 0) {
            scrollState.animateScrollTo(targetY)
            didScroll = true
        }
    }

    Column() {
        Image(
            modifier = Modifier
                .height(176.dp)
                .fillMaxWidth(),
            painter = painterResource(R.drawable.banner),
            contentDescription = ""
        )
        Spacer(Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 28.dp)
        ) {
            Box(
                modifier = Modifier.onGloballyPositioned { coords ->
                    if (section0Y == 0) {
                        section0Y = (coords.positionInRoot().y + (scrollState?.value ?: 0) - scrollTopOffsetPx).toInt()
                    }
                }
            ) {
                ModulesHeader(
                    titulo = stringResource(R.string.o_que_e_o_projeto),
                    subtitulo = null
                )
            }
            Text(
                stringResource(R.string.o_que_e_content),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = montserratRegularFontFamily,
                    color = Color.Black
                )
            )
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier.onGloballyPositioned { coords ->
                    if (section1Y == 0) {
                        section1Y = (coords.positionInRoot().y + (scrollState?.value ?: 0) - scrollTopOffsetPx).toInt()
                    }
                }
            ) {
                ModulesHeader(
                    titulo = stringResource(R.string.quem_faz_parte) + "?",
                    subtitulo = null
                )
            }
            Text(
                stringResource(R.string.quem_faz_parte_content),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = montserratRegularFontFamily,
                    color = Color.Black
                )
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                CardAboutUs(
                    modifier = Modifier
                        .weight(1f),
                    title = stringResource(R.string.produtores),
                    image = painterResource(R.drawable.produtores_about_us),
                    description = stringResource(R.string.about_us_products_content)
                )
                Spacer(Modifier.width(16.dp))
                CardAboutUs(
                    modifier = Modifier
                        .weight(1f),
                    title = stringResource(R.string.associacoes),
                    image = painterResource(R.drawable.associacoes_about_us),
                    description = stringResource(R.string.about_us_associacoes_content)
                )
            }
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier.onGloballyPositioned { coords ->
                    if (section2Y == 0) {
                        section2Y = (coords.positionInRoot().y + (scrollState?.value ?: 0) - scrollTopOffsetPx).toInt()
                    }
                }
            ) {
                ModulesHeader(
                    titulo = stringResource(R.string.como_participar) + "?",
                    subtitulo = null
                )
            }
            Text(
                stringResource(R.string.como_participar_content),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = montserratRegularFontFamily,
                    color = Color.Black
                )
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
private fun AboutUsViewPreview() {
    AboutUsView()
}
