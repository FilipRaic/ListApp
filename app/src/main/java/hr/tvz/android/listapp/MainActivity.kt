package hr.tvz.android.listapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hr.tvz.android.listapp.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val itemsList = ArrayList<ItemParcel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var wifiState: WifiStateReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Lego Bricks"
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = CustomAdapter(itemsList){
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Lego", it)
            startActivity(intent)
        }
        prepareItems()

        wifiState = WifiStateReceiver()

        IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION).also {
            registerReceiver(wifiState, it)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiState)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareItems() {
        for (i in 1..10) {
            itemsList.add(ItemParcel("Lego Brick 1x$i"))
        }
    }
}