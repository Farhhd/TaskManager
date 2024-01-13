package com.example.myapplicatio.ui.home



import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.myapplicatio.App
import com.example.myapplicatio.R
import com.example.myapplicatio.databinding.FragmentHomeBinding
import com.example.myapplicatio.ui.home.adapter.TaskAdapter
import com.example.myapplicatio.ui.model.Task
import com.example.myapplicatio.ui.task.TaskFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = TaskAdapter(this::onLongClick,this::onClick)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        setData()

        fab.setOnClickListener {

            findNavController().navigate(R.id.taskFragment)
        }
    }

    private fun onLongClick(task: Task) {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle(getString(R.string.delete))
        alert.setPositiveButton(getString(R.string.yes)) { _, _ ->
            App.db.taskDao().delete(task)
            setData()
        }
        alert.setNegativeButton(getString(R.string.no))
        { d, _ ->
            d.cancel()
        }
        alert.create().show()
    }
    private fun onClick(task: Task) {
        findNavController().navigate(R.id.taskFragment, bundleOf(TASK_UPDATE_KEY to task))
        setData()
    }
    private fun setData() {
        val tasks = App.db.taskDao().getAll()
        adapter.addTasks(tasks)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        const val TASK_UPDATE_KEY = "task.edit.key"
    }
}