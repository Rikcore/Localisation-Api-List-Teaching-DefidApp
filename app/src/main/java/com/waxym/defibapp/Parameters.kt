package com.waxym.defibapp

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Parameters : Serializable {

    @SerializedName("dataset")
    @Expose
    var dataset: List<String>? = null
    @SerializedName("timezone")
    @Expose
    var timezone: String? = null
    @SerializedName("rows")
    @Expose
    var rows: Int? = null
    @SerializedName("format")
    @Expose
    var format: String? = null

    companion object {
        private const val serialVersionUID = 3949958510782451810L
    }

}
