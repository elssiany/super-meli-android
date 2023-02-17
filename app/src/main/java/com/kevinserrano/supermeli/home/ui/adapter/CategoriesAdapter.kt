package com.kevinserrano.supermeli.home.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.home.model.CategoryItem
import com.kevinserrano.supermeli.utils.getDrawableFromImageName

class CategoriesAdapter(private val onCategoryClick: (CategoryItem) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var recyclerViewItems = emptyList<CategoryItem>()

    inner class CategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imgArticle: ImageView = itemView.findViewById(R.id.img)
        private val name: TextView = itemView.findViewById(R.id.name)

        fun bind(category: CategoryItem) = with(itemView) {
            imgArticle.setImageResource(imgArticle.context.getDrawableFromImageName(name = category.imageName))
            name.text = category.name
            itemView.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryViewHolder = CategoryViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_category,
        parent, false
    ))

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int,
    ) {
        val item = recyclerViewItems[holder.adapterPosition]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    fun setCategories(recyclerViewItems: List<CategoryItem>) {
        this.recyclerViewItems = recyclerViewItems
        notifyDataSetChanged()
    }
}
