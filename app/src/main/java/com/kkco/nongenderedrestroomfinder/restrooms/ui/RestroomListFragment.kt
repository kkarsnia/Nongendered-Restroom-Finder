package com.kkco.nongenderedrestroomfinder.restrooms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kkco.nongenderedrestroomfinder.databinding.FragmentRestroomListBinding
import com.kkco.nongenderedrestroomfinder.di.Injectable
import com.kkco.nongenderedrestroomfinder.di.injectViewModel
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
        //TODO: add repo stuff
    }
}