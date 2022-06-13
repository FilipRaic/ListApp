package hr.tvz.android.listapp

import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import hr.tvz.android.listapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val itemsList = ArrayList<ItemParcel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var wifiState: WifiStateReceiver
    private lateinit var dataDAO: DataDAO
    var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataBase = Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "database-name"
        )
        .allowMainThreadQueries()
        .build()

        dataDAO = dataBase.todoDao()

        if (findViewById<LinearLayout>(R.id.detail_container) != null) {
            twoPane = true
        }

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(ContentValues.TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.getResult().toString()

                // Log and toast
                Toast.makeText(this, token, Toast.LENGTH_LONG).show()
                Log.d(ContentValues.TAG, token)
            })

        title = "Lego Bricks"
        binding.listPane.layoutManager = LinearLayoutManager(applicationContext)
        binding.listPane.adapter = CustomAdapter(itemsList) {
            if (twoPane) {
                supportFragmentManager.commit {
                    replace<ItemFragment>(R.id.detail_container, it)
                    setReorderingAllowed(true)
                }
            } else {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("Lego", it)
                startActivity(intent)
            }
        }
        prepareItems()

        wifiState = WifiStateReceiver()
        Firebase.messaging.isAutoInitEnabled = true

        IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION).also {
            registerReceiver(wifiState, it)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiState)
    }

    private fun prepareItems() {
        val array = dataDAO.getAll()

        if (array.count() != 0) {
            for (item in array) {
                itemsList.add(ItemParcel(item.name.toString()))
            }
        }
    }
}