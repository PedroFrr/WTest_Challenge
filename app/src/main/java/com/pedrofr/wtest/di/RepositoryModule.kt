package com.pedrofr.wtest.di

import com.pedrofr.wtest.data.RepositoryImpl
import com.pedrofr.wtest.data.network.mapper.ApiMapper
import com.pedrofr.wtest.data.network.mapper.ApiMapperImpl
import com.pedrofr.wtest.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        impl: RepositoryImpl
    ) : Repository

    @Binds
    abstract fun providesApiMapper(
        impl: ApiMapperImpl
    ) : ApiMapper

}