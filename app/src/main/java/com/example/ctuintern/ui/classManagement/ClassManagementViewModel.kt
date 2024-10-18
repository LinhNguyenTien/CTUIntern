package com.example.ctuintern.ui.classManagement
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.Class
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Task
import com.example.ctuintern.data.repository.ClassRepository
import com.example.ctuintern.data.repository.UserRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ClassManagementViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val classRepository: ClassRepository
): MainViewModel() {
    private val _classes: MutableLiveData<List<Class>> = MutableLiveData(listOf())
    val classes: MutableLiveData<List<Class>> get() = _classes

    private val _students: MutableLiveData<List<Student>> = MutableLiveData(listOf())
    val students: MutableLiveData<List<Student>> get() = _students

    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData(listOf())
    val tasks: MutableLiveData<List<Task>> get() = _tasks

    fun initView(teacherID: String) {
        getClasses(teacherID)
    }

    fun initDetailClassView(classID: String) {
        getStudentList(classID)
        getTaskList(classID)
    }

    fun getClasses(teacherID: String) {
        viewModelScope.launch {
            _classes.value = userRepository.getClasses(teacherID)
        }
    }

    fun getStudentList(classID: String) {
        viewModelScope.launch {
            _students.value = classRepository.getStudentList(classID)
        }
    }

    fun getTaskList(classID: String) {
        viewModelScope.launch {
            _tasks.value = classRepository.getTaskList(classID)
        }
    }


}