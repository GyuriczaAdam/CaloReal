package hu.adikaindustries.tracker_data.remote.DTO

import com.squareup.moshi.Json

data class Product(  @field:Json(name = "image_front_thumb_url")
                     val imageFrontThumbUrl: String?,
                     val nutriments: Nutriments,
                     @field:Json(name = "product_name")
                     val productName: String?)
