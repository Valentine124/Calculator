package com.valentine.calculator.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.valentine.calculator.internet.Currencies

@Database(entities = [Currencies::class], version = 1, exportSchema = false)
abstract class ConverterDb: RoomDatabase() {
    abstract fun converterDao(): ConverterDao

    companion object {
        private var INSTANCE: ConverterDb? = null

        fun getInstance(context: Context): ConverterDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    ConverterDb::class.java, "converter_db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}