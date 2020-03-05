package org.buffer.android.boilerplate.ui.browse

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.model.ArticleIntent


class BrowseAdapter(private val glide: RequestManager) : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

    var articles: List<ArticleIntent> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.nameText.text = article.author
        holder.titleText.text = article.title
        glide.load(article.urlToImage).into(holder.avatarImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_bufferoo, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImage: ImageView = view.findViewById(R.id.image_avatar)
        var nameText: TextView = view.findViewById(R.id.text_name)
        var titleText: TextView = view.findViewById(R.id.text_title)
    }

}