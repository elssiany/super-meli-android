package com.kevinserrano.supermeli.search.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.databinding.FragmentSearchBinding
import com.kevinserrano.supermeli.detail.ui.ArticleDetailActivity
import com.kevinserrano.supermeli.detail.ui.IS_MELI_CHOICE_CANDIDATE_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.SELLER_NAME_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.SELLER_lINK_KEY_PARAM
import com.kevinserrano.supermeli.lib.ui.empty.EmptyFragment
import com.kevinserrano.supermeli.lib.ui.error.ErrorFragment
import com.kevinserrano.supermeli.lib.ui.loading.LoadingFragment
import com.kevinserrano.supermeli.search.model.Article
import com.kevinserrano.supermeli.searchbycategory.ui.ARTICLE_ID_KEY_PARAM
import com.kevinserrano.supermeli.utils.removeFragment
import com.kevinserrano.supermeli.utils.showFragment
import com.kevinserrano.supermeli.utils.toHtml
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchResultsViewModel by viewModels()
    private var articleResultsAdapter by Delegates.notNull<ArticleResultsAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputQuery.hint = getString(R.string.search_in_mercado_libre_blue).toHtml()
        setUpViews()
        handleEvents()
        handleStates()
        addListeners()
    }

    private fun addListeners() {
        binding.fab.setOnClickListener {
            showDialogFilter()
        }
        binding.inputQuery.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                if (query.isNotEmpty())
                    viewModel.searchArticles(query = query)
                v.hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun setUpViews() {
        articleResultsAdapter = ArticleResultsAdapter(this::onArticleClick)
        binding.rvResults.adapter = articleResultsAdapter
    }

    private fun showDialogFilter() {
        val tags = resources.getStringArray(R.array.filter_tags)
        val selectedTags = ArrayList<String>()
        var alertDialog: AlertDialog? = null
        alertDialog = AlertDialog.Builder(requireActivity(), R.style.AlertDialogCustom).apply {
            setCancelable(false)
            setIcon(R.drawable.icon_mercadolibre_small)
            setTitle(R.string.filter_search)
            setMultiChoiceItems(
                R.array.filter_items, null
            ) { _, which, isChecked ->
                val selectTag = tags[which]
                var uncheckPosi = -1
                if (isChecked) {
                    if (selectedTags.contains(FILTER_PRICE_ASC) && selectTag == FILTER_PRICE_DESC) {
                        uncheckPosi = tags.indexOf(FILTER_PRICE_ASC)
                        selectedTags.remove(FILTER_PRICE_ASC)
                    } else if (selectedTags.contains(FILTER_PRICE_DESC) && selectTag == FILTER_PRICE_ASC) {
                        uncheckPosi = tags.indexOf(FILTER_PRICE_DESC)
                        selectedTags.remove(FILTER_PRICE_DESC)
                    } else if (selectedTags.contains(FILTER_ARTICLE_NEW) && selectTag == FILTER_ARTICLE_USED) {
                        uncheckPosi = tags.indexOf(FILTER_ARTICLE_NEW)
                        selectedTags.remove(FILTER_ARTICLE_NEW)
                    } else if (selectedTags.contains(FILTER_ARTICLE_USED) && selectTag == FILTER_ARTICLE_NEW) {
                        uncheckPosi = tags.indexOf(FILTER_ARTICLE_USED)
                        selectedTags.remove(FILTER_ARTICLE_USED)
                    }
                    selectedTags.add(selectTag)
                    if (uncheckPosi >= 0)
                        alertDialog?.listView?.setItemChecked(uncheckPosi, false)
                } else if (selectedTags.contains(selectTag)) {
                    selectedTags.remove(selectTag)
                }
            }
                .setPositiveButton(
                    R.string.filter
                ) { dialog, _ ->
                    dialog.cancel()
                    viewModel.filterSearchArticles(
                        query = binding.inputQuery.text.toString(),
                        selectedTags = selectedTags
                    )
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
        }.create()
        alertDialog?.show()
    }

    private fun onArticleClick(article: Article) {
        viewModel.onArticleItemClicked(article = article)
    }

    private fun handleEvents() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            if (event is SearchResultsViewModel.Event.NavigationToArticleDetail) {
                requireContext().startActivity(
                    Intent(
                        requireContext(),
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
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.fab.hide()
            when {
                state.loading -> {
                    articleResultsAdapter.clean()
                    childFragmentManager.showFragment(
                        containerViewId = R.id.containerChildFragment,
                        fragment = LoadingFragment(), LoadingFragment::class.java.name
                    )
                }
                state.error -> {
                    articleResultsAdapter.clean()
                    childFragmentManager.showFragment(
                        containerViewId = R.id.containerChildFragment,
                        fragment = ErrorFragment().apply {
                            onRetry = {
                                val query = binding.inputQuery.toString()
                                if (query.isNotEmpty())
                                    viewModel.searchArticles(query = query)
                                else
                                    childFragmentManager.removeFragment()
                            }
                        }, ErrorFragment::class.java.name
                    )
                }
                !state.articles.isNullOrEmpty() -> {
                    childFragmentManager.removeFragment()
                    articleResultsAdapter.setArticle(state.articles)
                    binding.fab.show()
                }
                state.articles.isNullOrEmpty() -> {
                    childFragmentManager.showFragment(
                        containerViewId = R.id.containerChildFragment,
                        fragment = EmptyFragment(), EmptyFragment::class.java.name
                    )
                }
            }
        }
    }

    private fun View.hideKeyboard() {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(this.windowToken, 0)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}