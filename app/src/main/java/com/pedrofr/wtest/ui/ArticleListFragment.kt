package com.pedrofr.wtest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrofr.wtest.R
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.databinding.FragmentArticleListBinding
import com.pedrofr.wtest.util.viewBinding

class ArticleListFragment : Fragment(R.layout.fragment_article_list) {

    private val binding by viewBinding(FragmentArticleListBinding::bind)
    private val articleAdapter by lazy { ArticleListAdapter(::navigateToArticleDetail)}

    private val articles = listOf<DbArticle>(
        DbArticle(
            title = "Título",
            author = "Autor",
            summary = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,"
        ),
        DbArticle(
            title = "Título",
            author = "Autor",
            summary = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,"
        ),
        DbArticle(
            title = "Título",
            author = "Autor",
            summary = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,"
        ),
        DbArticle(
            title = "Título",
            author = "Autor",
            summary = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,"
        ),
        DbArticle(
            title = "Título",
            author = "Autor",
            summary = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,"
        ),
        DbArticle(
            title = "Título",
            author = "Autor",
            summary = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,"
        ),DbArticle(
            title = "Título",
            author = "Autor",
            summary = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,"
        ),

    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservables()
    }

    private fun initUi(){
        binding.articleRecyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            hasFixedSize()
        }

        articleAdapter.submitList(articles)
    }

    private fun initObservables(){

    }

    private fun navigateToArticleDetail(view: View, articleId: String){
        val direction = ArticleListFragmentDirections.articleListToDetail(articleId)
        view.findNavController().navigate(direction)
    }
}