package com.jan.products.ui.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jan.products.R
import com.jan.products.data.model.Product
import com.jan.products.util.CircleTransform
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private val context: Context,
    productsViewModel: ProductsViewModel,
    lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private val data = ArrayList<Product>()

    init {
        productsViewModel.products.observe(lifecycleOwner, Observer { products ->
            data.clear()
            if (products != null) {
                data.addAll(products)
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(private val context: Context, itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val txtName = itemView.findViewById<TextView>(R.id.txtName)
        private val txtDescription = itemView.findViewById<TextView>(R.id.txtDescription)

        fun bind(product: Product) {
            txtName.text = product.name
            txtDescription.text = product.description
            Picasso.with(context)
                .load(product.imageUrl)
                .centerCrop()
                .transform(CircleTransform())
                .fit()
                .into(image)
        }
    }
}