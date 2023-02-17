package com.kevinserrano.supermeli.searchbycategory.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.databinding.ActivitySearchByCategoryBinding
import com.kevinserrano.supermeli.detail.ui.ArticleDetailActivity
import com.kevinserrano.supermeli.detail.ui.IS_MELI_CHOICE_CANDIDATE_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.SELLER_NAME_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.SELLER_lINK_KEY_PARAM
import com.kevinserrano.supermeli.home.ui.CATEGORY_ID_KEY_PARAM
import com.kevinserrano.supermeli.home.ui.CATEGORY_TITLE_KEY_PARAM
import com.kevinserrano.supermeli.lib.ui.error.ErrorFragment
import com.kevinserrano.supermeli.lib.ui.loading.LoadingFragment
import com.kevinserrano.supermeli.search.model.Article
import com.kevinserrano.supermeli.search.ui.ArticleResultsAdapter
import com.kevinserrano.supermeli.utils.removeFragment
import com.kevinserrano.supermeli.utils.showFragment
import com.kevinserrano.supermeli.utils.toHtml
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SearchByCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchByCategoryBinding
    private val viewModel: SearchByCategoryViewModel by viewModels()
    private var articleResultsAdapter by Delegates.notNull<ArticleResultsAdapter>()
    private val title: String by lazy {
        intent.getStringExtra(CATEGORY_TITLE_KEY_PARAM) ?: ""
    }
    private val categoryId: String by lazy {
        intent.getStringExtra(CATEGORY_ID_KEY_PARAM) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
        handleEvents()
        handleStates()
        viewModel.searchArticlesByCategory(categoryId = categoryId)
    }

    private fun setUpViews() {
        articleResultsAdapter = ArticleResultsAdapter(this::onArticleClick)
        binding.rvResults.adapter = articleResultsAdapter
        binding.tvTitle.text = getString(R.string.for_category, title).toHtml()
    }

    private fun onArticleClick(article: Article) {
        viewModel.onArticleItemClicked(article = article)
    }

    private fun handleEvents() {
        viewModel.event.observe(this) { event ->
            if (event is SearchByCategoryViewModel.Event.NavigationToArticleDetail) {
                startActivity(
                    Intent(
                        this,
                        ArticleDetailActivity::class.java
                    ).apply {
                        putExtra(ARTICLE_ID_KEY_PARAM, event.articleId)
                        putExtra(IS_MELI_CHOICE_CANDIDATE_KEY_PARAM, event.isMeliChoiceCandidate)
                        putExtra(SELLER_NAME_KEY_PARAM, event.sellerName)
                        putExtra(SELLER_lINK_KEY_PARAM, event.sellerLink)
                    })
                viewModel.eventNone()
            }
        }
    }

    private fun handleStates() {
        viewModel.state.observe(this) { state ->
            when {
                state.loading -> {
                    supportFragmentManager.showFragment(
                        containerViewId = R.id.containerFragment,
                        fragment = LoadingFragment(), LoadingFragment::class.java.name
                    )
                }
                state.error -> {
                    articleResultsAdapter.clean()
                    supportFragmentManager.showFragment(
                        containerViewId = R.id.containerFragment,
                        fragment = ErrorFragment().apply {
                            onRetry = {
                                viewModel.searchArticlesByCategory(categoryId = categoryId)
                            }
                        }, ErrorFragment::class.java.name
                    )
                }
                !state.articles.isNullOrEmpty() -> {
                    supportFragmentManager.removeFragment()
                    articleResultsAdapter.setArticle(state.articles)
                }
                state.articles.isNullOrEmpty() -> {
                    supportFragmentManager.showFragment(
                        containerViewId = R.id.containerFragment,
                        fragment = ErrorFragment(), ErrorFragment::class.java.name
                    )
                }
            }
        }
    }
}

const val ARTICLE_ID_KEY_PARAM = "article_id"
