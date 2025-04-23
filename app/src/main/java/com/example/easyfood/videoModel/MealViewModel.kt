package com.example.easyfood.videoModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.pojo.Meal
import com.example.easyfood.pojo.MealList
import com.example.easyfood.retrofit.RetrofilInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel():ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    
    fun getmealDetailsLiveData(id:String){

        RetrofilInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}