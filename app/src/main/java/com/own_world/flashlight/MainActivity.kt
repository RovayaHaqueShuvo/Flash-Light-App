package com.own_world.flashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.own_world.flashlight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.flashlight.setOnClickListener {

            if (binding.flashlight.isActivated) {
                binding.button.setText(getString(R.string.turn_off))
                binding.flashlight.setImageResource(R.drawable.off)
                changelightstate(true)
            } else {
                binding.button.setText(getString(R.string.turn_on))
                binding.flashlight.setImageResource(R.drawable.on)
                changelightstate(false)
            }


        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changelightstate(b: Boolean) {

        var cameraManager = if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSystemService(Context.CAMERA_SERVICE) as CameraManager
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
        var cameraId: String? = null


        try {
            // true sets the torch in OFF mode
            cameraManager!!.setTorchMode(cameraId!!, b)

            // Inform the user about the flashlight
            // status using Toast message
            Toast.makeText(this@MainActivity, "Flashlight is turned OFF", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onStart() {
        super.onStart()
        binding.button.setText(R.string.turn_on)
        binding.flashlight.setImageResource(R.drawable.off)
    }


}