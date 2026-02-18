package com.example.careiroapp.feiras.data.datasource

import com.example.careiroapp.feiras.data.models.FeiraModel
import com.example.careiroapp.data.network.api.FeiraApiService
import retrofit2.Response
import javax.inject.Inject

class FeirasDataSource @Inject constructor(
    private val feiraApiService: FeiraApiService
) {

    suspend fun getFeiras(): Response<MutableList<FeiraModel>> {
        return feiraApiService.getFeiras()
    }

    suspend fun getFeiraById(id: Int): FeiraModel? {
        return feiraApiService.getFeiraById(id).body()
    }


}