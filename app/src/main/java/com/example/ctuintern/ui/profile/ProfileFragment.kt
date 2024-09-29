package com.example.ctuintern.ui.profile

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.ctuintern.R
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.data.model.User
import com.example.ctuintern.databinding.FragmentProfileBinding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : MainFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private lateinit var student: Student
    private lateinit var recycleView: RecyclerView
    private val viewModel: ProfileViewModel by viewModels()
    override fun initView() {
        if(getCurrentUser() != null) {
            user = getCurrentUser()!!
            when(user) {
                is Student -> {
                    Log.i("profile fragment", "user is student")
                    student = user as Student
                    setupStudentUI(student)
                    setupAchievements(student)
                }
                is Employer -> {
                    Log.i("profile fragment", "user is employer")
                    val employer: Employer= user as Employer
                    setupEmployerUI(employer)
                }
                is Teacher -> {
                    Log.i("profile fragment", "user is teacher")
                }
                else -> Log.i("profile fragment", "user is unknown type")
            }
        }

        viewModel.uploadState.observe(viewLifecycleOwner, Observer {
            var resultUpdateCVDialog: ResultUpdateCVDialog? = null
            if(it.equals(UploadState.SUCCESS)) {
                resultUpdateCVDialog = ResultUpdateCVDialog(requireContext(), true)
            }
            else if(it.equals(UploadState.FAIL)) {
                resultUpdateCVDialog = ResultUpdateCVDialog(requireContext(), false)
            }
            else {
                // do nothing
            }

            val window = resultUpdateCVDialog!!.window
            if (window != null) {
                // Set the dialog position to top
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(window.attributes)
                layoutParams.gravity = Gravity.TOP
                layoutParams.y = 50
                window.attributes = layoutParams
            }

            // Show the dialog
            resultUpdateCVDialog.show()
        })
    }

    private fun setupAchievements(student: Student) {
        val adapter = AchievementAdapter { imageURL ->
            ShowFullScreenDialog(requireContext(), imageURL)
        }
        adapter.setDataset(student.profile.achievements)
        binding.recycleView.adapter = adapter
    }

    private fun setupEmployerUI(employer: Employer) {
        binding.name.text = employer.userName
        binding.field.text = employer.field
        binding.address.text = employer.address
        binding.website.text = employer.websiteAddress
        binding.size.text = employer.size
        Glide.with(requireContext())
            .load(employer.profilePicture)
            .transform(CircleCrop())
            .override(150,150)
            .into(binding.profilePicture)

        binding.major.visibility = GONE
        binding.studyTime.visibility = GONE
        binding.GPA.visibility = GONE
        binding.phone.visibility = GONE
        binding.email.visibility = GONE
    }

    private fun setupStudentUI(student: Student) {
        Log.i("profileFragment", "Student info: $student")
        binding.name.text = student.userName
        binding.major.text = student.classCTU.major.majorName
        binding.studyTime.text = student.studyTime
        binding.GPA.text = student.GPA.toString()
        binding.phone.text = student.phone
        binding.email.text = student.email
        Glide.with(requireContext())
            .load(student.profilePicture)
            .transform(CircleCrop())
            .override(150,150)
            .into(binding.profilePicture)
        binding.CVFrame.setOnClickListener {
            if(student.profile.CVPath.isEmpty()) {
                val emptyCVDialog= EmptyCVDialog(
                    context = requireContext(),
                    addCV = {
                        val uploadCVDialog = UploadCVDialog(
                            context =  requireContext(),
                            uploadCV = {
                                openFilePicker()
                            }
                        )
                        uploadCVDialog.show()
                    }
                )
                emptyCVDialog.show()
            }
            else {
                checkPermissionsAndDownload()
            }
        }
        binding.fieldFrame.visibility = GONE
        binding.addressFrame.visibility = GONE
        binding.websiteFrame.visibility = GONE
        binding.sizeFrame.visibility = GONE
    }
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                viewModel.uploadCVToFirebaseStorage(student, uri)
            }
        }
    }

    private fun checkPermissionsAndDownload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Permission already granted
                downloadCV()
            } else {
                // Request permission
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
            }
        } else {
            // No runtime permission required for versions below Android 6.0
            downloadCV()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadCV() // Permission granted, start the download
            } else {
                makeToast("Permission denied")
            }
        }
    }

    override fun initClick() {
        binding.updateBtn.setOnClickListener {

        }
        binding.logout.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_profileFragment_to_loginFragment)
            setCurrentUser(null)
        }
        binding.backBtn.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_profileFragment_to_newsFragment)
        }
    }

    private fun downloadCV() {
        val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(student.profile.CVPath))

        request.setTitle("Downloading file")
        request.setDescription("Downloading file")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "CV_${user.userName}")

        // Enqueue the request
        downloadManager.enqueue(request)
    }

    override fun showNewsDetail(news: News) {
        // Do nothing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initView()
        initClick()
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        private const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1
        private const val PICK_FILE_REQUEST_CODE = 2
    }
}