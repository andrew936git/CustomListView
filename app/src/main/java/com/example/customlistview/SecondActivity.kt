package com.example.customlistview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SecondActivity : AppCompatActivity() {

    private var product: Product? = null
    private var listAdapter: ListAdapter? = null
    private var item: Int? = null
    private val GALLERY_REQUEST = 302
    private var photoUri: Uri? = null
    private var productsList = mutableListOf<Product>()

    private lateinit var toolbar: Toolbar
    private lateinit var listViewLV: ListView
    private lateinit var nameEditTextET: EditText
    private lateinit var priceEditTextET: EditText
    private lateinit var descriptionEditTextET: EditText
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
        descriptionEditTextET = findViewById(R.id.descriptionEditTextET)
        editImageViewIV = findViewById(R.id.editImageViewIV)
        editImageViewIV.setImageResource(R.drawable.ic_food)
        saveButtonBT = findViewById(R.id.saveButtonBT)

        editImageViewIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveButtonBT.setOnClickListener{
            val productName = nameEditTextET.text.toString()
            val productPrice = priceEditTextET.text.toString()
            val productDescription = descriptionEditTextET.text.toString()
            val productImage = photoUri.toString()
            product = Product(productName, productPrice, productDescription, productImage)
            productsList.add(product!!)

            listAdapter = ListAdapter(this@SecondActivity, productsList)
            listViewLV.adapter = listAdapter
            listAdapter!!.notifyDataSetChanged()
            editImageViewIV.setImageResource(R.drawable.ic_food)
            nameEditTextET.text.clear()
            priceEditTextET.text.clear()
            descriptionEditTextET.text.clear()
            photoUri = null
        }

        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener{ _, _, position, _ ->
                item = position
                val product = listAdapter!!.getItem(position)
                val intent = Intent(this, ProductEditor::class.java)
                intent.putExtra("product", product)
                intent.putExtra("productsList", this.productsList as ArrayList<Product>)
                intent.putExtra("position", item)
                startActivity(intent)
            }
    }

    override fun onResume() {
        super.onResume()
        val check = intent.extras?.getInt("newCheck")
        val checkBack = intent.extras?.getInt("checkBack")
        if (check == 1){
            productsList = intent.getParcelableArrayListExtra("products")!!
            listAdapter = ListAdapter(this, productsList)

        }
        if (checkBack == 2){
            productsList = intent.getParcelableArrayListExtra("productsBack")!!
            listAdapter = ListAdapter(this, productsList)
        }
        listViewLV.adapter = listAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exit_menu -> {
                Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageViewIV = findViewById(R.id.editImageViewIV)
        when(requestCode){
            GALLERY_REQUEST -> {
                if (resultCode === RESULT_OK){
                    photoUri = data?.data
                    editImageViewIV.setImageURI(photoUri)
                }
            }
        }
    }
}