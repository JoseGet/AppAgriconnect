package com.example.careiroapp.bag.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.careiroapp.R
import com.example.careiroapp.R.color.search_bar_border_color
import com.example.careiroapp.bag.ui.components.BagBottomBar
import com.example.careiroapp.bag.ui.components.BagProductCard
import com.example.careiroapp.bag.ui.components.BagTopBar
import com.example.careiroapp.bag.ui.viewmodel.BagViewModel
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily
import com.example.careiroapp.navigation.NavigationItem
import kotlinx.coroutines.coroutineScope

@Composable
fun BagView(
    navController: NavController
) {
    val viewModel: BagViewModel = hiltViewModel()
    val userData by viewModel.userData.collectAsStateWithLifecycle(initialValue = null)
    val itensNaSacola by viewModel.cartItems.collectAsStateWithLifecycle()
    val total by viewModel.totalPrice.observeAsState(0.0)

    Scaffold(
        topBar = {
            BagTopBar(
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BagBottomBar(
                total = total,
                onCheckout = {
                    navController.navigate(NavigationItem.Checkout.route)
                },
                isEnable = itensNaSacola.isNotEmpty()
            )
        },
        containerColor = colorResource(R.color.light_background)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp, top = 24.dp),
        ) {
            if (itensNaSacola.isNotEmpty()) {
                Text(
                    stringResource(R.string.order_summary),
                    fontFamily = montserratBoldFontFamily,
                    fontSize = 18.sp,
                    color = colorResource(R.color.dark_green)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    itemsIndexed(itensNaSacola) { index, produto ->
                        BagProductCard(
                            name = produto.name,
                            image = produto.imageUrl,
                            price = produto.price,
                            amount = produto.quantity,
                            increaseProduct = {
                                viewModel.addQuantity(produto.productId, cpf = userData?.cpf ?: "")
                            },
                            decreaseProduct = {
                                if (produto.quantity - 1 == 0) {
                                    viewModel.removeProduct(produto)
                                } else {
                                    viewModel.decreaseQuantity(produto.productId, cpf = userData?.cpf ?: "")
                                }
                            },
                        )
                        if (index < itensNaSacola.lastIndex) {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                        }
                    }
                }
            } else {
                EmptyBagColumn()
            }
        }
    }
}

@Composable
fun HorizontalDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                colorResource(search_bar_border_color)
            )
    )
}

@Composable
fun EmptyBagColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .height(190.dp)
                .width(150.dp),
            painter = painterResource(R.drawable.empty_bag_icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.empty_bag_title),
            fontFamily = montserratBoldFontFamily,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.empty_bag_description),
            fontFamily = montserratRegularFontFamily,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview
fun EmptyBagColumnPreview() {
    EmptyBagColumn()
}