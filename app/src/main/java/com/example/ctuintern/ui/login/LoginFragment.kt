package com.example.ctuintern.ui.login

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.ctuintern.R
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.data.model.User
import com.example.ctuintern.databinding.FragmentLoginBinding
import com.example.ctuintern.ui.main.MainFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LoginFragment : MainFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var forgetPassword: TextView
    private lateinit var register: TextView
    private lateinit var login: Button
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var loginFailDialog: LoginFailureDialog
    private lateinit var loadingDialog: LoadingDialog
    override fun initView() {
        loadingDialog = LoadingDialog(requireContext())
        email = binding.email
        password = binding.password
        forgetPassword = binding.forgetPassword
        register = binding.register
        login = binding.loginBtn
    }

    override fun initClick() {
        login.setOnClickListener {
            Log.i("authen", "click on login btn")
            authenticateAccount()
        }

        forgetPassword.setOnClickListener {
            // Switch to mail application with receiver address is linhb2012108@student.ctu.edu.vn
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:linhb2012108@student.ctu.edu.vn") // Set receiver email
                putExtra(Intent.EXTRA_SUBJECT, "FORGOT PASSWORD") // Optionally set the subject
            }

            try {
                startActivity(Intent.createChooser(emailIntent, "Send email via"))
            } catch (e: Exception) {
                // Handle error if no email app is available
                makeToast("No email app found")
            }
        }

        register.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_loginFragment_to_registerFragment1)
        }
    }

    private fun authenticateAccount() {
        showLoadingDialog()
        if(!email.text.isNullOrEmpty() || !password.text.isNullOrEmpty()){
            val emailTXT = email.text.toString()
            val passwordTXT = password.text.toString()
            viewModel.authenticationAccount(emailTXT, passwordTXT,
                successBehavior = {
                    loadingDialog.dismiss()
                    Log.i("authen", "user: ${it.toString()}")
                    setCurrentUser(it)
                    checkVerifiedEmail(emailTXT, passwordTXT)
                },
                failBehavior = {
                    loadingDialog.dismiss()
                    showLoginFailDialog("Tài khoản hoặc mật khẩu không hợp lệ")
                })
        }
    }

    private fun checkVerifiedEmail(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    firebaseUser?.let {
                        if (firebaseUser.isEmailVerified) {
                            navigateToFragment(binding.root, R.id.action_loginFragment_to_newsFragment)
                        } else {
                            // Show a message asking the user to verify their email
                            showLoginFailDialog("Email chưa được xác thực\nVui lòng kiểm tra email của bạn")
                        }
                    }
                }
            }
    }

    private fun showLoginFailDialog(reason: String) {
        loginFailDialog = LoginFailureDialog(requireContext(), reason)
        loginFailDialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(android.R.color.transparent)
            )
        )
        loginFailDialog.show()
    }

    private fun showLoadingDialog() {
        loadingDialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(android.R.color.transparent)
            )
        )
        loadingDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        FirebaseApp.initializeApp(requireContext())
        initView()
        initClick()
        return binding.root
    }
}