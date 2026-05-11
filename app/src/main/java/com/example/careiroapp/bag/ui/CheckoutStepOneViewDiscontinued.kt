//package com.example.careiroapp.bag.ui
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.careiroapp.R
//import com.example.careiroapp.bag.ui.components.CheckoutMainButton
//import com.example.careiroapp.bag.ui.components.Stepper
//import com.example.careiroapp.bag.ui.viewmodel.PayerData
//
//@Composable
//fun CheckoutStepOneViewDiscontinued(
//    padding: PaddingValues,
//    payerData: PayerData,
//    onButtonClick: (String, String, String) -> Unit
//) {
//
//    var email by rememberSaveable { mutableStateOf(payerData.email) }
//    var name by rememberSaveable { mutableStateOf(payerData.name) }
//    var phone by rememberSaveable { mutableStateOf(payerData.telefone) }
//
//    val textFieldColors = OutlinedTextFieldDefaults.colors(
//        focusedTextColor = Color.Black,
//        unfocusedTextColor = Color.Black,
//        focusedBorderColor = Color.Black,
//        unfocusedBorderColor = Color.Black,
//        cursorColor = Color.Black
//    )
//
//    Column(
//        modifier = Modifier
//            .padding(padding)
//            .padding(horizontal = 15.dp, vertical = 24.dp),
//        verticalArrangement = Arrangement.spacedBy(32.dp)
//    ) {
//
//        Stepper(currentStep = 1)
//
//        Text(
//            text = stringResource(R.string.identification),
//            fontWeight = FontWeight.Bold,
//            fontSize = 20.sp,
//            color = colorResource(R.color.dark_green)
//        )
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = { Text(text = "Digite seu email") },
//            singleLine = true,
//            label = { Text(text = "Email") },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Email
//            ),
//            colors = textFieldColors
//        )
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = { Text(text = "Digite seu nome") },
//            singleLine = true,
//            label = { Text(text = "Nome completo") },
//            colors = textFieldColors
//        )
//
//        OutlinedTextField(
//            value = phone,
//            onValueChange = { phone = it },
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = { Text(text = "(99) 99999-9999") },
//            singleLine = true,
//            label = { Text(text = "Telefone") },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Phone
//            ),
//            colors = textFieldColors
//        )
//
//        CheckoutMainButton(
//            {
//                onButtonClick(
//                    email,
//                    name,
//                    phone
//                )
//            }
//        )
//    }
//}