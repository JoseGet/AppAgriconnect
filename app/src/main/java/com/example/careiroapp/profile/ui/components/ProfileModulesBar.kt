package com.example.careiroapp.profile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily
import com.example.careiroapp.profile.ui.viewmodel.ProfileModules

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileModulesBar(
    currentModule: ProfileModules,
    onCLick: (ProfileModules) -> Unit
) {

    var selectedDestination by rememberSaveable { mutableIntStateOf(currentModule.ordinal) }

    PrimaryTabRow(
        containerColor = Color.Transparent,
        selectedTabIndex = selectedDestination,
        modifier = Modifier
            .fillMaxWidth(),
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(selectedDestination),
                color = colorResource(R.color.top_bar_title_color),
                width = 102.dp
            )
        },
        divider = {}
    ) {
        ProfileModules.entries.forEachIndexed { index, module ->
            Tab(
                selected = selectedDestination == index,
                onClick = {
                    selectedDestination = index
                    onCLick(module)
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = module.name,
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 14.sp,
                        letterSpacing = 1.sp,
                        fontFamily = if (selectedDestination == index) montserratBoldFontFamily else montserratRegularFontFamily,
                        color = colorResource(R.color.top_bar_title_color)
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun ProfileModulesBarPreview() {
    ProfileModulesBar(
        ProfileModules.PEDIDOS,
        onCLick = {}
    )
}