package com.example.ctuintern.ui.interview

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ctuintern.R
import com.example.ctuintern.data.model.AppliedNews
import com.example.ctuintern.databinding.JobItemBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InterviewNewsAdapter(
    val showDetail:(AppliedNews)->Unit
): RecyclerView.Adapter<InterviewNewsAdapter.ViewHolder>() {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appliedNews = newsList[position]
        holder.bind(appliedNews)
        holder.itemView.setOnClickListener {
            showDetail(appliedNews)
        }
    }

    class ViewHolder(val binding: JobItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
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
                binding.thirdSubTitle.text= "Ngày hẹn: " +
                        "${getDates(
                            news.room.interviewDay.toString()
                        )}"
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

        @RequiresApi(Build.VERSION_CODES.O)
        fun getDates(localDateTimeString: String): String {
            val inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

            // Parse the input string
            val localDateTime = LocalDateTime.parse(localDateTimeString, inputFormatter)

            // Format and return the date in the desired format
            return localDateTime.toLocalDate().format(outputFormatter)
        }
    }
}