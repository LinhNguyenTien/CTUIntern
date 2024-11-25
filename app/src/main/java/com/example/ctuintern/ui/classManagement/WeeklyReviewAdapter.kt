package com.example.ctuintern.ui.classManagement

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.data.model.WeeklyReview
import com.example.ctuintern.databinding.ReviewTaskItemBinding

class WeeklyReviewAdapter: RecyclerView.Adapter<WeeklyReviewAdapter.WeeklyReviewVH>() {
    private var weeklyReviews = listOf<WeeklyReview>()
    private var muteReview = false
    fun setDataset(weeklyReviews: List<WeeklyReview>) {
        this.weeklyReviews = weeklyReviews
    }

    fun muteReview() {
        muteReview = true
    }

    class WeeklyReviewVH(private val binding: ReviewTaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: WeeklyReview, muteReview: Boolean) {
            binding.week.text = "Tuần ${review.week} (${review.fromDay} - ${review.toDay})"
            binding.numberOfSession.text = "Số buổi làm việc: ${review.numberOfSessions}"
            binding.content.text = "Nội dung công việc: ${review.content}"
            if(muteReview) {
                binding.review.visibility = GONE
            }
            else {
                binding.review.text = "Nhận xét: ${review.review}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyReviewVH {
        return WeeklyReviewVH(
            ReviewTaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return weeklyReviews.size
    }

    override fun onBindViewHolder(holder: WeeklyReviewVH, position: Int) {
        val review = weeklyReviews[position]
        holder.bind(review, muteReview)
    }
}