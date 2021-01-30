package com.pedrofr.wtest.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class ArticleListViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    //TODO revise
    fun fetchArticlesPaginated(): Flow<PagingData<DbArticle>> {
        return repository.fetchArticlesPaginated().cachedIn(viewModelScope)
    }


}