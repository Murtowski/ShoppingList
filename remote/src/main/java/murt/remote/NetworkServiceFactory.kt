package murt.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Piotr Murtowski on 20.02.2018.
 */
class NetworkServiceFactory{
    private val connectTimeout = 20000
    private val readTimeout = 20000
    private val writeTimeout = 20000


    fun getNetworkService(isDebug: Boolean): Retrofit {
        return retrofit2.Retrofit.Builder()
            .client(getDefaultHttpClient(isDebug))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(provideRESTServerUrl())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

    }

    private fun provideRESTServerUrl(): String {
        return "endpoint-api"
    }

    private fun getDefaultHttpClient(isDebug: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createOAuthTokenInterceptor())
            .addInterceptor(createLoginInterceptor(isDebug))
            .connectTimeout(connectTimeout.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(readTimeout.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(writeTimeout.toLong(), TimeUnit.MILLISECONDS)
            //                .addNetworkInterceptor(new StethoInterceptor())
            .build()
    }

    private fun createOAuthTokenInterceptor(): Interceptor {
        return Interceptor{ chain ->
            val token = "" // e.g sharPref roken
            val original = chain.request()
            val builder = original.newBuilder()
            if (token.isNotBlank()) {
                builder.addHeader("Authorization", token)
            }
            chain.proceed(builder.build())
        }
    }

    private fun createLoginInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }


}