package com.example.hammersystemstesttask.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hammersystemstesttask.data.model.entity.CategoryEntity
import com.example.hammersystemstesttask.data.model.entity.FoodEntity

@Database(
    entities = [
        FoodEntity::class,
        CategoryEntity::class],
    version = 2
)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun foodDao(): FoodDao

    companion object {
        const val DATABASE_NAME = "menu_database"
    }
}