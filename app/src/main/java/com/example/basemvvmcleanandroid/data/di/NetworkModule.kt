package com.aramex.mypos.Data.DI



import com.aramex.mypos.Data.Interceptores.AuthenticationInterceptor
import com.aramex.mypos.Data.Interceptores.LoggingInterceptor
import com.aramex.mypos.Data.NetworkChecker.NetworkStatusInterceptor
import com.aramex.mypos.Data.remote.APIConstants.BASE_URL_DEV
import com.aramex.mypos.Data.remote.ApiServices
import com.aramex.mypos.Data.remote.DataWrapper.OffsetDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{


    @Provides
    @Singleton
    fun provideRegistrationApi(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_DEV)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    @Provides
    fun provideMoshi() : Moshi {
        return Moshi.Builder()
//            .add(MoshiArrayListJsonAdapter.FACTORY)
            .add(KotlinJsonAdapterFactory())
//            .add(GsonConverterFactory.create())
            .add(OffsetDateTimeAdapter())
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .serializeNulls()  // Ensures null values are handled
            .create()
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor,
        authenticationInterceptor: AuthenticationInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(authenticationInterceptor)
            .addInterceptor(httpLoggingInterceptor)
//            .connectTimeout(40, TimeUnit.SECONDS)  // Set the connect timeout to 40 seconds
//            .readTimeout(40, TimeUnit.SECONDS)     // Set the read timeout to 40 seconds
//            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }
}