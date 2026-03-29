package com.example.careiroapp.common.components.footer

import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun ColumnsFooter() {

    val uriHandler = LocalUriHandler.current
    val moverseNaWebUrl : String = "https://moverse.ceweb.br"

    val tituloTextStyle = TextStyle(
        fontFamily = montserratBoldFontFamily,
        fontSize = 18.sp,
        color = colorResource(R.color.dark_green)
    )

    val subtituloTextStyle = TextStyle(
        fontFamily = montserratRegularFontFamily,
        fontSize = 14.sp,
        color = Color.Black
    )

    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontFamily = montserratRegularFontFamily, color = colorResource(R.color.black))) {
            append(annotatedStringResource(R.string.apoio_content))
        }

        withLink(
            link = LinkAnnotation.Url(
                url = moverseNaWebUrl,
                styles = TextLinkStyles(
                    style = SpanStyle(
                        color = colorResource(R.color.dark_green),
                        fontFamily = montserratRegularFontFamily,
                        textDecoration = TextDecoration.Underline
                    )
                ),
                linkInteractionListener = {
                    uriHandler.openUri(moverseNaWebUrl)
                }
            )
        ) {
            append(moverseNaWebUrl)
        }
    }

    Column() {
        Text(stringResource(R.string.o_agriconnect), style = tituloTextStyle)
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.o_que_e), style = subtituloTextStyle)
        Spacer(Modifier.height(4.dp))
        Text(stringResource(R.string.quem_faz_parte), style = subtituloTextStyle)
        Spacer(Modifier.height(4.dp))
        Text(stringResource(R.string.como_participar), style = subtituloTextStyle)
        Spacer(Modifier.height(24.dp))
        Text(stringResource(R.string.apoio), style = tituloTextStyle)
        Spacer(Modifier.height(4.dp))
        Text(
            annotatedText,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun annotatedStringResource(@StringRes id: Int): AnnotatedString {
    val resources = LocalResources.current
    return remember(id) {
        val text = resources.getText(id)
        if (text is Spanned) {
            val spanStyles = mutableListOf<AnnotatedString.Range<SpanStyle>>()
            spanStyles.addAll(text.getSpans(0, text.length, UnderlineSpan::class.java).map {
                AnnotatedString.Range(
                    SpanStyle(textDecoration = TextDecoration.Underline),
                    text.getSpanStart(it),
                    text.getSpanEnd(it)
                )
            })
            spanStyles.addAll(text.getSpans(0, text.length, StyleSpan::class.java).map {
                AnnotatedString.Range(
                    SpanStyle(fontWeight = FontWeight.Bold),
                    text.getSpanStart(it),
                    text.getSpanEnd(it)
                )
            })
            AnnotatedString(text.toString(), spanStyles = spanStyles)
        } else {
            AnnotatedString(text.toString())
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ColumnsFooterPreview() {
    ColumnsFooter()
}
