package com.pedrofr.wtest.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.domain.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostcodeListViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val debouncePeriod: Long = 250
    private var searchJob: Job? = null
    private var _searchPostcodeLiveData: LiveData<PagingData<DbPostcode>>
    fun fetchPostcodes() = _searchPostcodeLiveData
    private val _searchFieldTextLiveData = MutableLiveData("")

    init {

        //every time the search field changes we re-execute the query
        _searchPostcodeLiveData = _searchFieldTextLiveData.switchMap {
            val query = it.replace(' ', '*').replace('-',' ')
            fetchPostcode(query)
        }

    }

    fun onSearchQuery(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            _searchFieldTextLiveData.value = query
        }
    }

    private fun fetchPostcode(query: String): LiveData<PagingData<DbPostcode>> {
        val liveData = MutableLiveData<PagingData<DbPostcode>>()
        viewModelScope.launch {
            if (query.isBlank()) {
                repository.fetchPostcodes()
                    .collect {
                        liveData.postValue(it)
                    }

            } else {
                val sanitizedQuery = sanitizeSearchQuery(query)
                repository.fetchPostcodesByQuery(sanitizedQuery).cachedIn(viewModelScope)
                    .collectLatest {
                        liveData.postValue(it)
                    }
            }
        }
        return liveData
    }

    private fun sanitizeSearchQuery(query: String): String {
        val queryWithEscapedQuotes = query.replace(Regex.fromLiteral("\""), "\"\"")
        return queryWithEscapedQuotes
    }

    suspend fun fetchData() = repository.fetchData()

}
