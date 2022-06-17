package hr.tvz.android.listapp.dataRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.tvz.android.listapp.model.Data

@Database(entities = [Data::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun todoDao(): DataDAO
}