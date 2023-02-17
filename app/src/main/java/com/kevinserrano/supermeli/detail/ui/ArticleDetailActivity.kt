package com.kevinserrano.supermeli.detail.ui

import android.graphics.Paint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.databinding.ActivityArticleDetailBinding
import com.kevinserrano.supermeli.detail.model.ArticleDetail
import com.kevinserrano.supermeli.lib.ui.error.ErrorFragment
import com.kevinserrano.supermeli.lib.ui.loading.LoadingFragment
import com.kevinserrano.supermeli.utils.openWebPage
import com.kevinserrano.supermeli.utils.removeFragment
import com.kevinserrano.supermeli.utils.showFragment
import com.kevinserrano.supermeli.utils.toHtml
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding
    private val viewModel: ArticleDetailViewModel by viewModels()
    private val articleId: String by lazy {
        intent.getStringExtra(ARTICLE_ID_KEY_PARAM) ?: ""
    }
    private val isMeliChoiceCandidate: Boolean by lazy {
        intent.getBooleanExtra(IS_MELI_CHOICE_CANDIDATE_KEY_PARAM, false)
    }
    private val sellerName: String by lazy {
        intent.getStringExtra(SELLER_NAME_KEY_PARAM) ?: ""
    }
    private val sellerLink: String by lazy {
        intent.getStringExtra(SELLER_lINK_KEY_PARAM) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleEvents()
        handleStates()
        viewModel.fetchArticleDetails(articleId = articleId)
    }

    private fun setDetailToViews(articleDetail: ArticleDetail) {
        binding.btnBuy.setOnClickListener {
            onBuy()
        }
        binding.btnBuy.visibility = View.VISIBLE
        binding.containerArticleDetailTitleInfo.lbShipping.visibility =
            if (articleDetail.isFreeShipping) View.VISIBLE else View.GONE
        binding.containerArticleDetailTitleInfo.lbRecommended.visibility =
            if (isMeliChoiceCandidate) View.VISIBLE else View.GONE
        binding.containerArticleDetailTitleInfo.tvTitle.text = articleDetail.title
        binding.containerArticleDetailTitleInfo.tvPrice.text = articleDetail.price
        if (articleDetail.originalPrice != PRICE_ZERO) {
            with(binding.containerArticleDetailTitleInfo.tvOriginalPrice) {
                visibility = View.VISIBLE
                text = articleDetail.originalPrice
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
        binding.containerArticleDetailTitleInfo.imgArticle.showPicture(picture = articleDetail.pictures.first())
        if (articleDetail.description.isNotEmpty()) {
            binding.containerArticleDetails.lbDescription.visibility = View.VISIBLE
            with(binding.containerArticleDetails.tvDescription) {
                text = articleDetail.description
            }
        }
        binding.containerArticleDetails.tvCharacteristic.text =
            articleDetail.characteristic.toHtml()
        with(binding.containerArticleDetails.tvSeller) {
            text = getString(R.string.seller, sellerLink, sellerName).toHtml()
            movementMethod = LinkMovementMethod.getInstance()
        }
        binding.containerFragment.visibility = View.GONE
    }

    private fun ImageView.showPicture(picture: String) {
        Glide.with(this)
            .load(picture)
            .into(this)
    }

    private fun onBuy() {
        viewModel.onBuy()
    }

    private fun handleEvents() {
        viewModel.event.observe(this) { event ->
            if (event is ArticleDetailViewModel.Event.NavigationToBuy) {
                openWebPage(event.articleLink)
                viewModel.eventNone()
            }
        }
    }

    private fun handleStates() {
        viewModel.state.observe(this) { state ->
            when {
                state.loading -> {
                    binding.containerFragment.visibility = View.VISIBLE
                    supportFragmentManager.showFragment(
                        containerViewId = R.id.containerFragment,
                        fragment = LoadingFragment(), LoadingFragment::class.java.name
                    )
                }
                state.error -> {
                    binding.containerFragment.visibility = View.VISIBLE
                    supportFragmentManager.showFragment(
                        containerViewId = R.id.containerFragment,
                        fragment = ErrorFragment().apply {
                            onRetry = {
                                viewModel.fetchArticleDetails(articleId = articleId)
                            }
                        }, ErrorFragment::class.java.name
                    )
                }
                else -> {
                    if (state.articleDetail != null) {
                        supportFragmentManager.removeFragment()
                        setDetailToViews(articleDetail = state.articleDetail)
                        viewModel.goSaveArticleViewed(
                            articleDetail = state.articleDetail,
                            isMeliChoiceCandidate = isMeliChoiceCandidate,
                            sellerLink = sellerLink,
                            sellerName = sellerName
                        )
                    }
                }
            }
        }
    }
}

const val ARTICLE_ID_KEY_PARAM = "article_id"
const val IS_MELI_CHOICE_CANDIDATE_KEY_PARAM = "is_meli_choice_candidate"
const val SELLER_NAME_KEY_PARAM = "seller_name"
const val SELLER_lINK_KEY_PARAM = "seller_link"
const val PRICE_ZERO = "$ 0"
