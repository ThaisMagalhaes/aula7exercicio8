package com.example.aula7exercicio8

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnCapturar: Button
    private lateinit var btnEscolherImagem: Button
    private lateinit var imgFoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCapturar = findViewById(R.id.btnCapturar)
        btnEscolherImagem = findViewById(R.id.btnEscolherImagem)
        imgFoto = findViewById(R.id.imgFoto)

        btnCapturar.setOnClickListener {
            capturePhoto()
        }

        btnEscolherImagem.setOnClickListener {
            chooseImageFromGallery()
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            imgFoto.setImageBitmap(it)
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val intentData: Intent? = result.data
            val selectedImageUri = intentData?.data
            imgFoto.setImageURI(selectedImageUri)
        }
    }

    private fun capturePhoto() {
        takePictureLauncher.launch(null)
    }

    ///metodo para poder abrir a galeria
    private fun chooseImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(galleryIntent)
    }
}

