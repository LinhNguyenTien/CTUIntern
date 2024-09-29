package com.example.ctuintern.ui.register

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.ctuintern.R
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentRegister2Binding
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.ulti.isValidInputStep2
import com.example.ctuintern.ulti.isValidPhoneNumber
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
@AndroidEntryPoint
class RegisterFragment2 : MainFragment() {
    private var _binding: FragmentRegister2Binding? = null
    private val binding get() = _binding!!
    private lateinit var account: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var confirmPassword: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var nextBtn: Button
    private lateinit var backBtn: Button
    private lateinit var employer: Employer
    private val args: RegisterFragment2Args by navArgs()
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var registerFailDialog: RegisterFailDialog

    override fun initView() {
        account = binding.account
        password = binding.password
        confirmPassword = binding.confirmPassword
        phone = binding.phone
        email = binding.email
        nextBtn = binding.nextBtn
        backBtn = binding.backBtn
    }

    override fun initClick() {
        nextBtn.setOnClickListener {
            if(isValidInputStep2(account.text.toString(), password.text.toString(), confirmPassword.text.toString(), phone.text.toString(), email.text.toString())) {
                getInput()
                registerViewModel.createUser(employer,
                    successBehavior = {
                        Log.i("employer info", "employer info: ${employer.account}, ${employer.password}, ${employer.phone}, ${employer.email}, ${employer.role}")
                        sendActiveEmail(employer.email)
                    },
                    failBehavior = {
                        showRegisterFailDialog(it)
                    })

            }
        }
        backBtn.setOnClickListener {
            navigateToFragment(binding.root, RegisterFragment2Directions.actionRegisterFragment2ToRegisterFragment1(employer))
        }
    }

    override fun showNewsDetail(news: News) {

    }

    private fun sendActiveEmail(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(employer.email, employer.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    sendVerificationEmail(user!!)
                } else {
                    // Handle registration failure
                    showRegisterFailDialog("Tạo người dùng không thành công!")
                }
            }
    }

    private fun sendVerificationEmail(user: FirebaseUser) {
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToFragment(binding.root, R.id.action_registerFragment2_to_registerFragment3)
            } else {
                // Handle error
                showRegisterFailDialog("Gửi email xác thực không thành công!")
            }
        }
    }
    private fun showRegisterFailDialog(reason: String) {
        registerFailDialog = RegisterFailDialog(requireContext(), reason)
        registerFailDialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(android.R.color.transparent)
            )
        )
        registerFailDialog.show()
    }

    private fun getInput() {
        employer.account = account.text.toString()
        employer.password = password.text.toString()
        employer.phone = phone.text.toString()
        employer.email = email.text.toString()
        employer.role = "employer"
        employer.profilePicture = "https://icons.veryicon.com/png/o/miscellaneous/two-color-icon-library/user-286.png"
        employer.userID = UUID.randomUUID().toString()
        employer.employerID = employer.userID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegister2Binding.inflate(inflater, container, false)
        initView()
        initClick()
        employer = args.employer
        if(employer != null) {
            Log.i("employer info", " employer name: ${employer.userName}, field: ${employer.field}, address: ${employer.address}, website: ${employer.websiteAddress}, size: ${employer.size}")
        }
        return binding.root
    }
}