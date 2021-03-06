package hr.tvz.android.listapp.service

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import hr.tvz.android.listapp.model.ItemParcel
import hr.tvz.android.listapp.R

internal class CustomAdapter(private var itemsList: List<ItemParcel>, private val clickListener: (String) -> Unit) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTextView: TextView = view.findViewById(R.id.listItemView)
        var imageView: ImageView = view.findViewById(R.id.imageView6)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemTextView.text = item.toString()

        holder.itemTextView.setOnClickListener {
            holder.imageView.animate().setDuration(1000).rotationBy(360f).start()
            Handler().postDelayed({
                clickListener(itemsList[position].toString())
            }, 1000)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}