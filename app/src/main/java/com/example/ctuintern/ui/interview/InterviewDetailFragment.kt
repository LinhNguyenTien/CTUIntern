package com.example.ctuintern.ui.interview

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.FragmentInterviewBinding
import com.example.ctuintern.databinding.FragmentInterviewDetailBinding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class InterviewDetailFragment : MainFragment() {
    private var _binding: FragmentInterviewDetailBinding? = null
    private val binding get() = _binding!!
    private val args: InterviewDetailFragmentArgs by navArgs()
    private val appliedNews get() = args.appliedNew
    private var student: Student? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        if(getCurrentUser() != null) {
            val user = getCurrentUser()
            if (user is Student) {
                student = user
            }
        }

        Glide
            .with(requireContext())
            .load(appliedNews.news?.employer?.profilePicture)
            .error(R.drawable.default_company)
            .into(binding.companyLogo)

        if(appliedNews.room != null) {
            binding.state.apply {
                text = "Phỏng vấn"
                setTextColor(ContextCompat.getColor(requireContext(), R.color.success))
            }
        }
        else {
            binding.state.apply {
                text = "Chưa phản hồi"
                setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            }
        }
        binding.jobName.text = appliedNews.news?.title
        binding.companyName.text = appliedNews.news?.employer?.userName
        binding.day.text = getDates(appliedNews.room?.interviewDay.toString())
        binding.duration.text = formatDuration(appliedNews.room?.duration?:-1)
        binding.interviewer.text = appliedNews.room?.interviewerName
        binding.description.text = appliedNews.news?.content
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initClick() {
        val inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss")
        val interviewDay = LocalDateTime.parse(appliedNews.room?.interviewDay, inputFormatter)
        binding.joinRoom.setOnClickListener {
            if (isToday(interviewDay) && isBefore(interviewDay, minutes = 10)) {
                startCall()
            }
            else {
                showRoundedDialog(
                    WaitingDialog(
                        requireContext(),
                        interviewDay
                    )
                )
            }
        }
        binding.close.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun showRoundedDialog(dialog: Dialog) {
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(android.R.color.transparent)
            )
        )
        dialog.show()
    }

    private fun startCall() {
        // This zegocloud account will be expired on 07/09/2024
        val appID: Long = 307241710L
        val appSign = "24fb1838cd34652551b5a387e6255965c29ee9f8a82898a12b8e49641b42873c"

        val callID = appliedNews.room?.roomID
        val userID = student?.userID
        val userName = student?.userName

        // You can also use GroupVideo/GroupVoice/OneOnOneVoice to make more types of calls.
//        val config = ZegoUIKitPrebuiltCallConfig()
//        ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall()
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

    private fun formatDuration(duration: Int): String {
        if(duration > 0) {
            val hours = duration / 60
            val minutes = duration % 60
            if(hours > 0) return "$hours tiếng $minutes phút"
            return "$minutes phút"
        }
        else {
            return "Không xác định"
        }
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

    @RequiresApi(Build.VERSION_CODES.O)
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
        val formattedDay = interviewDay?.toLocalDate()
        val today = LocalDate.now()
        Log.i("today localdate", "today: $today and interview day: $formattedDay")
        return formattedDay?.isEqual(today) == true
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
