package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import com.pedrofr.wtest.R
import com.pedrofr.wtest.data.db.AppDatabase
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.databinding.FragmentPostCodeListBinding
import com.pedrofr.wtest.util.gone
import com.pedrofr.wtest.util.toast
import com.pedrofr.wtest.util.viewBinding
import com.pedrofr.wtest.util.visible
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
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
                header = LoadingAdapter { postcodesAdapter.retry() },
                footer = LoadingAdapter { postcodesAdapter.retry() }
            )

            postcodesAdapter.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds.
                binding.postcodeRecyclerView.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                binding.loadingProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    toast("\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_LONG)
                }
            }

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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
            .observe(viewLifecycleOwner, Observer<PagingData<DbPostcode>> { pagingData ->

                lifecycleScope.launch {
                    if(postcodeListViewModel.fetchData() != null){
                        binding.loadingProgressBar.gone()
                        postcodesAdapter.submitData(pagingData)
                    }else{
                        binding.loadingProgressBar.visible()
                    }
                }

            })


    }

}