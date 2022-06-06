package hr.tvz.android.listapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Data(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name="name") val name: String?
) {
}