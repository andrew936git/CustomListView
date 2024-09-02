package com.example.customlistview

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context: Context, list: MutableList<Product>):
    ArrayAdapter<Product>(context, R.layout.list_item, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val product = getItem(position)
        if (view == null) {
            view = LayoutInflater
                .from(context)
                .inflate(R.layout.list_item, parent, false)
        }

        val imageViewIV = view?.findViewById<ImageView>(R.id.imageViewIV)
        val nameTextViewTV = view?.findViewById<TextView>(R.id.nameTextViewTV)
        val priceTextViewTV = view?.findViewById<TextView>(R.id.priceTextViewTV)

        imageViewIV?.setImageURI(Uri.parse(product?.image))
        nameTextViewTV?.text = product?.name
        priceTextViewTV?.text = product?.price
        return view!!
    }
}