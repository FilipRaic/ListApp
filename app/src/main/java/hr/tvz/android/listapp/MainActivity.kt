package hr.tvz.android.listapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hr.tvz.android.listapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val itemsList = ArrayList<String>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "List of Raić"
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = CustomAdapter(itemsList)
        prepareItems()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareItems() {
        for (i in 1..25) {
            itemsList.add("Item $i")
        }
        CustomAdapter(itemsList).notifyDataSetChanged()
    }
}