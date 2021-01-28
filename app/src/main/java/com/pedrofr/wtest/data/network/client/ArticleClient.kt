package com.pedrofr.wtest.data.network.client

import javax.inject.Inject

class ArticleClient @Inject constructor(
    private val articleService: ArticleService
) {

    //TODO replace with concrete call to service. Handle Success, Failure...

}

