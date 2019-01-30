package com.waxym.defibapp

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Geometry : Serializable {

    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("coordinates")
    @Expose
    var coordinates: List<Double>? = null

    companion object {
        private const val serialVersionUID = 6336903041409844920L
    }

}
