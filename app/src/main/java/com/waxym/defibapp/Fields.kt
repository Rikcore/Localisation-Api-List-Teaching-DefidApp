package com.waxym.defibapp

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Fields : Serializable {

    @SerializedName("commune")
    @Expose
    var commune: String? = null
    @SerializedName("accessibilite")
    @Expose
    var accessibilite: String? = null
    @SerializedName("implantation")
    @Expose
    var implantation: String? = null
    @SerializedName("adresse")
    @Expose
    var adresse: String? = null
    @SerializedName("geo_point_2d")
    @Expose
    var geoPoint2d: List<Double>? = null
    @SerializedName("nom_site")
    @Expose
    var nomSite: String? = null
    @SerializedName("geo_shape")
    @Expose
    var geoShape: GeoShape? = null
    @SerializedName("type_structure")
    @Expose
    var typeStructure: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null

    var distance: Int = 0

    companion object {
        private const val serialVersionUID = 4019991131907912047L
    }
}
