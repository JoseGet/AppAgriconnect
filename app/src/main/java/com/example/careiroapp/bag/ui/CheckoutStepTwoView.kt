package com.example.careiroapp.bag.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.CheckoutMainButton
import com.example.careiroapp.bag.ui.components.DateTimeSelector
import com.example.careiroapp.bag.ui.components.LocationSelector
import com.example.careiroapp.bag.ui.components.Stepper
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily
import com.example.careiroapp.models.DateTimeOptionModel
import com.example.careiroapp.models.LocationOptionModel

@Composable
fun CheckoutStepTwoView(
    padding: PaddingValues,
    onButtonClick: (String, String, String) -> Unit
) {
    val locations = listOf(
        LocationOptionModel("1", "Feira da Estrada de Autazes"),
        LocationOptionModel("2", "Feira Central"),
        LocationOptionModel("3", "Feira Norte"),
        LocationOptionModel("4", "Sede da Associação Mamuri")
    )

    val dateTimes = listOf(
        DateTimeOptionModel("1", "21/08", "14h-15h"),
        DateTimeOptionModel("2", "21/08", "15h-16h"),
        DateTimeOptionModel("3", "23/08", "09h-10h"),
        DateTimeOptionModel("4", "23/08", "10h-11h"),
        DateTimeOptionModel("5", "24/08", "11h-12h"),
        DateTimeOptionModel("6", "24/08", "13h-14h")
    )

    var selectedLocationId by remember { mutableStateOf<String?>(null) }
    var selectedDateTimeId by remember { mutableStateOf<String?>(null) }

    val selectedLocation = locations.find { it.id == selectedLocationId }
    val selectedDateTime = dateTimes.find { it.id == selectedDateTimeId }
    val isValid = selectedLocationId != null && selectedDateTimeId != null

    Column(
        modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 15.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Stepper(2)

        Text(
            text = stringResource(R.string.pickup),
            fontSize = 18.sp,
            color = colorResource(R.color.dark_green),
            fontFamily = montserratBoldFontFamily
        )

        Text(
            text = stringResource(R.string.pickup_explanation),
            fontSize = 14.sp,
            fontFamily = montserratRegularFontFamily,
            color = Color.Black
        )

        Text(
            text = stringResource(R.string.pickup_places),
            fontSize = 16.sp,
            fontFamily = montserratBoldFontFamily,
            color = Color.Black
        )

        LocationSelector(
            locations = locations,
            selectedId = selectedLocationId,
            onSelect = { selectedLocationId = it.id }
        )

        Text(
            text = stringResource(R.string.pickup_dates),
            fontSize = 16.sp,
            fontFamily = montserratBoldFontFamily,
            color = Color.Black
        )

        DateTimeSelector(
            options = dateTimes,
            selectedId = selectedDateTimeId,
            onSelect = { selectedDateTimeId = it.id }
        )

        CheckoutMainButton(
            {
                if (!isValid) {
                    Log.i("","")
                } else {
                    onButtonClick(
                        selectedDateTime?.date ?: "",
                        selectedDateTime?.time ?: "",
                        selectedLocation?.label ?: ""
                    )
                }
            },
            isValid = isValid
        )
    }
}
