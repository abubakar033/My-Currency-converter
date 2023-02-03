package com.applications.currencyconverter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applications.currencyconverter.data.db.entities.CurrencyEntities
import com.applications.currencyconverter.databinding.RecyclerviewItemBinding

class CurrencyRVAdapter(private val list: List<CurrencyEntities>, private var amount :String) :
    RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(list) {

            holder.binding.tvPairName.text = this[position].key
            holder.binding.tvLiveRate.text = this[position].value.toString()
            holder.binding.tvConvertAmount.text = (this[position].value.toString().toDouble()*getAmount().toDouble()).toString()

        }


    }

    fun getAmount():String{
         if (!amount.isNullOrEmpty()){
            return amount
        }
        else{
            return "1"
        }
    }

    fun setUpdatedValue(value :String){
        amount = value

    }
}