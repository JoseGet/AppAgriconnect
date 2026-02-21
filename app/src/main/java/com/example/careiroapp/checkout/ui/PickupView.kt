package com.example.careiroapp.checkout.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.BagTopBar
import com.example.careiroapp.checkout.ui.components.BottomBarCheckout
import com.example.careiroapp.checkout.ui.components.Stepper
import com.example.careiroapp.checkout.ui.components.*
import com.example.careiroapp.models.DateTimeOptionModel
import com.example.careiroapp.models.LocationOptionModel

@Composable
fun PickupView(navController: NavController) {

    var selectedLocationId by remember { mutableStateOf<String?>(null) }
    var selectedDateTimeId by remember { mutableStateOf<String?>(null) }

    val isValid = selectedLocationId != null && selectedDateTimeId != null

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

    Scaffold(
        topBar = {
            BagTopBar(
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomBarCheckout(
                enabled = isValid,
                onClick = {
                    Log.d("PickupView", "go to confirm payment screen")
                }
            )
        },
        containerColor = colorResource(R.color.light_background)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Stepper(2)

            Text(
                text = stringResource(R.string.pickup),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(R.color.dark_green)
            )

            Text(
                text = stringResource(R.string.pickup_explanation),
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = stringResource(R.string.pickup_places),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            LocationSelector(
                locations = locations,
                selectedId = selectedLocationId,
                onSelect = { selectedLocationId = it.id }
            )

            Text(
                text = stringResource(R.string.pickup_dates),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            DateTimeSelector(
                options = dateTimes,
                selectedId = selectedDateTimeId,
                onSelect = { selectedDateTimeId = it.id }
            )
        }
    }
}
