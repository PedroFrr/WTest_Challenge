package com.pedrofr.wtest.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pedrofr.wtest.core.Result
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.network.ArticlePagingSource
import com.pedrofr.wtest.data.network.response.ArticleResponse
import com.pedrofr.wtest.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ArticleListViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    //TODO revise
    fun fetchArticlesPaginated(): Flow<PagingData<DbArticle>> {
        return repository.fetchArticlesPaginated().cachedIn(viewModelScope)
    }


}