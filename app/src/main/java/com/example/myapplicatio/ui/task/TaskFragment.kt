package com.example.myapplicatio.ui.task


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.myapplicatio.App
import com.example.myapplicatio.R
import com.example.myapplicatio.databinding.FragmentTaskBinding
import com.example.myapplicatio.ui.home.HomeFragment
import com.example.myapplicatio.ui.model.Task

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        val task = arguments?.getSerializable(HomeFragment.TASK_UPDATE_KEY) as Task?
        if (task != null) {
            binding.btnSave.text = (getString(R.string.update))
            binding.etTitle.setText(task.title)
            binding.etDesc.setText(task.desc)
        }
        binding.btnSave.setOnClickListener {

            if (task == null) {
                save()

            } else {

                updateTask(task)
            }
            findNavController().navigateUp()
        }

    }

    private fun updateTask(task: Task) {
        App.db.taskDao().update(
            task.copy(
                title = binding.etTitle.text.toString(),
                desc = binding.etDesc.text.toString()
            )
        )
    }

    private fun save() {
        val task =
            Task(
                title = binding.etTitle.text.toString(),
                desc = binding.etDesc.text.toString()
            )
        App.db.taskDao().insert(task)
    }

    companion object {
        const val TASK_REQUEST_KEY = "task.request.key"
        const val TASK_KEY = "task.key"
    }
}