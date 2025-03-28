package com.refactorme.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.refactorme.demo.CountriesAdapter.CountryHolder
import com.refactorme.demo.databinding.ItemCountriesBinding

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

    fun setData(data: List<Country>) {
        items = data.map {
            ItemCountry(
                it.name.common,
                it.capital.firstOrNull().orEmpty(),
                it.flag.orEmpty()
            )
        }
        notifyDataSetChanged()
    }

    inner class CountryHolder(private val binding: ItemCountriesBinding) : ViewHolder(binding.root) {

        fun bind(item: ItemCountry) = with(binding) {
            name.text = item.name
            capital.text = item.capital
            flag.text = item.flag
        }
    }

    inner class ItemCountry(
        val name: String,
        val capital: String,
        val flag: String
    )
}