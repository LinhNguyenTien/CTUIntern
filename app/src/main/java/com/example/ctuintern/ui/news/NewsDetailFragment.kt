package com.example.ctuintern.ui.news

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Transform
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentNewsDetailBinding
import com.example.ctuintern.ui.login.LoginFailureDialog
import com.example.ctuintern.ui.main.MainFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : MainFragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var applyDialog: ApplyDialog
    private val args: NewsDetailFragmentArgs by navArgs()
    private val viewModel: NewsDetailViewModel by viewModels()
    override fun initView() {
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        val news = args.news
        adapter = ViewPagerAdapter(news, requireActivity())
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager, object: TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(p0: TabLayout.Tab, p1: Int) {
                when(p1) {
                    0 -> p0.text = "Thông tin"
                    1 -> p0.text = "Công ty"
                    else -> "Unknow type"
                }
            }
        }).attach()

        Glide.with(requireContext())
            .load(news.employer!!.profilePicture)
            .transform(CircleCrop())
            .override(100, 100)
            .into(binding.companyLogo)

        binding.applyBtn.setOnClickListener {
            viewModel.applyNews(news, getCurrentUser()!!.userID)
            showApplyDialog()
            binding.applyBtn.apply {
                text = "Đã ứng tuyển"
                setOnClickListener {
                    // do nothing
                }
            }
        }

        binding.back.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_newsDetailFragment_to_newsFragment)
        }
    }

    private fun showApplyDialog() {
        applyDialog = ApplyDialog(
            context = requireContext(),
            switchToApplyFragment = {
                navigateToFragment(binding.root, R.id.action_newsDetailFragment_to_applyFragment)
            })
        applyDialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(android.R.color.transparent)
            )
        )
        applyDialog.show()
    }

    override fun initClick() {
    }

    override fun showNewsDetail(news: News) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }

}