package com.example.ctuintern.ui.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.collection.arrayMapOf
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.User

abstract class MainFragment: Fragment() {
    abstract fun initView()

    abstract fun initClick()

    fun navigateToFragment(view: View, action: Int) {
        Navigation.findNavController(view).navigate(action)
    }

    fun navigateToFragment(view: View, action: Int, bundle: Bundle) {
        Navigation.findNavController(view).navigate(action, bundle)
    }

    fun navigateToFragment(view: View, navDirections: NavDirections) {
        Navigation.findNavController(view).navigate(navDirections)
    }

    fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun setCurrentUser(user: User?) {
        (requireActivity() as MainActivity).setCurrentUser(user)
    }

    fun getCurrentUser(): User? {
        return (requireActivity() as MainActivity).getCurrentUser()
    }

    fun checkPermission(permission: String): Boolean {
        return (requireActivity() as MainActivity).checkPermission(permission)
    }

    abstract fun showNewsDetail(news: News)
}