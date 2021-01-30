package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.pedrofr.wtest.R
import com.pedrofr.wtest.databinding.FragmentFormBinding
import com.pedrofr.wtest.util.*
import java.util.regex.Pattern


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

        binding.optionEditText.doOnTextChanged { text, _, _, _ ->
            text ?: return@doOnTextChanged
            if (text.toString().isValidOption()) {
                binding.optionInputLayout.error = null
            }else{
                binding.optionInputLayout.error = getString(R.string.options_error)
            }
        }

        //TODO validate postcode
        binding.postcodeEditText.doOnTextChanged { text, _, _, _ ->
            text ?: return@doOnTextChanged
            if (text.toString().isValidOption()) {
                binding.postcodeInputLayout.error = null
            }else{
                binding.postcodeInputLayout.error = getString(R.string.options_error)
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
            optionInputLayout.error = getString(R.string.empty_error)
            postcodeInputLayout.error = getString(R.string.empty_error)
        }
    }

    //TODO improve this regex
    private fun String.isHifenAndCharactersValid(): Boolean {
        val rx1 = Pattern.compile("[A-Z]{6}(-)[A-Z]")
        val rx2 = Pattern.compile("[A-Z]{5}(-)[A-Z]{1,2}")
        val rx3 = Pattern.compile("[A-Z]{4}(-)[A-Z]{1,3}")
        val rx4 = Pattern.compile("[A-Z]{3}(-)[A-Z]{1,4}")
        val rx5 = Pattern.compile("[A-Z]{2}(-)[A-Z]{1,5}")
        val rx6 = Pattern.compile("[A-Z](-)[A-Z]{1,6}")

        return rx1.matcher(this).matches() ||
                rx2.matcher(this).matches() ||
                rx3.matcher(this).matches() ||
                rx4.matcher(this).matches() ||
                rx5.matcher(this).matches() ||
                rx6.matcher(this).matches()

    }

    private fun String.isValidOption() =
        this == "Mau"
                || this == "Satisfatório"
                || this == "Bom"
                || this == "Muito Bom"
                || this == "Excelente"


}