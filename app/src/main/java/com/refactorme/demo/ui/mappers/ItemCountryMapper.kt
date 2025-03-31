package com.refactorme.demo.ui.mappers

import com.refactorme.demo.data.dto.Country
import com.refactorme.demo.ui.entities.ItemCountry

class ItemCountryMapper {

    fun map(item: Country) = ItemCountry(
        item.name.common,
        item.capital.firstOrNull().orEmpty(),
        item.flag.orEmpty()
    )

    fun mapList(list: List<Country>) = list.map(this::map)
}