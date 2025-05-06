package com.example.easyfood.fragments

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easyfood.R
import com.example.easyfood.activites.MainActivity
import com.example.easyfood.adaptors.MealsAdapter
import com.example.easyfood.databinding.FragmentFavoritesBinding
import com.example.easyfood.databinding.FragmentSearchBinding
import com.example.easyfood.videoModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    internal lateinit var binding: FragmentSearchBinding
    internal lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerViewAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override  fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        prepareRecyclerView()

        binding.imgSearchArrow.setOnClickListener { searchMeals() }

        observeSearchedMealsLiveData()

        var searchJob: Job?=null
        binding.edSearchBox.doOnTextChanged { text, _, _, _ ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(text.toString())
            }
        }

    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchMealsLiveData().observe (viewLifecycleOwner, Observer{ mealsList ->
            searchRecyclerViewAdapter.differ.submitList(mealsList)

        } )
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter = searchRecyclerViewAdapter
        }
    }

    private fun SearchFragment.searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

}
