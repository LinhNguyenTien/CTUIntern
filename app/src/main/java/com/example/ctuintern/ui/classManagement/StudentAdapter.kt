package com.example.ctuintern.ui.classManagement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.StudentListItemBinding

class StudentAdapter(private val showStudentDetail:(Student) -> Unit): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private lateinit var students: List<Student>

    fun setDataset(students: List<Student>) {
        this.students = students
    }

    class StudentViewHolder(private val binding: StudentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student, position: Int, showStudentDetail:(Student) -> Unit) {
            binding.stt.text = (position+1).toString()
            binding.studentCode.text = student.studentID
            binding.studentName.text = student.userName

            binding.root.setOnClickListener {
                showStudentDetail(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            StudentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student, position, showStudentDetail)
    }
}