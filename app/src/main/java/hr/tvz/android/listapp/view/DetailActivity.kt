package hr.tvz.android.listapp.view

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.tvz.android.listapp.R

class DetailActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.super_mario_world)
        mediaPlayer.start()

        val text = findViewById<TextView>(R.id.textToChange)
        text.text = intent.getStringExtra("Lego")

        val img: ImageView = findViewById(R.id.imageLink)

        img.setOnClickListener {
            val url = "https://lstore.hr/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        val share: FloatingActionButton = findViewById(R.id.floatingActionButton)

        share.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setMessage("Share " + intent.getStringExtra("Lego") + "?")
                .setPositiveButton("Yes") { dialog, which ->
                    Intent().also { intent ->
                        intent.setAction("com.example.broadcast.MY_NOTIFICATION")
                        intent.putExtra("data", "Nothing to see here, move along.")
                        sendBroadcast(intent)
                    }

                    Toast.makeText(
                        applicationContext,
                        "Building...",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .setNegativeButton("No") { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        "Alright then",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .create()
                .show()
        }
    }

    override fun onBackPressed() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        super.onBackPressed()
    }
}