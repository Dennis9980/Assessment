package org.d3if0119.pomodoroappnew.ui.recomendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if0119.pomodoroappnew.databinding.RecomendationFragmentBinding
import org.d3if0119.pomodoroappnew.network.ApiStatus

class RecomendationFragment : Fragment(){
    private val viewModel: RecomendationViewModel by lazy {
        ViewModelProvider(this)[RecomendationViewModel::class.java]
    }
    private lateinit var binding: RecomendationFragmentBinding
    private lateinit var myAdapter: RecomendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecomendationFragmentBinding.inflate(layoutInflater, container, false)
        myAdapter = RecomendationAdapter()
        with(binding.recyclerview){
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner){
            myAdapter.updateData(it)
        }
        viewModel.getStatus().observe(viewLifecycleOwner){
            statusProgress(it)
        }
    }

    private fun statusProgress(status: ApiStatus){
        when (status){
            ApiStatus.LOADING ->{
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS ->{
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED ->{
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}