package com.aplikasi.apptokosi01.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.apptokosi01.Callbackinterface
import com.aplikasi.apptokosi01.R
import com.aplikasi.apptokosi01.response.cart.Cart
import com.aplikasi.apptokosi01.response.produk.Produk
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class TransaksiAdapter (val listProduk: List<Produk>): RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    var callbackinterface:Callbackinterface? = null
    var total : Int = 0
    var cart :ArrayList<Cart> = arrayListOf<Cart>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_supplier, parent, false)
        return TransaksiAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransaksiAdapter.ViewHolder, position: Int) {
        val produk = listProduk[position]
        holder.txtNamaProduk.text = produk.nama
        holder.txtStoktrans.text = produk.stok

        val localeID = Locale("in","ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        holder.txtHarga.text = numberFormat.format(produk.harga.toDouble()).toString().toString()

        holder.btnPlus.setOnClickListener {

            val old_value = holder.txtQty.text.toString().toInt()
            val new_value = old_value+1

            holder.txtQty.setText(new_value.toString())

            total = total + produk.harga.toString().toInt()

            val index = cart.indexOfFirst { it.id == produk.id.toInt() }.toInt()

            if(index!=-1){
                cart.removeAt(index)
            }

            val itemcart = Cart(produk.id.toInt(),produk.harga.toInt(),new_value)
            cart.add(itemcart)

            callbackinterface?.passResultCallback(total.toString(),cart)

        }

        holder.btnMinus.setOnClickListener {
            val old_value = holder.txtQty.text.toString().toInt()
            val new_value = old_value-1


            val index = cart.indexOfFirst { it.id == produk.id.toInt() }.toInt()

            if(index!=-1){
                cart.removeAt(index)
            }

            if (new_value>=0){
                holder.txtQty.setText(new_value.toString())
                total = total - produk.harga.toString().toInt()


            }

            if (new_value>=1){
                val itemcart = Cart(produk.id.toInt(),produk.harga.toInt(),new_value)
                cart.add(itemcart)
            }

            callbackinterface?.passResultCallback(total.toString(),cart)

        }

    }
    override fun getItemCount(): Int {
        return listProduk.size
    }

    class ViewHolder (itemViem: View) : RecyclerView.ViewHolder(itemViem) {
        val txtNamaProduk = itemViem.findViewById(R.id.txtNamaProduk) as TextView
        val txtHarga = itemViem.findViewById(R.id.txtHarga) as TextView
        val txtQty = itemViem.findViewById(R.id.txtQty) as TextView
        val txtStoktrans = itemViem.findViewById(R.id.txtStoktrans) as TextView
        val btnPlus = itemViem.findViewById(R.id.btnPlus) as ImageButton
        val btnMinus = itemViem.findViewById(R.id.btnMinus) as ImageButton
    }

}