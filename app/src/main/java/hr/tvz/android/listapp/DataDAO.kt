package hr.tvz.android.listapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDAO {
    @Query("SELECT * FROM Data")
    fun getAll(): List<Data>

    @Insert
    fun insertAll(vararg data: Data)

    @Delete
    fun delete(data: Data)
}