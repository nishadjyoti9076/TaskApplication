package com.example.firebaseapp.crudwithoutdi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapplication.R
import com.example.taskapplication.models.Products

class ProductAdapter(private val context: Context,private var productList :List<Products>) : RecyclerView.Adapter<ProductAdapter.ProductVieHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductVieHolder {
       return ProductVieHolder(LayoutInflater.from(parent.context).inflate(R.layout.each_row,parent,false))
    }

    override fun onBindViewHolder(holder: ProductVieHolder, position: Int) {

        val products : Products =productList[position]
        holder.text1.text=products.id.toString()
        holder.text2.text=products.title.toString()
    }

    override fun getItemCount(): Int {
     return productList.size
    }

    class ProductVieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val text1 : TextView=itemView.findViewById(R.id.text1)
        val text2 : TextView=itemView.findViewById(R.id.text2)


    }

    fun setData(list: List<Products>){
        this.productList=list
        notifyDataSetChanged()
    }
}