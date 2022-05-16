package com.truecallerassignment.data.repository

import com.truecallerassignment.common.Constant
import com.truecallerassignment.domain.repository.GetDataRepository
import it.skrape.core.document
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import javax.inject.Inject

class GetDataRepositoryImpl : GetDataRepository{

    override suspend fun getData(): String {
        val contentResponse = skrape(AsyncFetcher) {
            request {
                url = Constant.url
                timeout = Constant.requestTimeOut
                followRedirects = true
                sslRelaxed = true
            }
            response {
                document.text
            }
        }
        return contentResponse
    }
}