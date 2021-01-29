package com.pedrofr.wtest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pedrofr.wtest.R
import com.pedrofr.wtest.databinding.FragmentArticleDetailBinding
import com.pedrofr.wtest.databinding.FragmentPostCodeListBinding
import com.pedrofr.wtest.util.viewBinding


class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    private val binding by viewBinding(FragmentArticleDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservables()
    }

    private fun initUi(){

    }

    private fun initObservables(){
        
    }


}