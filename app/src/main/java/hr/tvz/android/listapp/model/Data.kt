package hr.tvz.android.listapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Data(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name="name") val name: String?
) {
}