package com.example.careiroapp.data.network.retrofit

import com.example.careiroapp.BuildConfig
import com.example.careiroapp.data.network.api.AssociacaoApiService
import com.example.careiroapp.data.network.api.AuthApiService
import com.example.careiroapp.data.network.api.ClienteApiService
import com.example.careiroapp.data.network.api.FeiraApiService
import com.example.careiroapp.data.network.api.PedidoApiService
import com.example.careiroapp.data.network.api.ProdutoApiService
import com.example.careiroapp.data.network.api.RefreshApiTokenService
import com.example.careiroapp.data.network.api.VendedorApiService
import com.example.careiroapp.data.network.authenticator.AuthAuthenticator
import com.example.careiroapp.data.network.interceptor.AccessTokenInterceptor
import com.example.careiroapp.data.network.interceptor.RefreshTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenRefreshClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PublicClient

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    @AuthenticatedClient
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @TokenRefreshClient
    fun provideRefreshOkHttpClient(
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(refreshTokenInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @PublicClient
    fun provideUnauthenticatedOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun produtoApiSetupProvider(@PublicClient okHttpClient: OkHttpClient): ProdutoApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ProdutoApiService::class.java)
    }

    @Provides
    @Singleton
    fun vendedorApiSetupProvider(@PublicClient okHttpClient: OkHttpClient): VendedorApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(VendedorApiService::class.java)
    }

    @Provides
    @Singleton
    fun feiraApiSetupProvider(@PublicClient okHttpClient: OkHttpClient): FeiraApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FeiraApiService::class.java)
    }

    @Provides
    @Singleton
    fun associacaoApiSetupProvider(@PublicClient okHttpClient: OkHttpClient): AssociacaoApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AssociacaoApiService::class.java)
    }

    @Provides
    @Singleton
    fun pedidoApiServiceProvider(@AuthenticatedClient okHttpClient: OkHttpClient): PedidoApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PedidoApiService::class.java)
    }

    @Provides
    @Singleton
    fun clienteApiSetupProvider(@AuthenticatedClient okHttpClient: OkHttpClient): ClienteApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ClienteApiService::class.java)
    }

    @Provides
    @Singleton
    fun authApiSetupProvider(@PublicClient okHttpClient: OkHttpClient): AuthApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun refreshTokenApiSetupProvider(@TokenRefreshClient okHttpClient: OkHttpClient): RefreshApiTokenService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RefreshApiTokenService::class.java)
    }

}