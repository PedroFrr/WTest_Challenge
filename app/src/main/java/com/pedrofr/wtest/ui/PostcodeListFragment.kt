package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrofr.wtest.R
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.databinding.FragmentPostCodeListBinding
import com.pedrofr.wtest.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostcodeListFragment : Fragment(R.layout.fragment_post_code_list) {

    private val binding by viewBinding(FragmentPostCodeListBinding::bind)
    private val postcodeListViewModel by viewModels<PostcodeListViewModel>()
    private val postcodesAdapter by lazy { PostCodeListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservables()
    }

    private fun initUi() {

        binding.postcodeRecyclerView.apply {
            adapter = postcodesAdapter
            adapter = postcodesAdapter.withLoadStateHeaderAndFooter(
                header = PostcodeListLoadingAdapter { postcodesAdapter.retry() },
                footer = PostcodeListLoadingAdapter { postcodesAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
                stackFromEnd = true
            }


            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            hasFixedSize()
        }

        /*KTX Core addTextChangedListener
        * Once search changes (and after debounce period) display new data
         */
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            postcodeListViewModel.onSearchQuery(text.toString())
        }

    }

    private fun initObservables() {
        //TODO see if I can change this to KTX (just have to declare the type)
        postcodeListViewModel.fetchPostcodes()
            .observe(viewLifecycleOwner, Observer<PagingData<DbPostcode>> {
                lifecycleScope.launch {
                    postcodesAdapter.submitData(it)
                }
            })

    }

}