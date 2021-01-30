package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrofr.wtest.R
import com.pedrofr.wtest.databinding.FragmentArticleListBinding
import com.pedrofr.wtest.featureui.ArticlesListPaginatedAdapter
import com.pedrofr.wtest.util.gone
import com.pedrofr.wtest.util.toast
import com.pedrofr.wtest.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleListFragment : Fragment(R.layout.fragment_article_list) {

    private val binding by viewBinding(FragmentArticleListBinding::bind)
    private val articlesListPaginatedAdapter by lazy { ArticlesListPaginatedAdapter() }
    private val articleListViewModel by viewModels<ArticleListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservables()

    }

    private fun initUi() {
        binding.articleRecyclerView.apply {

            adapter = articlesListPaginatedAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter { articlesListPaginatedAdapter.retry() },
                footer = LoadingAdapter { articlesListPaginatedAdapter.retry() }
            )

            articlesListPaginatedAdapter.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds.
                binding.articleRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
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

    }

    private fun initObservables() {
        lifecycleScope.launch {
            articleListViewModel.fetchArticlesPaginated().collectLatest { pagingData ->
                binding.loadingProgressBar.gone()
                articlesListPaginatedAdapter.submitData(pagingData)
            }
        }

    }


    private fun navigateToArticleDetail(view: View, articleId: String) {
        val direction = ArticleListFragmentDirections.articleListToDetail(articleId)
        view.findNavController().navigate(direction)
    }
}