package org.d3if0119.pomodoroappnew.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if0119.pomodoroappnew.databinding.TasksFragmentBinding
import org.d3if0119.pomodoroappnew.db.TaskEntity
import org.d3if0119.pomodoroappnew.db.TasksDao
import org.d3if0119.pomodoroappnew.db.TasksDb

class TasksFragment : Fragment() {
    private val viewModel: TasksViewModel by lazy {
        val db = TasksDb.getInstance(requireContext())
        val factory = TasksViewModelFactory(db.taskDao())
        ViewModelProvider(this, factory)[TasksViewModel::class.java]

    }
    private lateinit var taskDao: TasksDao
    private lateinit var myAdapter: TasksAdapter
    private lateinit var binding: TasksFragmentBinding
    private val itemList = mutableListOf<String>()
    private val adapter by lazy { ArrayAdapter(requireContext(), android.R.layout.simple_list_item_multiple_choice, itemList) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TasksFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = TasksAdapter(viewModel::deleteTask)
        with(binding.listView){
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
            Log.d("delete", "data berhasil dihapus")
        }

        viewModel.tasksLiveData.observe(viewLifecycleOwner) {
            binding.emptyView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        }

        binding.add.setOnClickListener {
            val task = binding.addText.editText?.text.toString().trim()
            if (task.isNotEmpty()) {
                val newTask = TaskEntity(name = task)
                viewModel.insertTask(newTask)
                Log.d("insert", "udah masuk nih")
                binding.addText.editText?.text?.clear()
                myAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Task tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.tasksLiveData.observe(viewLifecycleOwner) { tasks ->
            itemList.clear()
            itemList.addAll(tasks.map { it.name })
            adapter.notifyDataSetChanged()
        }
    }
}