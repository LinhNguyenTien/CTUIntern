package com.example.ctuintern.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ctuintern.data.model.Review
import com.example.ctuintern.databinding.ReviewItemBinding

class ReviewAdapter(private val showReviewDetail:(Review) -> Unit): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private lateinit var reviews: List<Review>

    fun setDataset(reviews: List<Review>) {
        this.reviews = reviews
    }

    class ReviewViewHolder(private val binding: ReviewItemBinding) : ViewHolder(binding.root) {
        fun bind(review: Review, showReviewDetail: (Review) -> Unit) {
            binding.apply {
                companyName.text = review.employer.userName
                option.text = "Lựa chọn: " + when(review.options) {
                    1 -> "Phù hợp với thực tế"
                    2 -> "Chưa phù hợp với thực tế"
                    3 -> "Tăng cường kỹ năng mềm"
                    4 -> "Tăng cường ngoại ngữ"
                    else -> "Tăng cường kỹ năng làm việc nhóm"
                }
                content.text = "Đánh giá: " + review.content
                reviewDay.text = "Ngày đánh giá: " + review.reviewDay
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review, showReviewDetail)
    }
}