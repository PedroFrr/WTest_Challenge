package com.pedrofr.wtest.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.wtest.domain.repository.Repository
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class FormViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _validPostalDesignation = MutableLiveData<String>()
    fun isPostcodeValid(): LiveData<String> = _validPostalDesignation

    fun fetchData(postcode: String) {
        val isPostcodeFormatValid = Pattern.compile("[0-9]{4}(-)[0-9]{3}").matcher(postcode).matches()

        if(isPostcodeFormatValid){
            viewModelScope.launch {
                val postcodeNumber = postcode.substringBefore("-")
                val postcodeExtension = postcode.substringAfter("-")
                val postcode = repository.fetchValidPostcode(postcodeNumber, postcodeExtension)
                _validPostalDesignation.value = postcode?.postalDesignation ?: ""
            }
        }else{
            _validPostalDesignation.value = ""
        }

    }





}