
package com.example.ctuintern.data.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.User
import com.example.ctuintern.databinding.JobItemBinding
import com.example.ctuintern.ulti.mDiffUtil
import okhttp3.internal.addHeaderLenient

class NewsAdapter(
    private val addNewsToFavorite: (News) -> Unit
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList: List<News> = listOf()
    class NewsViewHolder(private val binding: JobItemBinding, private val addNewsToFavorite: (News) -> Unit) : ViewHolder(binding.root) {
        lateinit var companyPhoto: ImageView
        lateinit var companyName: TextView
        lateinit var quantity: TextView
        lateinit var expireDay: TextView
        lateinit var favorite: ImageView

        fun initView() {
            companyPhoto = binding.companyLogo
            companyName = binding.companyName
            quantity = binding.quantity
            expireDay = binding.deadline
            favorite = binding.favorite
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
            quantity.text = news.quantity.toString()
            expireDay.text = news.expireDay.toString()
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
        }
        else {
            Log.i("news info", "news is null")
        }
    }
}
