package com.truecallerassignment.domain.repository

interface GetDataRepository {

    suspend fun getData(): String

}