package com.truecallerassignment.domain.use_cases

import com.truecallerassignment.common.Resource
import com.truecallerassignment.domain.repository.GetDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDataUseCase  @Inject constructor(private val getDataRepository: GetDataRepository){


     operator fun invoke() : Flow<Resource<String>> = flow {
        val response = getDataRepository.getData()
        emit(Resource.Success(data = response))
    }


}