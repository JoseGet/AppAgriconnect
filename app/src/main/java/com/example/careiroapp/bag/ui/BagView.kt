package com.example.careiroapp.bag.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.BottomBar
import com.example.careiroapp.bag.ui.components.ProductCard
import com.example.careiroapp.bag.ui.components.TopBar
import com.example.careiroapp.bag.ui.viewmodel.BagViewModel
import com.example.careiroapp.navigation.NavigationItem
@Composable
fun BagView(
    navController: NavController
) {
    val viewModel: BagViewModel = hiltViewModel()
    val itensNaSacola by viewModel.cartItems.collectAsStateWithLifecycle()

//    val produtos = remember {
//        mutableStateListOf(
//            Produto("Banana", 2.0, 8),
//            Produto("Melancia", 4.0, 3),
//            Produto("Abacaxi", 23.0, 4)
//        )
//    }
//
//    fun decreaseProduct(index: Int) {
//        val produto = produtos[index]
//        if (produto.amount > 0) {
//            produtos[index] = produto.copy(amount = produto.amount - 1)
//        }
//    }
//
//    fun increaseProduct(index: Int) {
//        val produto = produtos[index]
//        produtos[index] = produto.copy(amount = produto.amount + 1)
//    }
//
//    val total by remember {
//        derivedStateOf {
//            produtos.sumOf { it.unitPrice * it.amount }
//        }
//    }

    Scaffold(
        topBar = {
            TopBar(Modifier.height(92.dp), { navController.popBackStack() })
        },
        bottomBar = {
            BottomBar(total = 4.0, onCheckout = {navController.navigate(NavigationItem.Checkout.route)})
        },
        containerColor = colorResource(R.color.light_background)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                stringResource(R.string.order_summary),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                itemsIndexed(itensNaSacola) { index, produto ->
                    ProductCard(
                        name = produto.name,
                        image = produto.imageUrl,
                        index = index,
                        price = produto.price,
                        amount = produto.quantity,
                        increaseProduct = {  },
                        decreaseProduct = {  },
                    )
                }
            }
        }
    }
}
