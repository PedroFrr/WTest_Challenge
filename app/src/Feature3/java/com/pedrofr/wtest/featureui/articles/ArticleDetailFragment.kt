package com.pedrofr.wtest.featureui.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pedrofr.wtest.R
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.databinding.FragmentArticleDetailBinding
import com.pedrofr.wtest.featureui.articles.ArticleDetailFragmentArgs
import com.pedrofr.wtest.util.loadImage
import com.pedrofr.wtest.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    private val binding by viewBinding(FragmentArticleDetailBinding::bind)
    private val articleDetailViewModel by viewModels<ArticleDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservables()
    }

    private fun initUi(){
        arguments?.let {
            val args = ArticleDetailFragmentArgs.fromBundle(it)
            val articleId = args.articleId
            articleDetailViewModel.fetchArticleDetail(articleId)

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
                    binding.articleBody.text = article.body
                }


            })
    }


}