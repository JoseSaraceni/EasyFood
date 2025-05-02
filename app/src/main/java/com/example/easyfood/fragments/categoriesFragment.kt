package com.example.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.activites.MainActivity
import com.example.easyfood.adaptors.CategoriesAdapter
import com.example.easyfood.adaptors.FavoritesMealsAdapter
import com.example.easyfood.databinding.FragmentCategoriesBinding
import com.example.easyfood.videoModel.HomeViewModel

class categoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        observeCategoies()
    }

    private fun observeCategoies() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer{categories ->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply{
            layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }
}
