package com.dovene.androidhttprequestsharedprefences

import com.google.gson.annotations.SerializedName


data class PhotoApiResponse (
    @SerializedName("page"          ) var page         : Int?              = null,
    @SerializedName("per_page"      ) var perPage      : Int?              = null,
    @SerializedName("photos"        ) var photos       : ArrayList<Photos> = arrayListOf(),
    @SerializedName("total_results" ) var totalResults : Int?              = null,
    @SerializedName("next_page"     ) var nextPage     : String?           = null
)

data class Photos (
    @SerializedName("id"               ) var id              : Int?     = null,
    @SerializedName("width"            ) var width           : Int?     = null,
    @SerializedName("height"           ) var height          : Int?     = null,
    @SerializedName("url"              ) var url             : String?  = null,
    @SerializedName("photographer"     ) var photographer    : String?  = null,
    @SerializedName("photographer_url" ) var photographerUrl : String?  = null,
    @SerializedName("photographer_id"  ) var photographerId  : Int?     = null,
    @SerializedName("avg_color"        ) var avgColor        : String?  = null,
    @SerializedName("src"              ) var src             : Src?     = Src(),
    @SerializedName("liked"            ) var liked           : Boolean? = null,
    @SerializedName("alt"              ) var alt             : String?  = null
)

data class Src (
    @SerializedName("original"  ) var original  : String? = null,
    @SerializedName("large2x"   ) var large2x   : String? = null,
    @SerializedName("large"     ) var large     : String? = null,
    @SerializedName("medium"    ) var medium    : String? = null,
    @SerializedName("small"     ) var small     : String? = null,
    @SerializedName("portrait"  ) var portrait  : String? = null,
    @SerializedName("landscape" ) var landscape : String? = null,
    @SerializedName("tiny"      ) var tiny      : String? = null

)