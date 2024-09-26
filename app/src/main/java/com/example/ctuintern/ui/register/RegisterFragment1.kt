package com.example.ctuintern.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.example.ctuintern.R
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.Field
import com.example.ctuintern.databinding.FragmentRegister1Binding
import com.example.ctuintern.ui.main.MainFragment
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Address
import kotlin.io.path.fileVisitor

@AndroidEntryPoint
class RegisterFragment1 : MainFragment() {
    private var _binding: FragmentRegister1Binding? = null
    private val binding get() = _binding!!
    private lateinit var name: TextInputEditText
    private lateinit var field: AutoCompleteTextView
    private lateinit var address: TextInputEditText
    private lateinit var website: TextInputEditText
    private lateinit var size: AutoCompleteTextView
    private lateinit var nextBtn: Button
    private lateinit var backBtn: Button
    private var employer: Employer? = null
    private val args: RegisterFragment1Args by navArgs()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun initView() {
        name = binding.name
        field = binding.field
        address = binding.address
        website = binding.website
        size = binding.size
        nextBtn = binding.nextBtn
        backBtn = binding.backBtn
    }

    override fun initClick() {
        nextBtn.setOnClickListener {
            if(checkInputStep1()){
                getInput()
                if(employer != null) {
                    Log.i("employer info", "employer info: ${employer!!.userName}, ${employer!!.field}, ${employer!!.address}, ${employer!!.websiteAddress}, ${employer!!.size}")
                    navigateToFragment(binding.root, RegisterFragment1Directions.actionRegisterFragment1ToRegisterFragment2(
                        employer!!
                    ))
                }
            }
        }
        backBtn.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_registerFragment1_to_loginFragment)
        }
    }

    private fun getInput() {
        employer = Employer()
        if(employer != null) {
            employer!!.userName = name.text.toString()
            employer!!.field = field.text.toString()
            employer!!.address = address.text.toString()
            employer!!.websiteAddress = website.text.toString()
            employer!!.size = size.text.toString()
        }
    }

    private fun checkInputStep1(): Boolean {
        return (!name.text.isNullOrEmpty() && !field.text.isNullOrEmpty() && !address.text.isNullOrEmpty())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegister1Binding.inflate(inflater, container, false)
        initView()
        initClick()

        // List of suggestions
        val suggestions = listOf("0 - 10", "20 - 50", "50 - 100", "100 - 500", "500 - 1000", ">1000")
        // Create an ArrayAdapter
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions)
        size.setAdapter(adapter)

        // List of field
        val fieldNameList = listOf("Khác", "Nông nghiệp", "Thủy sản", "Công nghiệp chế biến", "Xây dựng", "Công nghệ thông tin", "Du lịch", "Giao thông vận tải", "Kinh tế tài chính")
        // Create an ArrayAdapter
        val fieldAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, fieldNameList)
        field.setAdapter(fieldAdapter)
        return binding.root
    }

}