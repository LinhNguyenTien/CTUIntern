package com.example.ctuintern.ui.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.Review
import com.example.ctuintern.data.repository.ClassRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val classRepository: ClassRepository
): MainViewModel() {
    private val _reviews: MutableLiveData<List<Review>> = MutableLiveData(listOf())
    val reviews: MutableLiveData<List<Review>> get() = _reviews

    fun initView(teacherID: String) {
        getReviews(teacherID)
    }

    private fun getReviews(teacherID: String) {
        viewModelScope.launch {
            _reviews.value = classRepository.getReviewList(teacherID)
        }
    }
}