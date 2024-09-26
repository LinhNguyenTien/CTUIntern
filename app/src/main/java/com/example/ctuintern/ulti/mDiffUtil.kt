package com.example.ctuintern.ulti

import androidx.recyclerview.widget.DiffUtil
import com.example.ctuintern.data.model.News

class mDiffUtil: DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            // Compare unique IDs or properties that define whether two items are the same
            return oldItem.newID == newItem.newID
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            // Compare the entire content of the item to check if they have the same data
            return oldItem == newItem
        }
}