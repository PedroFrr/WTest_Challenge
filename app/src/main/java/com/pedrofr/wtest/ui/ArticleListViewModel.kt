package com.pedrofr.wtest.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.wtest.core.Result
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.domain.repository.Repository
import kotlinx.coroutines.launch

class ArticleListViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _articles = MutableLiveData<Result<List<DbArticle>>>()
    fun fetchArticles(): LiveData<Result<List<DbArticle>>> = _articles

    init {
        viewModelScope.launch {
            val articles = repository.fetchArticles()
            _articles.postValue(articles)
        }
    }

}