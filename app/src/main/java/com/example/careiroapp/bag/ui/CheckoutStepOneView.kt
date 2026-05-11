package com.example.careiroapp.bag.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.CheckoutMainButton
import com.example.careiroapp.bag.ui.components.DateTimeSelector
import com.example.careiroapp.bag.ui.components.LocationSelector
import com.example.careiroapp.bag.ui.components.Stepper
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily
import com.example.careiroapp.models.DateTimeOptionModel
import com.example.careiroapp.models.LocationOptionModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CheckoutStepOneView(
    padding: PaddingValues,
    onButtonClick: (String, String, String) -> Unit
) {
    val locations = listOf(
        LocationOptionModel("2", "Feira Central"),
    )

    val filteredFairDays = getFairDays(dayOfWeek = DayOfWeek.FRIDAY)

    val dateTimes = listOf(
        DateTimeOptionModel("1", filteredFairDays.first(), "06h-08h"),
        DateTimeOptionModel("2", filteredFairDays.first(), "08h-10h"),
        DateTimeOptionModel("3", filteredFairDays.first(), "10h-12h"),
        DateTimeOptionModel("4", filteredFairDays.last(), "06h-08h"),
        DateTimeOptionModel("5", filteredFairDays.last(), "08h-10h"),
        DateTimeOptionModel("6", filteredFairDays.last(), "10h-12h")
    )

    var selectedLocationId by remember { mutableStateOf<String?>(null) }
    var selectedDateTimeId by remember { mutableStateOf<String?>(null) }

    val selectedLocation = locations.find { it.id == selectedLocationId }
    val selectedDateTime = dateTimes.find { it.id == selectedDateTimeId }
    val isValid = selectedLocationId != null && selectedDateTimeId != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 15.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Stepper(1)

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

@RequiresApi(Build.VERSION_CODES.O)
private fun getFairDays(
    dayOfWeek: DayOfWeek
): List<String> {

    val today = LocalDate.now()

    val formater = DateTimeFormatter.ofPattern("dd/MM")

    var nextDays = today.with(TemporalAdjusters.next(dayOfWeek))

    val fairDaysList = mutableListOf<String>()

    repeat(2) {
        println(nextDays.format(formater))
        fairDaysList.add(nextDays.format(formater))
        nextDays = nextDays.plusWeeks(1)
    }

    return fairDaysList
}
