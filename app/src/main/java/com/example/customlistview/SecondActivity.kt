package com.example.customlistview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.IOException


class SecondActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    private var bitmap: Bitmap? = null
    private val productsList = mutableListOf<Product>()

    private lateinit var toolbar: Toolbar
    private lateinit var listViewLV: ListView
    private lateinit var nameEditTextET: EditText
    private lateinit var priceEditTextET: EditText
    private lateinit var editImageViewIV: ImageView
    private lateinit var saveButtonBT: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        init()
    }
    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Добавление продуктов"

        listViewLV = findViewById(R.id.listViewLV)
        nameEditTextET = findViewById(R.id.nameEditTextET)
        priceEditTextET = findViewById(R.id.priceEditTextET)
        editImageViewIV = findViewById(R.id.editImageViewIV)
        saveButtonBT = findViewById(R.id.saveButtonBT)

        editImageViewIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveButtonBT.setOnClickListener{
            val productName = nameEditTextET.text.toString()
            val productPrice = priceEditTextET.text.toString()
            val productImage = bitmap
            val product = Product(productName, productPrice, productImage)
            productsList.add(product)

            val listAdapter = ListAdapter(this@SecondActivity, productsList)
            listViewLV.adapter = listAdapter
            listAdapter.notifyDataSetChanged()
            nameEditTextET.text.clear()
            priceEditTextET.text.clear()
            editImageViewIV.setImageResource(R.drawable.ic_food)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.exit_menu -> {
                finishAffinity()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageViewIV = findViewById(R.id.editImageViewIV)
        when(requestCode){
            GALLERY_REQUEST -> {
                if (resultCode === RESULT_OK){
                    val selectedImage: Uri? = data?.data
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    }catch (e: IOException){
                        e.printStackTrace()
                    }
                    editImageViewIV.setImageBitmap(bitmap)
                }
            }
        }
    }
}