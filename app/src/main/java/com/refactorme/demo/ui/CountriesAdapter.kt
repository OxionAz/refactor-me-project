package com.refactorme.demo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.refactorme.demo.ui.CountriesAdapter.CountryHolder
import com.refactorme.demo.databinding.ItemCountriesBinding
import com.refactorme.demo.ui.entities.ItemCountry

class CountriesAdapter : Adapter<CountryHolder>() {

    private var items = listOf<ItemCountry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        return LayoutInflater.from(parent.context)
            .let { ItemCountriesBinding.inflate(it, parent, false) }
            .let(::CountryHolder)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    // we can also use DiffUtils for lists with significant size
    fun setData(data: List<ItemCountry>) {
        items = data
        notifyDataSetChanged()
    }

    inner class CountryHolder(
        private val binding: ItemCountriesBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: ItemCountry) = with(binding) {
            name.text = item.name
            capital.text = item.capital
            flag.text = item.flag
        }
    }
}