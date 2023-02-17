package com.kevinserrano.supermeli.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.home.model.ArticleViewed

class ArticleViewedsAdapter(private val onArticleViewedClick: (ArticleViewed) -> Unit) :
    RecyclerView.Adapter<ArticleViewedsAdapter.ArticleViewedViewHolder>() {

    private var recyclerViewItems = emptyList<ArticleViewed>()

    inner class ArticleViewedViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imgArticle: ImageView = itemView.findViewById(R.id.imgArticle)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val lbFreeShipping: TextView = itemView.findViewById(R.id.lbFreeShipping)

        fun bind(articleViewed: ArticleViewed) = with(itemView) {
            Glide.with(context)
                .load(articleViewed.thumbnail)
                .into(imgArticle)
            tvName.text = articleViewed.title
            tvPrice.text = articleViewed.price
            lbFreeShipping.visibility = if(articleViewed.freeShipping == 1)
                View.VISIBLE
            else
                View.GONE
            itemView.setOnClickListener {
                onArticleViewedClick(articleViewed)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArticleViewedViewHolder = ArticleViewedViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_article_viewed,
        parent, false
    ))

    override fun onBindViewHolder(
        holder: ArticleViewedViewHolder,
        position: Int,
    ) {
        val item = recyclerViewItems[holder.adapterPosition]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    fun setArticleViewed(recyclerViewItems: List<ArticleViewed>) {
        this.recyclerViewItems = recyclerViewItems
        notifyDataSetChanged()
    }
}
