package com.pedrofr.wtest.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.domain.repository.Repository
import kotlinx.coroutines.launch

class ArticleDetailViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _articleDetail = MutableLiveData<DbArticle>()
    fun getArticleDetail(): LiveData<DbArticle> = _articleDetail

    fun fetchArticleDetail(articleId: String){
        viewModelScope.launch {
            val article = repository.fetchArticle(articleId)
            _articleDetail.postValue(article)
        }
    }
}