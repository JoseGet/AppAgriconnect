package com.example.careiroapp.mocks

import com.example.careiroapp.R
import com.example.careiroapp.associacoes.data.models.AssociacaoModel
import com.example.careiroapp.associacoes.data.models.AssociacaoProductorModel
import com.example.careiroapp.products.data.models.ProductModel
import com.example.careiroapp.feiras.data.models.FeiraModel
import java.util.UUID

object Mocks {

//    val productsCardList: List<ProductModel> = listOf(
//        ProductModel(
//            id = ,
//            image = R.drawable.abobora,
//            nomeProduto = "Abóbora",
//            precoProduto = 10.0f,
//            isPromocao = true,
//            precoPromocao = 8.0
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Tomate",
//            precoProduto = 6.5f,
//            isPromocao = false,
//            precoPromocao = 0.0
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Maçã",
//            precoProduto = 12.0f,
//            isPromocao = true,
//            precoPromocao = 9.5
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Laranja",
//            precoProduto = 8.0f,
//            isPromocao = false,
//            precoPromocao = 0.0
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Melancia",
//            precoProduto = 20.0f,
//            isPromocao = true,
//            precoPromocao = 15.0
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Abóbora",
//            precoProduto = 10.0f,
//            isPromocao = true,
//            precoPromocao = 8.0
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Tomate",
//            precoProduto = 6.5f,
//            isPromocao = false,
//            precoPromocao = 0.0
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Maçã",
//            precoProduto = 12.0f,
//            isPromocao = true,
//            precoPromocao = 9.5
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Laranja",
//            precoProduto = 8.0f,
//            isPromocao = false,
//            precoPromocao = 0.0
//        ),
//        ProductModel(
//            image = R.drawable.abobora,
//            nomeProduto = "Melancia",
//            precoProduto = 20.0f,
//            isPromocao = true,
//            precoPromocao = 15.0
//        )
//    )

    val associacoesCardList = listOf<AssociacaoModel>(
        AssociacaoModel(
            idAssociacao = UUID.randomUUID(),
            nome = "Associação dos Agricultores",
            image = "",
            descricao = "",
            dataHora = null,
            endereco = null,
            productorsList = emptyList<AssociacaoProductorModel>() as MutableList<AssociacaoProductorModel>
        ),
        AssociacaoModel(
            idAssociacao = UUID.randomUUID(),
            nome = "Cooperativa de Pescadores",
            image = "",
            descricao = "",
            dataHora = null,
            endereco = null,
            productorsList = emptyList<AssociacaoProductorModel>() as MutableList<AssociacaoProductorModel>
        ),
        AssociacaoModel(
            idAssociacao = UUID.randomUUID(),
            nome = "Associação dos Artesãos",
            image = "",
            descricao = "",
            dataHora = null,
            endereco = null,
            productorsList = emptyList<AssociacaoProductorModel>() as MutableList<AssociacaoProductorModel>
        ),
        AssociacaoModel(
            idAssociacao = UUID.randomUUID(),
            nome = "Associação Comunitária",
            image = "",
            descricao = "",
            dataHora = null,
            endereco = null,
            productorsList = emptyList<AssociacaoProductorModel>() as MutableList<AssociacaoProductorModel>
        )
    )

    val feirasCardList: List<FeiraModel> = listOf(
        FeiraModel(
            id = 0,
            nome = "Associação dos Agricultores",
            dataHora = "",
            descricao = "",
            localizacao = "",
            image = ""
        ),
        FeiraModel(
            id = 1,
            nome = "Cooperativa de Pescadores",
            dataHora = "",
            descricao = "",
            localizacao = "",
            image = ""
        ),
        FeiraModel(
            id = 1,
            image = "",
            nome = "Associação dos Artesãos",
            dataHora = "",
            localizacao = "",
            descricao = ""
        ),
        FeiraModel(
            id = 2,
            image = "",
            nome = "Associação Comunitária",
            dataHora = "",
            localizacao = "",
            descricao = ""
        )
    )

    val singleProduct = ProductModel(
        id = UUID.randomUUID(),
        nomeProduto = "Shampoo Revitalizante",
        descricao = "Shampoo indicado para cabelos secos, promove hidratação intensa.",
        image = "https://picsum.photos/200/300?random=1",
        unidadeMedida = "kg",
        disponivel = true,
        precoProduto = 29.90f,
        isPromocao = true,
        precoPromocao = 19.90,
        fkVendedor = UUID.randomUUID()
    )
}

