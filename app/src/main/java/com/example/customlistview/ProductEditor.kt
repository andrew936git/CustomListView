package com.example.customlistview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ProductEditor : AppCompatActivity() {

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
        var check = intent.extras?.getBoolean("check")

        val name = product?.name
        val price = product?.price
        val description = product?.description
        val image: Uri? = Uri.parse(product?.image)

        editImageProductIV.setImageURI(image)
        editNameProductET.setText(name)
        editPriceProductET.setText(price)
        editDescriptionProductET.setText(description)
        saveEditButtonBT.setOnClickListener {
            val newProduct = Product(
                editNameProductET.text.toString(),
                editPriceProductET.text.toString(),
                editDescriptionProductET.text.toString(),
                product?.image
            )
            val productList = products as MutableList<Product>
            if (item != null) {
                swap(item, newProduct, productList)
            }
            check = false
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
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.exit_menu -> {
                finishAffinity()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}