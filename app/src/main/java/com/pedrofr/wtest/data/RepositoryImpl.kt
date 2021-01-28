package com.pedrofr.wtest.data

import com.pedrofr.wtest.data.network.mapper.ApiMapper
import com.pedrofr.wtest.data.db.dao.PostcodeDao
import com.pedrofr.wtest.data.network.client.ArticleClient
import com.pedrofr.wtest.domain.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class RepositoryImpl @Inject constructor(
    private val apiMapper: ApiMapper,
    private val articleClient : ArticleClient,
    private val postcodeDao: PostcodeDao,
) : Repository {

    //TODO add calls. save first on db ...

}
