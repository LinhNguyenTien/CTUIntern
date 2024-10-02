
package com.example.ctuintern.data.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.JobItemBinding

class NewsAdapter(
    private val addNewsToFavorite: (News) -> Unit,
    private val removeNewsFromFavorite: (News) -> Unit,
    private val showDetail: (News) -> Unit
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList: List<News> = listOf()
    class NewsViewHolder(private val binding: JobItemBinding, private val addNewsToFavorite: (News) -> Unit) : ViewHolder(binding.root) {
        lateinit var companyPhoto: ImageView
        lateinit var companyName: TextView
        lateinit var jobName: TextView
        lateinit var quantity: TextView
        lateinit var expireDay: TextView
        lateinit var favorite: ImageView

        fun initView() {
            companyPhoto = binding.companyLogo
            companyName = binding.title
            quantity = binding.secondSubTitle
            expireDay = binding.thirdSubTitle
            favorite = binding.favorite
            jobName = binding.firstSubTitle
        }

        fun bind(news: News) {
            initView()
            val context: Context = binding.root.context
            // Fetch data
            Glide.with(itemView.context)
                .load(news.employer!!.profilePicture)
                .override(150, 150)
                .into(companyPhoto)

            companyName.text = news.employer!!.userName
            jobName.text = news.title
            quantity.text = "${news.quantity.toString()} người"
            expireDay.text = "Hạn nộp: ${news.expireDay.toString()}"
            favorite.setOnClickListener {
                addNewsToFavorite(news)
            }
        }
    }

    fun setDataset(newsList: List<News>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            JobItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false),
            addNewsToFavorite)
    }

    override fun getItemCount(): Int {
        if(newsList != null) {
            return newsList.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        if(news != null) {
            Log.i("news info", "news: ${news.toString()}")
            holder.bind(news)
            holder.itemView.setOnClickListener {
                showDetail(news)
            }
            holder.favorite.setOnClickListener {
                if(news.isFavorite) {
                    removeNewsFromFavorite(news)
                    holder.favorite.setImageResource(R.drawable.heart_gray)
                    news.isFavorite = false
                }
                else {
                    addNewsToFavorite(news)
                    holder.favorite.setImageResource(R.drawable.heart_clicked)
                    news.isFavorite = true
                }

            }
        }
        else {
            Log.i("news info", "news is null")
        }
    }
}
