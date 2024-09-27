package com.example.ctuintern.ui.news

import android.app.job.JobInfo
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ctuintern.data.model.News

class ViewPagerAdapter(val news: News, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> JobInformationFragment(news)
            1 -> CompanyInformationFragment(news)
            else -> JobInformationFragment(news)
        }
    }

}