package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pedrofr.wtest.R
import com.pedrofr.wtest.databinding.FragmentPostCodeListBinding
import com.pedrofr.wtest.util.viewBinding

class PostcodeListFragment : Fragment(R.layout.fragment_post_code_list) {

    private val binding by viewBinding(FragmentPostCodeListBinding::bind)
    private val postcodeListViewModel by viewModels<PostcodeListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservables()
    }

    private fun initUi(){

        /*KTX Core addTextChangedListener
        * Once search changes (and after debounce period) display new data
         */
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            postcodeListViewModel.onSearchQuery(text.toString())
        }

    }

    private fun initObservables(){

    }

}