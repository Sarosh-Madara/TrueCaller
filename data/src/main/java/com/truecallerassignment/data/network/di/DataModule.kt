package com.truecallerassignment.data.network.di

import com.truecallerassignment.data.repository.GetDataRepositoryImpl
import com.truecallerassignment.domain.repository.GetDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideGetDataRepository(): GetDataRepository {
        return GetDataRepositoryImpl()
    }


}