package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.pedrofr.wtest.R
import com.pedrofr.wtest.databinding.FragmentFormBinding
import com.pedrofr.wtest.util.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZonedDateTime
import java.util.*
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
            if (text.toString().isDateFormatValid()) {
                val date = text.toString().getDate("dd/MM/yyyy")
                date?.let {
                    if(it.isFormDateValid()){
                        binding.dateInputLayout.error = null
                    }else{
                        binding.dateInputLayout.error = "Date cannot be a Monday or in the future"
                    }
                }

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

    private fun String.isHifenAndCharactersValid() =  Pattern.compile("[A-Z -]{3,7}").matcher(this).matches()

    private fun String.isValidOption() =
        this == "Mau"
                || this == "Satisfatório"
                || this == "Bom"
                || this == "Muito Bom"
                || this == "Excelente"

    private fun Date.isFormDateValid(): Boolean{
        val c = Calendar.getInstance().apply { time = this@isFormDateValid }
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)

        val isMonday = dayOfWeek == 2

        val isFutureDate = Date().before(this)

        return !isMonday && !isFutureDate

    }


}