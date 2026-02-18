package com.example.careiroapp.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.OutlineAppButton
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun HomeCardFeira(
    modifier: Modifier,
    image: String,
    localText: String,
    dataText: String,
    titleText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(173.dp)
            .wrapContentHeight()
            .background(color = Color.Transparent),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(25.dp),
    ) {
        Column(
            modifier = Modifier
                .background(color = colorResource(R.color.light_gray)),
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(173.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                titleText,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                maxLines = 2,
                fontSize = 16.sp,
                fontFamily = montserratBoldFontFamily,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.map_marker),
                    contentDescription = "",
                    tint = colorResource(R.color.dark_green)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    localText,
                    fontSize = 14.sp,
                    maxLines = 2,
                    fontFamily = montserratRegularFontFamily,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(R.drawable.calendar),
                    contentDescription = "",
                    tint = colorResource(R.color.dark_green)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    dataText,
                    fontSize = 14.sp,
                    maxLines = 2,
                    color = Color.Black,
                    fontFamily = montserratRegularFontFamily,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                Arrangement.Center
            ) {
                OutlineAppButton(
                    text = stringResource(R.string.ver_mais),
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    onClick = onClick,
                    icon = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeCardFeiraPreview() {
    HomeCardFeira(
        modifier = Modifier,
        image = "",
        localText = "Parque das Laranjeiras",
        dataText = "20/09/25",
        titleText = "Doge",
        onClick = {}
    )
}