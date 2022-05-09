package hr.tvz.android.listapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.widget.Toast

class WifiStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)) {
            WifiManager.WIFI_STATE_ENABLED -> {
                Toast.makeText(context,"WiFi is ON", Toast.LENGTH_LONG).show()
            }
            WifiManager.WIFI_STATE_DISABLED -> {
                Toast.makeText(context,"WiFi is OFF", Toast.LENGTH_LONG).show()
            }
        }
    }
}