package com.pedrofr.wtest.featureui.articles

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrofr.wtest.R
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.databinding.FragmentArticleDetailBinding
import com.pedrofr.wtest.ui.ArticleDetailViewModel
import com.pedrofr.wtest.ui.LoadingAdapter
import com.pedrofr.wtest.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    private val binding by viewBinding(FragmentArticleDetailBinding::bind)
    private val articleDetailViewModel by viewModels<ArticleDetailViewModel>()
    private val articleCommentsPaginatedAdapter by lazy { ArticleCommentsPaginatedAdapter() }
    private var articleId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservables()
    }

    private fun initUi(){
        arguments?.let {
            val args =
                ArticleDetailFragmentArgs.fromBundle(
                    it
                )
            articleId = args.articleId
            articleDetailViewModel.fetchArticleDetail(articleId)

        }

        binding.commentRecyclerView.apply {

            adapter = articleCommentsPaginatedAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter { articleCommentsPaginatedAdapter.retry() },
                footer = LoadingAdapter { articleCommentsPaginatedAdapter.retry() }
            )

            articleCommentsPaginatedAdapter.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds.
                isVisible = loadState.source .refresh is LoadState.NotLoading

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

    private fun initObservables(){
        articleDetailViewModel.getArticleDetail()
            .observe(viewLifecycleOwner, Observer<DbArticle> { article ->

                binding.apply {
                    articleHero.loadImage(article.hero, R.drawable.ic_baseline_article_24)
                    articleAuthor.text = article.author
                    articleTitle.text = article.title
                    articlePublishedAt.text = article.publishedAt
                    articleBody.text = article.body
                    articleAuthorAvatar.loadCircleImage(article.author, R.drawable.ic_baseline_supervised_user_circle_24)
                    textHeader.visible()
                }

            })


        lifecycleScope.launch {
            articleDetailViewModel.fetchCommentsPaginated(articleId).collectLatest { pagingData ->
                articleCommentsPaginatedAdapter.submitData(pagingData)
            }
        }
    }


}