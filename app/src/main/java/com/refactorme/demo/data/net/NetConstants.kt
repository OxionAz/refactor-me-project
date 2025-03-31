package com.refactorme.demo.data.net

import com.refactorme.demo.BuildConfig

object NetConstants {
    const val BASE_API_PATH = "${BuildConfig.API_SERVER}/v3.1/"

    const val ALL_COUNTRIES_PATH = "all"
    const val ALL_COUNTRIES_DEF_FIELDS = "flag,name,latlng,languages,capital"
}