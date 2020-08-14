package com.kkco.nongenderedrestroomfinder.restrooms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.kkco.nongenderedrestroomfinder.data.Result
import com.kkco.nongenderedrestroomfinder.databinding.FragmentRestroomListBinding
import com.kkco.nongenderedrestroomfinder.di.Injectable
import com.kkco.nongenderedrestroomfinder.di.injectViewModel
import com.kkco.nongenderedrestroomfinder.ui.hide
import com.kkco.nongenderedrestroomfinder.ui.show
import javax.inject.Inject

class RestroomListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RestroomListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        val binding = FragmentRestroomListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = RestroomListAdapter()
        //TODO: addItemDecoration stuff... make it pretty
        binding.rvRestrooms.adapter = adapter

        subscribeUi(binding, adapter)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentRestroomListBinding, adapter: RestroomListAdapter) {
        viewModel.restrooms.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    result.data?.let { adapter.submitList(it) }
                }
                Result.Status.LOADING -> binding.progressBar.show()
                Result.Status.ERROR -> {
                    binding.progressBar.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    // companion object {
    //     fun newInstance(): RestroomListFragment {
    //         val args = Bundle()
    //
    //         val fragment = RestroomListFragment()
    //         fragment.arguments = args
    //         return fragment
    //     }
    // }
}