package com.pedrofr.wtest.di

import com.pedrofr.wtest.data.network.client.ArticleClient
import com.pedrofr.wtest.data.network.client.ArticleService
import com.pedrofr.wtest.featureutil.ARTICLES_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {


    //TODO Add Authorization Interceptor if Token is needed for API

    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    fun buildArticleRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(ARTICLES_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleService(articleRetrofit: Retrofit) = articleRetrofit.create(ArticleService::class.java)

    @Provides
    @Singleton
    fun provideArticleClient(articleService: ArticleService) = ArticleClient(articleService)

}