package com.example.ctuintern.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ctuintern.R
import com.example.ctuintern.databinding.FragmentRegister3Binding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment3 : MainFragment() {
    private var _binding: FragmentRegister3Binding? = null
    private val binding get() = _binding!!
    private lateinit var backToLogin: Button
    override fun initView() {
        backToLogin = binding.backBtn
    }

    override fun initClick() {
        backToLogin.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_registerFragment3_to_loginFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegister3Binding.inflate(inflater, container, false)
        initView()
        initClick()
        // Inflate the layout for this fragment
        return binding.root
    }

}