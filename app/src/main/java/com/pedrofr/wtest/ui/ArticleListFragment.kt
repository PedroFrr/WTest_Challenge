package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrofr.wtest.R
import com.pedrofr.wtest.core.Failure
import com.pedrofr.wtest.core.Loading
import com.pedrofr.wtest.core.Result
import com.pedrofr.wtest.core.Success
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.databinding.FragmentArticleListBinding
import com.pedrofr.wtest.util.gone
import com.pedrofr.wtest.util.viewBinding
import com.pedrofr.wtest.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleListFragment : Fragment(R.layout.fragment_article_list) {

    private val binding by viewBinding(FragmentArticleListBinding::bind)
    private val articleAdapter by lazy { ArticleListAdapter(::navigateToArticleDetail) }
    private val articlesListPaginatedAdapter by lazy { ArticlesListPaginatedAdapter() }
    private val articleListViewModel by viewModels<ArticleListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservables()

    }

    private fun initUi() {
        binding.articleRecyclerView.apply {
            adapter = articlesListPaginatedAdapter
            adapter = articlesListPaginatedAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter { articlesListPaginatedAdapter.retry() },
                footer = LoadingAdapter { articlesListPaginatedAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            hasFixedSize()
        }

    }

    private fun initObservables() {
        articleListViewModel.fetchArticles()
            .observe(viewLifecycleOwner, Observer<Result<List<DbArticle>>> { result ->

//            when(result){
//                is Success -> {
//                    binding.loadingProgressBar.gone()
//                    binding.errorGroup.gone()
//                    binding.articleRecyclerView.visible()
//
//                    articleAdapter.submitList(result.data)
//
//                }
//                is Failure -> {
//                    binding.loadingProgressBar.gone()
//                    binding.errorGroup.visible()
//                    binding.articleRecyclerView.gone()
//
//                    //TODO add error message
//                }
//                Loading -> {
//                    binding.loadingProgressBar.visible()
//                    binding.errorGroup.gone()
//                    binding.articleRecyclerView.gone()
//                }
//            }


            })

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