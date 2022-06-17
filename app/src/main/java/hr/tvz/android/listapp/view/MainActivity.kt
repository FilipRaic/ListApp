package hr.tvz.android.listapp.view

import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import hr.tvz.android.listapp.*
import hr.tvz.android.listapp.service.CustomAdapter
import hr.tvz.android.listapp.model.ItemModel
import hr.tvz.android.listapp.backendData.ServiceGenerator
import hr.tvz.android.listapp.backendData.ServiceInterface
import hr.tvz.android.listapp.dataRoom.DataDAO
import hr.tvz.android.listapp.dataRoom.Database
import hr.tvz.android.listapp.databinding.ActivityMainBinding
import hr.tvz.android.listapp.model.ItemFragment
import hr.tvz.android.listapp.model.ItemParcel
import hr.tvz.android.listapp.receiver.WifiStateReceiver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val itemsList = ArrayList<ItemParcel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var wifiState: WifiStateReceiver
    private lateinit var dataDAO: DataDAO
    var twoPane = false
    val API_URL = "http://10.0.2.2:8080/"
    lateinit var models: MutableList<ItemModel>

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
        val client: ServiceInterface = ServiceGenerator().createService(
            ServiceInterface::class.java,
            API_URL
        )

        val items: Call<MutableList<ItemModel>> = client.fetchModel()

        items.enqueue(object : Callback<MutableList<ItemModel>> {
            override fun onResponse(
                call: Call<MutableList<ItemModel>>,
                response: Response<MutableList<ItemModel>>
            ) {
                if (response.isSuccessful) {
                    models = response.body()!!
                    for (model in models) {
                        itemsList.add(ItemParcel(model.name))
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<ItemModel>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
        Thread.sleep(1000)


        /*val array = dataDAO.getAll()

        if (array.count() != 0) {
            for (item in array) {
                itemsList.add(ItemParcel(item.name.toString()))
            }
        }*/
    }
}