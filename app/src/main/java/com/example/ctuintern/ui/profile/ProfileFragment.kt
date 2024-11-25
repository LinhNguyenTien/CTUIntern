package com.example.ctuintern.ui.profile

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.ctuintern.R
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Profile
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.data.model.User
import com.example.ctuintern.databinding.FragmentProfileBinding
import com.example.ctuintern.ui.login.LoginFailureDialog
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : MainFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private lateinit var student: Student
    private var folder = ""
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var profile: Profile;
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, proceed with the download
            downloadCV()
        } else {
            // Permission denied, show a message
            makeToast("Permission denied. Cannot download file.")
        }
    }
    override fun initView() {
        if(getCurrentUser() != null) {
            user = getCurrentUser()!!
            when(user) {
                is Student -> {
                    Log.i("profile fragment", "user is student")
                    student = user as Student
                    setupStudentUI(student)
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
            // Show the dialog
            if (resultUpdateCVDialog != null) {
                showRoundedDialog(resultUpdateCVDialog)
                viewModel.resetUploadState()
            }
        })

        viewModel.newProfilePath.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext())
                .load(it)
                .transform(CircleCrop())
                .override(150,150)
                .error(R.drawable.default_user)
                .into(binding.profilePicture)
        })
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
        viewModel.initView(student.userID)
        Log.i("profileFragment", "Student info: $student")
        binding.name.text = student.userName
        binding.studyTime.text = student.studyTime
        binding.GPA.text = student.GPA.toString()
        binding.phone.text = student.phone
        binding.email.text = student.email
        Glide.with(requireContext())
            .load(student.profilePicture)
            .transform(CircleCrop())
            .override(150,150)
            .error(R.drawable.default_user)
            .into(binding.profilePicture)
        binding.fieldFrame.visibility = GONE
        binding.addressFrame.visibility = GONE
        binding.websiteFrame.visibility = GONE
        binding.sizeFrame.visibility = GONE

        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            this.profile = profile
            binding.CVFrame.setOnClickListener {
                if(profile.CVPath.isNullOrEmpty()) {
                    val emptyCVDialog= EmptyCVDialog(
                        context = requireContext(),
                        addCV = {
                            val uploadCVDialog = UploadCVDialog(
                                context =  requireContext(),
                                uploadCV = {
                                    openFilePicker("application/pdf")
                                }
                            )
                            showRoundedDialog(uploadCVDialog)
                        }
                    )
                    showRoundedDialog(emptyCVDialog)
                }
                else {
                    checkPermissionsAndDownload()
                }
            }
        })

        viewModel.classCTU.observe(viewLifecycleOwner, Observer { classCTU ->
            binding.major.text = classCTU.major.majorName
        })
    }

    private fun showRoundedDialog(dialog: Dialog) {
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(android.R.color.transparent)
            )
        )
        dialog.show()
    }

    private fun openFilePicker(fileTypes: String = "*/*") {
        folder = if(fileTypes == "image/pdf") "CV"
        else "ProfilePicture"
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = fileTypes
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                viewModel.uploadResourceToFBS(student, profile, folder, uri) {
                    profile.CVPath = it
                }
            }
        }
    }

    private fun checkPermissionsAndDownload() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, proceed with download
                downloadCV()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                // Show an explanation to the user why this permission is needed
                makeToast("Storage permission is needed to download files.")
            }
            else -> {
                // Directly request the permission
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    override fun initClick() {
        binding.updateBtn.setOnClickListener {
            showRoundedDialog(
                UpdateInfoDialog(
                    context = requireContext(),
                    updateCV = { openFilePicker("application/pdf") },
                    updateProfile = {
                        newPhone -> run {
                            student.phone = newPhone
                            binding.phone.text = newPhone
                            viewModel.updateProfile(student)
                        }
                    }
                )
            )
        }
        binding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.updateProfilePicture.setOnClickListener {
            openFilePicker("image/png")
        }
        binding.logout.setOnClickListener {
            setCurrentUser(null)
            logout()
        }
    }

    private fun downloadCV() {
        val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(profile.CVPath))

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