package com.example.ctuintern.ui.interview

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ctuintern.R
import com.example.ctuintern.data.adapter.NewsAdapter.NewsViewHolder
import com.example.ctuintern.data.model.AppliedNews
import com.example.ctuintern.databinding.JobItemBinding

class AppliedNewsAdapter(val showDetailAppointment:(AppliedNews)->Unit): RecyclerView.Adapter<AppliedNewsAdapter.ViewHolder>() {
    private lateinit var newsList: List<AppliedNews>
    fun setDataSet(newsList: List<AppliedNews>) {
        this.newsList = newsList
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
        return newsList.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appliedNews = newsList[position]
        holder.bind(appliedNews)
        holder.itemView.setOnClickListener {
            showDetailAppointment(appliedNews)
        }
    }

    class ViewHolder(val binding: JobItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: AppliedNews) {
            binding.title.text = news.news?.employer?.userName
            binding.firstSubTitle.text = news.news?.title
            binding.favorite.visibility = GONE
            Glide
                .with(binding.root.context)
                .load(news.news?.employer?.profilePicture)
                .override(100,100)
                .error(R.drawable.default_company)
                .into(binding.companyLogo)

            if(news.room != null) {
                binding.secondSubTitle.text = Html.fromHtml(
                    getString(binding.root.context, R.string.interviewStateAccept),
                    Html.FROM_HTML_MODE_LEGACY
                )
                binding.thirdSubTitle.text = "Ngày hẹn: ${news.room.interviewDay}"
            }
            else {
                binding.secondSubTitle.text = Html.fromHtml(
                    getString(binding.root.context, R.string.interviewStateWaiting),
                    Html.FROM_HTML_MODE_LEGACY
                )
                binding.thirdSubTitle.text = Html.fromHtml(
                    getString(binding.root.context, R.string.interviewDayWaiting),
                    Html.FROM_HTML_MODE_LEGACY
                )
            }
        }
    }
}