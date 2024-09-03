package com.example.customlistview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ProductEditor : AppCompatActivity() {

    val GALLERY_REQUEST = 302
    private var editPhotoUri: Uri? = null
    private lateinit var toolbarProductEditor: Toolbar
    private lateinit var editImageProductIV: ImageView
    private lateinit var editNameProductET: EditText
    private lateinit var editPriceProductET: EditText
    private lateinit var editDescriptionProductET: EditText
    private lateinit var saveEditButtonBT: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_editor)
        init()
    }

    private fun init() {
        toolbarProductEditor = findViewById(R.id.toolbarProductEditor)
        setSupportActionBar(toolbarProductEditor)
        title = "Редоктирование продукта"

        editImageProductIV = findViewById(R.id.editImageProductIV)
        editNameProductET = findViewById(R.id.editNameProductET)
        editPriceProductET = findViewById(R.id.editPriceProductET)
        editDescriptionProductET = findViewById(R.id.editDescriptionProductET)
        saveEditButtonBT = findViewById(R.id.saveEditButtonBT)

        val product: Product? = intent?.getParcelableExtra("product")
        val products = intent.getParcelableArrayListExtra<Product>("productsList")
        val item = intent.extras?.getInt("position")
        var check = 0

        val name = product?.name
        val price = product?.price
        val description = product?.description
        editPhotoUri = Uri.parse(product?.image)

        editImageProductIV.setImageURI(editPhotoUri)
        editNameProductET.setText(name)
        editPriceProductET.setText(price)
        editDescriptionProductET.setText(description)

        editImageProductIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveEditButtonBT.setOnClickListener {
            val newProduct = Product(
                editNameProductET.text.toString(),
                editPriceProductET.text.toString(),
                editDescriptionProductET.text.toString(),
                editPhotoUri.toString()
            )
            val productList = products as MutableList<Product>
            if (item != null) {
                swap(item, newProduct, productList)
            }
            check = 1
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("products", productList as ArrayList<Product>)
            intent.putExtra("newCheck", check)
            startActivity(intent)
            finish()
        }
    }

    private fun swap(item: Int, product: Product, list: MutableList<Product>) {
        list.add(item + 1, product)
        list.removeAt(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_editor_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit_menu -> {
                Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
                finish()
            }
            R.id.back_menu ->{
                val intentBack = Intent(this, SecondActivity::class.java)
                val productBack: Product? = intent?.getParcelableExtra("product")
                val productsBack = intent.getParcelableArrayListExtra<Product>("productsList")
                val itemBack = intent.extras?.getInt("position")

                swap(itemBack!!, productBack!!, productsBack!!)

                intentBack.putExtra("productsBack", productsBack as ArrayList<Product>)
                val check = 2
                intentBack.putExtra("checkBack", check)
                startActivity(intentBack)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageProductIV = findViewById(R.id.editImageProductIV)
        when(requestCode){
            GALLERY_REQUEST -> {
                if (resultCode === RESULT_OK){
                    editPhotoUri = data?.data
                    editImageProductIV.setImageURI(editPhotoUri)
                }
            }
        }
    }
}