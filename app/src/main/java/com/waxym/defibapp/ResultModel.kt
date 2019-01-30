package com.waxym.defibapp

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultModel : Serializable {

    @SerializedName("nhits")
    @Expose
    var nhits: Int? = null
    @SerializedName("parameters")
    @Expose
    var parameters: Parameters? = null
    @SerializedName("records")
    @Expose
    var records: List<Record>? = null

    companion object {
        private const val serialVersionUID = -1607903208672125205L
    }

}
