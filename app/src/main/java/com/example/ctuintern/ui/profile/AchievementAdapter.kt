package com.example.ctuintern.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ctuintern.R
import com.example.ctuintern.data.model.Achievement
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.AchievementItemsBinding

class AchievementAdapter(private val showImageFullScreen:(String)->Unit): RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {
    private var achievements: List<Achievement> = listOf()

    fun setDataset(achievements: List<Achievement>) {
        this.achievements = achievements
        notifyDataSetChanged()
    }
    class AchievementViewHolder(val binding: AchievementItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isOpen: Boolean = false
        fun bind(achievement: Achievement, showImageFullScreen: (String) -> Unit) {
            binding.apply {
                title.text = achievement.achievementTitle

                Glide.with(binding.root.context)
                    .load(achievement.achievementURL)
                    .override(100, 100)
                    .into(image)
                image.setOnClickListener {
                    showImageFullScreen(achievement.achievementURL)
                }

                dropDown.setOnClickListener {
                    if(isOpen) {
                        image.visibility = View.GONE
                        isOpen = false
                        it.setBackgroundResource(R.drawable.drop_down_arrow)
                    } else {
                        image.visibility = View.VISIBLE
                        isOpen = true
                        it.setBackgroundResource(R.drawable.drop_up_arrow)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        return AchievementViewHolder(AchievementItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        if(achievements != null) {
            return achievements.size
        } else {
            return 0
        }
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = achievements[position]
        if(achievement != null) {
            holder.bind(achievement, showImageFullScreen)
        }
    }
}