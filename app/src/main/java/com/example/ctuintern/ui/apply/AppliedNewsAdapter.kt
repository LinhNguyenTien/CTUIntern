package com.example.ctuintern.ui.apply

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ctuintern.R
import com.example.ctuintern.data.model.AppliedNews
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.JobItemBinding

class AppliedNewsAdapter(
    private val showDetail: ((AppliedNews) -> Unit)? = null
): RecyclerView.Adapter<AppliedNewsAdapter.ViewHolder>() {
    private lateinit var newList: List<AppliedNews>

    fun setDataSet(newList: List<AppliedNews>) {
        this.newList = newList
    }

    class ViewHolder(private val binding: JobItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: AppliedNews, showDetail: ((AppliedNews) -> Unit)? = null) {
            binding.title.text = news.news?.employer?.userName
            binding.firstSubTitle.text = news.news?.title
            binding.secondSubTitle.text = "Số lượng: ${news.news?.quantity} người"
            binding.thirdSubTitle.text = "Hạn nộp: ${news.news?.expireDay}"
            binding.favorite.visibility = View.GONE
            Glide
                .with(binding.root.context)
                .load(news.news?.employer?.profilePicture)
                .override(100,100)
                .error(R.drawable.default_company)
                .into(binding.companyLogo)

            binding.root.setOnClickListener {
                showDetail?.invoke(news)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            JobItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return newList.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appliedNews = newList[position]
        if(appliedNews != null) holder.bind(appliedNews, showDetail)
        else Log.i("Apply Fragment Adapter", "item is null")
    }
}