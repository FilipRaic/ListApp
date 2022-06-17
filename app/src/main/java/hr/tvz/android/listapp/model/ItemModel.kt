package hr.tvz.android.listapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemModel(var id: Long, var name: String, var description: String): Parcelable