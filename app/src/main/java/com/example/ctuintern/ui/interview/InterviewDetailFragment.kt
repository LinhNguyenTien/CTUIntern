package com.example.ctuintern.ui.interview

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentInterviewBinding
import com.example.ctuintern.databinding.FragmentInterviewDetailBinding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime

@AndroidEntryPoint
class InterviewDetailFragment : MainFragment() {
    private var _binding: FragmentInterviewDetailBinding? = null
    private val binding get() = _binding!!
    private val args: InterviewDetailFragmentArgs by navArgs()
    private val appliedNews get() = args.appliedNew
    override fun initView() {
        Glide
            .with(requireContext())
            .load(appliedNews.news?.employer?.profilePicture)
            .error(R.drawable.default_company)
            .into(binding.companyLogo)

        binding.companyName.text = appliedNews.news?.employer?.userName
        binding.day.text = appliedNews.room?.interviewDay
        binding.interviewer.text = appliedNews.room?.interviewerName
        binding.description.text = appliedNews.news?.content
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initClick() {
        val interviewDay = LocalDateTime.parse(appliedNews.room?.interviewDay)
        if (isToday(interviewDay) && isBefore(interviewDay, minutes = 10)) {
            binding.joinRoom.setOnClickListener {
                startCall()
            }
        }


    }

    private fun startCall() {
        //        Log.i("Received Message", "Start call at Root")
//        // This zegocloud account will be expired on 07/09/2024
//        val appID: Long = BuildConfig.ZEGOCLOUD_APP_ID
//        val appSign = BuildConfig.ZEGOCLOUD_APP_SIGN
//
//        val callID = callObject.callID
//        val userID = currentUser.id
//        val userName = currentUser.fullname
//
//        // You can also use GroupVideo/GroupVoice/OneOnOneVoice to make more types of calls.
//        val config = ZegoUIKitPrebuiltCallConfig()
//        if(callObject.isVideoCall) {
//            Log.i("Received Message", "Video call")
//            ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall()
//        }
//        else{
//            Log.i("Received Message", "Audio call")
//            ZegoUIKitPrebuiltCallConfig.oneOnOneVoiceCall()
//        }
//        config.apply {
//            // Set up event listener click on close video call, back to current fragment
//            this.leaveCallListener = ZegoUIKitPrebuiltCallFragment.LeaveCallListener {
//                supportFragmentManager.popBackStack()
//            }
//            // Mute microphone and turn off camera when joining call
//            this.turnOnCameraWhenJoining = false
//            this.turnOnMicrophoneWhenJoining = false
//        }
//
//        // Set up fragment to show call
//        val fragment = ZegoUIKitPrebuiltCallFragment.newInstance(
//            appID, appSign, userID, userName, callID, config
//        )
//
//        // Show fragment
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainerInCall, fragment)
//            .addToBackStack(null)
//            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isBefore(interviewDay: LocalDateTime, hours: Long? = 0, minutes: Long? = 0): Boolean {
        var currentTime = LocalDateTime.now()
        if(currentTime.isAfter(interviewDay)) return true

        currentTime = currentTime.plusHours(hours?:0)
        currentTime = currentTime.plusMinutes(minutes?:0)
        return currentTime.equals(interviewDay) || currentTime.isAfter(interviewDay)
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
        _binding = FragmentInterviewDetailBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isToday(interviewDay: LocalDateTime?): Boolean {
        val today = LocalDate.now()
        return interviewDay?.toLocalDate()?.isEqual(today) == true
    }
}
