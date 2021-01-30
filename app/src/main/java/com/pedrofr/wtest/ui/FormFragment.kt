package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.pedrofr.wtest.R
import com.pedrofr.wtest.databinding.FragmentFormBinding
import com.pedrofr.wtest.util.isDateValid
import com.pedrofr.wtest.util.isEmailValid
import com.pedrofr.wtest.util.isHifenAndCharactersValid
import com.pedrofr.wtest.util.viewBinding


class FormFragment : Fragment(R.layout.fragment_form) {

    private val binding by viewBinding(FragmentFormBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

    }

    //TODO change all this strings to resources to support internationalization
    private fun initUi(){

        setDefaultEmptyError()

        binding.freeTextEditText.doOnTextChanged { text, _, _, _ ->
            text ?: return@doOnTextChanged
            if (text.isNotBlank()) {
                binding.freeTextInputLayout.error = null
            }else{
                binding.freeTextInputLayout.error = getString(R.string.empty_error)
            }
        }

        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            text ?: return@doOnTextChanged
            if (text.toString().isEmailValid()) {
                binding.emailInputLayout.error = null
            }else{
                binding.emailInputLayout.error = getString(R.string.email_invalid)
            }
        }

        binding.numbersEditText.doOnTextChanged { text, _, _, _ ->
            text ?: return@doOnTextChanged
            if (text.isNotBlank()) {
                binding.numbersInputLayout.error = null
            }else{
                binding.numbersInputLayout.error = getString(R.string.empty_error)
            }
        }

        binding.lettersAndNumbersEditText.doOnTextChanged { text, _, _, _ ->
            text ?: return@doOnTextChanged
            if (text.toString().isHifenAndCharactersValid()) {
                binding.lettersAndNumbersInputLayout.error = null
            }else{
                binding.lettersAndNumbersInputLayout.error = getString(R.string.letters_numbers_error)
            }
        }

        binding.dateEditText.doOnTextChanged { text, _, _, _ ->
            text ?: return@doOnTextChanged
            if (text.toString().isDateValid()) {
                binding.dateInputLayout.error = null
            }else{
                binding.dateInputLayout.error = getString(R.string.date_error)
            }
        }
    }

    private fun setDefaultEmptyError(){
        binding.apply {
            freeTextInputLayout.error = getString(R.string.empty_error)
            emailInputLayout.error = getString(R.string.empty_error)
            numbersInputLayout.error = getString(R.string.empty_error)
            dateInputLayout.error = getString(R.string.empty_error)
            lettersAndNumbersInputLayout.error = getString(R.string.empty_error)

        }
    }


}