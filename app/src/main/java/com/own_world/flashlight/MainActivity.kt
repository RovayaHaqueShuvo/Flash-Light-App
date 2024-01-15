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
            onFlashlight()
        }

        binding.flashlight.setOnLongClickListener {
            offFlashlight()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun onFlashlight(): Boolean {
        var cameraManager: CameraManager? = null
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            var cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, true)
          /*  if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                cameraManager.setTorchMode(cameraId, true)
            }*/
            Toast.makeText(this, "Flashlight is on", Toast.LENGTH_SHORT).show()

        }catch (e:CameraAccessException ){

            Toast.makeText(this, "Exception : " + e.message, Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun offFlashlight(): Boolean {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

            try {
                val cameraId = cameraManager.cameraIdList[0]
                cameraManager.setTorchMode(cameraId, false)
                Toast.makeText(this, "Flashlight is on", Toast.LENGTH_SHORT).show()
            }catch (e : CameraAccessException){
                Toast.makeText(this, "Exception : " + e.message, Toast.LENGTH_SHORT).show()
            }

        }

        return true
    }



}