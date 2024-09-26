package com.example.ctuintern.ui.login

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.ctuintern.databinding.LoginFailDialogBinding

class LoginFailureDialog(context: Context, reason: String): Dialog(context) {
    private var _binding: LoginFailDialogBinding? = null
    private val binding get() = _binding!!
    private var reason: String = reason
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = LoginFailDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.reason.text = reason
    }
}