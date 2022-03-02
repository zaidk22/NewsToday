package com.example.newstoday

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val context: Context, private val articles:List<Article>):RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    private lateinit var mListener : onItemClickedListener
    interface onItemClickedListener{
        fun onItemClicked(position: Int)
    }
    fun setItemClickListener(listener: onItemClickedListener){

        mListener = listener
    }

    class ArticleViewHolder(itemView: View,listener: onItemClickedListener):RecyclerView.ViewHolder(itemView)
{
    var newsImage = itemView.image!!
    var newsTitle = itemView.title1!!
    var newsDescription = itemView.desc!!
    var publishDate = itemView.published_at!!
    var author = itemView.author!!
    init {
       itemView.setOnClickListener {
           listener.onItemClicked(adapterPosition)
       }
    }




}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false)

        return ArticleViewHolder(view,mListener)

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.newsTitle.text=article.title
        holder.newsDescription.text = article.description
        holder.author.text=article.author
        holder.publishDate.text=article.publishedAt
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)

    }

    override fun getItemCount(): Int {
        return  articles.size
    }

}

