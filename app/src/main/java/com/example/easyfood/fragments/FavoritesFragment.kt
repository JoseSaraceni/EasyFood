package com.example.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.activites.MainActivity
import com.example.easyfood.adaptors.MealsAdapter
import com.example.easyfood.databinding.FragmentFavoritesBinding
import com.example.easyfood.videoModel.HomeViewModel
import com.example.easyfood.videoModel.MealViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class favoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealViewModel: MealViewModel
    private lateinit var favoritesAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()
        setupSwipeToDelete()
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = MealsAdapter()
        binding.rvFavorites.apply{
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites(){
        viewModel.observeFavoritesMealsLiveData().observe(viewLifecycleOwner, Observer {meals->
            favoritesAdapter.differ.submitList(meals)
        })

    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val deletedMeal = favoritesAdapter.getMealAt(position)

                    // Remove do banco de dados
                    viewModel.deleteMeal(deletedMeal)

                    // Mostra a Snackbar com opção de desfazer
                    Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            viewModel.insertMeal(deletedMeal)  // Reinsere o item no banco de dados
                        }
                    }.show()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvFavorites)
    }
}