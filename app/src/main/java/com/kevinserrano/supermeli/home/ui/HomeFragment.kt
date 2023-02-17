package com.kevinserrano.supermeli.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.databinding.FragmentHomeBinding
import com.kevinserrano.supermeli.detail.ui.ArticleDetailActivity
import com.kevinserrano.supermeli.detail.ui.IS_MELI_CHOICE_CANDIDATE_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.SELLER_NAME_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.SELLER_lINK_KEY_PARAM
import com.kevinserrano.supermeli.home.model.ArticleViewed
import com.kevinserrano.supermeli.home.model.CategoryItem
import com.kevinserrano.supermeli.home.ui.adapter.ArticleViewedsAdapter
import com.kevinserrano.supermeli.home.ui.adapter.CategoriesAdapter
import com.kevinserrano.supermeli.searchbycategory.ui.SearchByCategoryActivity
import com.kevinserrano.supermeli.utils.removeFragment
import com.kevinserrano.supermeli.utils.showFragment
import com.kevinserrano.supermeli.welcome.WelcomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var categoriesAdapter by Delegates.notNull<CategoriesAdapter>()
    private var articleViewedsAdapter by Delegates.notNull<ArticleViewedsAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        handleEvents()
        handleStates()
    }

    private fun setUpViews() {
        categoriesAdapter = CategoriesAdapter(this::onCategoryClick)
        articleViewedsAdapter = ArticleViewedsAdapter(this::onArticleViewedClick)
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvResults.adapter = articleViewedsAdapter
    }

    private fun onArticleViewedClick(articleViewed: ArticleViewed) {
        viewModel.onArticleViewedClick(
            articleViewedId = articleViewed.id,
            isMeliChoiceCandidate = articleViewed.isMeliChoiceCandidate == 1,
            sellerName = articleViewed.sellerName,
            sellerLink = articleViewed.sellerLink
        )
    }

    private fun onCategoryClick(category: CategoryItem) {
        viewModel.onCategoryItemClicked(title = category.name, categoryId = category.id)
    }

    private fun handleEvents() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is HomeViewModel.Event.NavigationToArticleDetail -> {
                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            ArticleDetailActivity::class.java
                        ).apply {
                            putExtra(CATEGORY_ID_KEY_PARAM, event.articleViewedId)
                            putExtra(
                                IS_MELI_CHOICE_CANDIDATE_KEY_PARAM,
                                event.isMeliChoiceCandidate
                            )
                            putExtra(SELLER_NAME_KEY_PARAM, event.sellerName)
                            putExtra(SELLER_lINK_KEY_PARAM, event.sellerLink)
                        })
                }
                is HomeViewModel.Event.NavigationToSearchByCategory -> {
                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            SearchByCategoryActivity::class.java
                        ).apply {
                            putExtra(CATEGORY_TITLE_KEY_PARAM, event.title)
                            putExtra(CATEGORY_ID_KEY_PARAM, event.categoryId)
                        })
                }
            }
        }
    }

    private fun handleStates() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when {
                state.error -> {
                    binding.rvCategories.visibility = View.GONE
                    binding.rvResults.visibility = View.GONE
                }
                !state.categories.isNullOrEmpty() -> {
                    categoriesAdapter.setCategories(state.categories)
                }
            }
            when {
                !state.articleVieweds.isNullOrEmpty() -> {
                    binding.lbTitle.visibility = View.VISIBLE
                    articleViewedsAdapter.setArticleViewed(state.articleVieweds)
                    childFragmentManager.removeFragment()
                }
                state.articleVieweds.isNullOrEmpty() -> {
                    childFragmentManager.showFragment(
                        containerViewId = R.id.containerChildFragment,
                        fragment = WelcomeFragment(), WelcomeFragment::class.java.name
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

const val CATEGORY_TITLE_KEY_PARAM = "category_title"
const val CATEGORY_ID_KEY_PARAM = "article_id"