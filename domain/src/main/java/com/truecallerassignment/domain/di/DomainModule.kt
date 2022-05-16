package com.truecallerassignment.domain.di

import com.truecallerassignment.domain.repository.GetDataRepository
import com.truecallerassignment.domain.use_cases.GetDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun provideGetDataUseCase(getDataRepository: GetDataRepository): GetDataUseCase{
        return GetDataUseCase(getDataRepository)
    }

}