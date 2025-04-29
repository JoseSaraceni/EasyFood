package com.example.easyfood.db

import androidx.resourceinspection.annotation.Attribute
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertor {
    fun fromAnyToString(attribute: Any?): String{
     if (attribute == null)
         return ""
     return attribute as String
    }

    @TypeConverters
    fun fromStringToAny(attribute: String?):Any{
        if (attribute == null)
            return ""
        return attribute
    }
}