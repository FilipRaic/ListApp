package hr.tvz.android.listapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemParcel(val item: String) : Parcelable {
    override fun toString(): String {
        return item
    }
}