package hr.tvz.android.listapp.dataRoom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hr.tvz.android.listapp.model.Data

@Dao
interface DataDAO {
    @Query("SELECT * FROM Data")
    fun getAll(): List<Data>

    @Insert
    fun insertAll(vararg data: Data)

    @Delete
    fun delete(data: Data)
}