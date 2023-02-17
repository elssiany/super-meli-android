package com.kevinserrano.supermeli.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.search.model.Article

class ArticleResultsAdapter(private val onArticleClick: (Article) -> Unit) :
    RecyclerView.Adapter<ArticleResultsAdapter.ArticleViewHolder>() {

    private var recyclerViewItems = emptyList<Article>()

    inner class ArticleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imgArticle: ImageView = itemView.findViewById(R.id.img)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvDues: TextView = itemView.findViewById(R.id.tvDues)
        private val lbShipping: TextView = itemView.findViewById(R.id.lbShipping)
        private val lbRecommended: TextView = itemView.findViewById(R.id.lbRecommended)
        private val tvCondition: TextView = itemView.findViewById(R.id.tvCondition)

        fun bind(article: Article) = with(itemView) {
            Glide.with(context)
                .load(article.thumbnail)
                .into(imgArticle)
            tvTitle.text = article.title
            tvPrice.text = article.price
            tvDues.text = article.dueText
            tvCondition.text = if(article.condition == "new")
                tvCondition.context.getText(R.string.neww)
            else
                tvCondition.context.getText(R.string.use)
            lbShipping.visibility = if(article.isFreeShipping)
                View.VISIBLE
            else
                View.GONE
            lbRecommended.visibility = if(article.meliChoiceCandidate)
                View.VISIBLE
            else
                View.INVISIBLE
            itemView.setOnClickListener {
                onArticleClick(article)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArticleViewHolder = ArticleViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_article,
        parent, false
    ))

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int,
    ) {
        val item = recyclerViewItems[holder.adapterPosition]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    fun clean() {
        this.recyclerViewItems = emptyList()
        notifyDataSetChanged()
    }

    fun setArticle(recyclerViewItems: List<Article>) {
        this.recyclerViewItems = recyclerViewItems
        notifyDataSetChanged()
    }
}
